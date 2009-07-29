package uk.ac.kmi.microwsmo.server;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * This class implement a parser. It parse the XML file
 * and validate it with according to a DTD or accordint to
 * a XMLSchema.
 * 
 * @author Simone Spaccarotella
 */
public class XMLParser {

	private DocumentBuilder parser;
	private DocumentBuilderFactory docBuildFactory;

	/**
	 * Create a new parser from a Factory Class.
	 */
	public XMLParser() {
		docBuildFactory = DocumentBuilderFactory.newInstance();
	}

	/**
	 * Set the validating flag. This method is referred
	 * to a validation with a DTD. From default the validating
	 * is setted to false.
	 * 
	 * @param validating true if the parser validate the document, false otherwise.
	 */
	public void setValidating(boolean validating) {
		docBuildFactory.setValidating(validating);
	}

	/**
	 * Set an xml schema to this parser, so the XML file
	 * can be validated according to it.
	 * 
	 * @param xmlSchemaFile the XMLSchema
	 */
	public void setXMLSchema(File xmlSchemaFile) {
		Schema schema = null;
		try {
			schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(xmlSchemaFile);
		} catch (SAXException e) {
			e.printStackTrace();
		}
		docBuildFactory.setSchema(schema);
	}

	/**
	 * Parse the XML file passed in input.
	 * 
	 * @param xmlFile the XML file which have to be parsed
	 * @throws SAXException A parsing exception.
	 * @throws IOException It is thrown if the file doesn't exists.
	 */
	public void parse(File xmlFile) throws SAXException, IOException {
		try {
			parser = docBuildFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		parser.setErrorHandler(new ErrorChecker());
		parser.parse(xmlFile);
	}

}
