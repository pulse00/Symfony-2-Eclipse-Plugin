package com.dubture.symfony.core.parser;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.helpers.DefaultHandler;

/**
 * http://will.thestranathans.com/post/1026712315/getting-line-numbers-from-xpath-in-java
 */
public class LocationRecordingHandler extends DefaultHandler
{

    public static final String KEY_LINE_NO = "com.will.LineNumber";
    public static final String KEY_COLUMN_NO = "com.will.ColumnNumber";

    private Document doc;
    private Locator locator = null;
    private Element current;

    // The docs say that parsers are "highly encouraged" to set this
    public LocationRecordingHandler(Document doc) {
        this.doc = doc;
    }

    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    // This just takes the location info from the locator and puts
    // it into the provided node
    private void setLocationData(Node n) {
        if (locator != null) {
            n.setUserData(KEY_LINE_NO, locator.getLineNumber(), null);
            n.setUserData(KEY_COLUMN_NO, locator.getColumnNumber(), null);
        }
    }

    // Admittedly, this is largely lifted from other examples
    public void startElement(
            String uri, String localName, String qName, Attributes attrs) {
        
        Element e = null;
        if (localName != null && !"".equals(localName)) {
          e = doc.createElementNS(uri, localName);
        } else {
          e = doc.createElement(qName);
        }

        // But this part isn't lifted ;)
        setLocationData(e);

        if (current == null) {
          doc.appendChild(e);
        } else {
          current.appendChild(e);
        }
        current = e;

        // For each attribute, make a new attribute in the DOM, append it
        // to the current element, and set the column and line numbers.
        if (attrs != null) {
          for (int i = 0; i < attrs.getLength(); i++) {
            Attr attr = null;
            if (attrs.getLocalName(i) != null && !"".equals(attrs.getLocalName(i))) {
              attr = doc.createAttributeNS(attrs.getURI(i), attrs.getLocalName(i));
              attr.setValue(attrs.getValue(i));
              setLocationData(attr);
              current.setAttributeNodeNS(attr);
            } else {
              attr = doc.createAttribute(attrs.getQName(i));
              attr.setValue(attrs.getValue(i));
              setLocationData(attr);
              current.setAttributeNode(attr);
            }
          }
        }
    }

    public void endElement(String uri, String localName, String qName) {
        
    }

    // Even with text nodes, we can record the line and column number
    public void characters(char buf[], int offset, int length) {
        Node n = doc.createTextNode(new String(buf, offset, length));
        setLocationData(n);
    }    
}
