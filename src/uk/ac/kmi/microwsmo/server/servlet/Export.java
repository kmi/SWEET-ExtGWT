package uk.ac.kmi.microwsmo.server.servlet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xml.sax.InputSource;

/**
 * This servlet transform an HTML file in a XML file. It
 * can be called only by a POST method. If is called by a
 * GET method, it will return an error page.
 * 
 * @author KMi, The Open University
 */
public class Export extends HttpServlet {
	
	private static final long serialVersionUID = -4142113565472752463L;
	private static String webAppRoot;
	private static File xsltFile;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		// retrieve the web application root folder
		webAppRoot = getServletContext().getRealPath("/");
		// set the base path of the internal model
		String internalModelPath = "resources/im/";
		// handle the files
		xsltFile = new File(webAppRoot + internalModelPath + "hrests.xslt");
	}

	/**
	 * Redirect the user to an error page. This servlet doesn't allow the direct
	 * invocation.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if( session != null ) {
			// parse the html page
			Document document = getDOM(session);
			// create a new transformer object from the "hrests.xslt"
			Transformer transformer = getTransformer(response);
			// transforms the page and export it in an RDF/XML file
			export(document, transformer, response);
		}
	}
	
	/**
	 * Export in RDF an HTML page.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    // retrieve the web page's html code
	    String innerHtml = request.getParameter("html");
	    session.setAttribute("html", innerHtml);
	    response.setContentType("text/html");
	    ServletOutputStream out = response.getOutputStream();
	    out.print("ok");
	    out.close();
	}
	
	/**
	 * Returns the DOM interface from a given html file.
	 * 
	 * @param inFile the html file.
	 * @param response the output interface of the servlet.
	 * @return a DOM
	 * @throws IOException
	 */
	private Document getDOM(HttpSession session) throws IOException {
		Tidy parser = new Tidy();
		Document document = null;
		String innerHTML = (String) session.getAttribute("html");
		InputSource source = new InputSource(new ByteArrayInputStream(innerHTML.getBytes()));
		document = parser.parseDOM(source.getByteStream(), null);
		return document;
	}
	
	/**
	 * Returns a transformer object, which transforms the xhtml document in a
	 * xml document by a XSLT file.
	 * 
	 * @param transformFile the XSLT file.
	 * @param response the output interface of the servlet.
	 * @return a Transformer.
	 * @throws IOException
	 */
	private Transformer getTransformer(HttpServletResponse response) throws IOException {
		TransformerFactory xformFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = xformFactory.newTransformer(new StreamSource(xsltFile));
		} catch (TransformerConfigurationException e) {
			response.sendRedirect("error-page.jsp?msg=" + e.getMessage() + "&own=" + e.getClass().getName());
		}
		return transformer;
	}
	
	/**
	 * Exports the XHTML file in a RDF/XML file.
	 * 
	 * @param document the DOM.
	 * @param transformer the transformer object.
	 * @param response the output interface of the servlet.
	 * @throws IOException
	 */
	private void export(Document document, Transformer transformer, HttpServletResponse response) throws IOException {
		DOMSource source = new DOMSource(document);
		response.setContentType("application/x-download");
    	response.setHeader("content-disposition", "attachment; filename=\"rdfExportation.xml\"");
    	ServletOutputStream out = response.getOutputStream();
		StreamResult result = new StreamResult(out);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			response.sendRedirect("error-page.jsp?msg=" + e.getMessage() + "&own=" + e.getClass().getName());
		}
		out.close();
	}

}
