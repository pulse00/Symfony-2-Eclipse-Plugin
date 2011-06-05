package org.eclipse.symfony.core.parser;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.resources.IFile;
import org.xml.sax.SAXException;

public class XMLConfigParser implements IConfigParser {

	private SAXParserFactory parserFactory;

	@Override
	public void parse(IFile file) {

		try {			
			System.out.println("parse xml config file: " + file.getFullPath().toString());
			getParser().parse(file.getContents(), new XMLHandler(file));
		} catch (Exception e) {			
			e.printStackTrace();			
		}
	}


	private SAXParser getParser() throws ParserConfigurationException,
		SAXException {
		if (parserFactory == null) {
			parserFactory = SAXParserFactory.newInstance();
		}
		return parserFactory.newSAXParser();
	}
}