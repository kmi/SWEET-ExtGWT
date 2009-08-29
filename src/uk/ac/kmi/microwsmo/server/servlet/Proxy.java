package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.tidy.Tidy;

/**
 * This servlet is basically a proxy. It retrieve the web page and send it back
 * to a page inside the server domain. An iframe is pointed to this proxy page
 * and every operation client side is allowed. This because the javascript function
 * works on a page that is in the same domain and so, they don't violate the
 * sand box's restrictions.
 * 
 * @author KMi, The Open University 
 */
public class Proxy extends HttpServlet {
	
	private static final long serialVersionUID = -7809356775586825102L;
	private URL url;
	private URLConnection connection;
	private Tidy parser;
	
	/**
	 * Redirects the user to an error page. It means the user is not allowed to
	 * calls directly the page by a GET method.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("405-method-not-allowed.html");
	}

	/**
	 * Is the method which manages the client request.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// retrieve the url from the query string
		String address = request.getParameter("url");
		// decodes the url
		address = URLDecoder.decode(address, "UTF-8");
		// instantiates a new URL object
		url = new URL(address);
		// create a DOM from the web page
		Document document = getDOM();
		// manipulate it, changing the relative paths, in absolute
		document = manipulateDOM(document);
		// serialize the DOM and send the serialized version back to the client
		serializeDOM(document, response);
	}
	
	/**
	 * Makes up the DOM from a web page.
	 * 
	 * @return an object which represent the DOM
	 * @throws IOException
	 */
	private Document getDOM() throws IOException {
		// open the connection toward the server which own the web page
		connection = url.openConnection();
		InputStream inputStream = connection.getInputStream();
		/*
		 * Retrieve the DOM from the page by the Tidy parser.
		 * For further information: <http://tidy.sourceforge.net>,
		 * <http://www.w3.org/People/Raggett/tidy>
		 */
		parser = new Tidy();
		Document document = parser.parseDOM(inputStream, null);
		return document;
	}
	
	/**
	 * Change every link of the page, from relative to absolute.
	 * Basically the algorithm change the value of the attributes:
	 * "src", "href" and "action".
	 * 
	 * @param document the DOM to change
	 */
	private Document manipulateDOM(Document document) {
		NodeList elements = document.getElementsByTagName("*");
		Node href = null;
		Node src = null;
		for( int i = 0; i < elements.getLength(); i++ ) {
			Node element = elements.item(i);
			String elementName = element.getNodeName();
			NamedNodeMap attributes = element.getAttributes();
			// if the tag element <script> contains inline code
			if( elementName.equals("script") && attributes.getNamedItem("src") == null ) {
				urlInspector(element);
			} else {
				// rewrite the url value of the "href" attribute
				href = attributes.getNamedItem("href");
				if( href != null ) {
					// if the tag is an <a> element
					if( elementName.equals("a") ) {
						href.setNodeValue("javascript:callProxy('" + rewriteURL(href.getNodeValue()) + "')");
					// otherwise set only the absolute path
					} else {
						href.setNodeValue(rewriteURL(href.getNodeValue()));
					}
				}
				// rewrite the url value of the "src" attribute 
				src = attributes.getNamedItem("src");
				if( src != null ) {
					src.setNodeValue(rewriteURL(src.getNodeValue()));
				}
			}
		}
		return document;
	}
	
	/**
	 * Serialize the DOM and send it back to the client.
	 * 
	 * @param document the DOM
	 * @param response the object which represent the comunication from the servlet to the client.
	 * @throws IOException
	 */
	private void serializeDOM(Document document, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		OutputStream out = response.getOutputStream();
		parser.pprint(document, out);
		out.close();
	}
	
	/**
	 * 
	 * @param element
	 */
	private void urlInspector(Node element) {
		/*
		 * TODO: this method is used for the tag such as
		 * <script> or <style> which contain inline code.
		 */
	}
	
	/**
	 * This method rewrite the URL from relative to absolute.
	 * 
	 * @param urlString is the URL string that has to be rewrited.
	 * @return the rewrited URL in absolute way.
	 */
	private String rewriteURL(String urlString) {
		if( urlString.startsWith("http") || urlString.startsWith("ftp") || urlString.startsWith("file") ) {
			return urlString;
		} else if (urlString.startsWith("/")) {
			return getDomain() + urlString;
		} else {
			return getCurrentPath() + urlString;
		}
	}
	
	/**
	 * Returns the name server domain.
	 * @return a string which represent the URL of the server.
	 */
	private String getDomain() {
		String protocol = url.getProtocol();
		String host = url.getHost();
		return protocol + "://" + host;
	}
	
	/**
	 * Returns the name of the URL, where the resource is stored inside
	 * the server.
	 * @return a string which represent the URL of the resource 
	 */
	private String getCurrentPath() {
		String path = url.getPath();
		String[] split = path.split("/");
		path = "/";
		for( int i = 0; i < split.length - 1; i++) {
			path += split[i];
		}
		return getDomain() + "/" + path + "/";
	}

}
