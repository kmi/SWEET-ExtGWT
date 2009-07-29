package uk.ac.kmi.microwsmo.server;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This is the default error handler of the XMLParser. It
 * print the message error in a custom way.
 * 
 * @author Simone Spaccarotella
 */
public class ErrorChecker extends DefaultHandler {
	
	/**
	 * The default empty constructor.
	 */
	public ErrorChecker() {}
	
	/**
	 * 
	 */
	public void error(SAXParseException e) {
		System.err.println("[PARSING ERROR]");
		System.err.println("[message]: " + e.getMessage());
		System.err.println("[source]: " + e.getSystemId());
		System.err.println("[point]: line " + e.getLineNumber() + " column	 " + e.getColumnNumber());
		System.err.println();
	}
	
	/**
	 * 
	 */
	public void warning(SAXParseException e) {
		System.err.println("[PARSING WARNING]");
		System.err.println("[message]: " + e.getMessage());
		System.err.println("[source]: " + e.getSystemId());
		System.err.println("[point]: line " + e.getLineNumber() + " column	 " + e.getColumnNumber());
		System.err.println();
	}
	
	/**
	 * 
	 */
	public void fatalError(SAXParseException e) {
		System.err.println("[PARSING FATAL ERROR] - " + e.getMessage());
		System.err.println("[source]: " + e.getSystemId());
		System.err.println("[point]: line " + e.getLineNumber() + " column	 " + e.getColumnNumber());
		System.err.println("[CANNOT CONTINUE]");
		System.exit(1);
	}
	
}
