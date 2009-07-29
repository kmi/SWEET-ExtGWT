package uk.ac.kmi.microwsmo.server.internalmodel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.SAXException;

import uk.ac.kmi.microwsmo.server.XMLParser;

/**
 * 
 * @author Simone Spaccarotella
 */
public class InternalModelGenerator extends HttpServlet {
	
	private static final long serialVersionUID = -1472163444302903143L;
	private static String WEBAPP_ROOT_PATH;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WEBAPP_ROOT_PATH = getServletContext().getRealPath("/");		
	}
	
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("405-method-not-allowed.html");
	}
	
	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// set the base path
		String basePath = "resources/im/";
		// handle the files
		File transformFile = new File(WEBAPP_ROOT_PATH + basePath + "xhtml2xml.xslt");
		File internalModelFile = new File(WEBAPP_ROOT_PATH + basePath + "internalModel.xml");
		File xmlSchemaFile = new File(WEBAPP_ROOT_PATH + basePath + "hrestSchema.xsd");
		String innerHTML = request.getParameter("html");
		ByteArrayInputStream inputStream = new ByteArrayInputStream(innerHTML.getBytes());
		
		// parse the file
		Tidy tidyParser = new Tidy();
		Document document = null;
		document = tidyParser.parseDOM(inputStream, null);
		inputStream.close();
		// create a new transformer object from the "xhtml2xml.xslt"
		TransformerFactory xformFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = xformFactory.newTransformer(new StreamSource(transformFile));
		} catch (TransformerConfigurationException e) {
			response.sendRedirect("error-page.jsp?msg=" + e.getMessage() + "&own=" + e.getClass().getName());
		}
		// transforms the page and save it in an XML file
		DOMSource source = new DOMSource(document);
		StreamResult result = new StreamResult(internalModelFile);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			response.sendRedirect("error-page.jsp?msg=" + e.getMessage() + "&own=" + e.getClass().getName());
		}
		
		response.setContentType("text/plain");
		ServletOutputStream out = response.getOutputStream();
		XMLParser parser = new XMLParser();
		parser.setXMLSchema(xmlSchemaFile);
		try {
			parser.parse(internalModelFile);
			out.print("ok");
		} catch (SAXException e) {
			out.print("error");
		} finally {
			out.close();
		}
		
	}
	
}
