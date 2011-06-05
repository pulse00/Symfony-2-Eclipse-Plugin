package org.eclipse.symfony.core.parser;

import org.eclipse.core.resources.IFile;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
	
	private IFile file;

	public XMLHandler(IFile file) {
		this.file = file;
	}
	
	
	public void error(SAXParseException exception) throws SAXException {

	}

	public void fatalError(SAXParseException exception) throws SAXException {

	}

	public void warning(SAXParseException exception) throws SAXException {

	}
	

}
