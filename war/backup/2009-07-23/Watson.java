package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import uk.ac.kmi.microwsmo.server.Cache;
import uk.ac.kmi.microwsmo.server.WatsonEngine;

/**
 * This servlet receives a keyword and retrieves semantic documents.
 * Is possible manage some admin operations.
 * 
 * @author Simone Spaccarotella
 */
public class Watson extends HttpServlet {
	
	private static final long serialVersionUID = 3703751098039606931L;
	
	private Cache cache;
	private WatsonEngine watsonEngine;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		cache = new Cache();
		cache.setExpireTimeInMinutes(5);
	}
	
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
		// get the parameter from the query string
		String method = request.getParameter("method");
		
		if( method.equals("sp") || method.equals("od") ) {
			String keyword = request.getParameter("keyword");
			String result = "";
			
			try {
				
				String[] semanticDocuments = searchByKeyword(keyword);
				// if the method is "sp" (Service Properties)
				if( method.equals("sp") ) {
					result = getServiceProperties(keyword, semanticDocuments);
				// else if the method is "od" (Ontology Domain)
				} else if( method.equals("od") ) {
					result = getOntologyDomain(keyword, semanticDocuments);
				}
				
			} catch (ServiceException e) {}
			
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			out.print(result);
			out.close();
			
		} else if( method.equals("setCacheExpireTime") ) {
			// set the cache expire time
			setCacheExpireTime(request);
		} else if( method.equals("getConcepts") ) {
			String ontoURI = request.getParameter("ontoURI");
			String result = getConceptsOf(ontoURI);
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			out.print(result);
			out.close();
		}
	}
	
	private String[] searchByKeyword(String keyword) throws ServiceException, RemoteException {
		watsonEngine = new WatsonEngine();
		if( !cache.isAlreadySearched(keyword) ) {
			String[] result = watsonEngine.searchByKeyword(keyword);
			cache.storeSemanticDocuments(keyword, result);
			return result;
		} else {
			return cache.getSemanticDocumentsOf(keyword);
		}
	}
	
	private String getServiceProperties(String keyword, String[] semanticDocuments) throws IOException {
		if( cache.isServicePropertiesCached(keyword) ) {
			return cache.getServicePropertiesOf(keyword);
		} else {
			String serviceProperties = watsonEngine.getServiceProperties(semanticDocuments);
			cache.storeServiceProperties(keyword, serviceProperties);
			return serviceProperties;
		}
	}
	
	private String getOntologyDomain(String keyword, String[] semanticDocuments) throws IOException {
		if( cache.isDomainOntologiesCached(keyword) ) {
			return cache.getDomainOntologiesOf(keyword);
		} else {
			String domainOntologies = watsonEngine.getDomainOntologies(semanticDocuments);
			cache.storeDomainOntologies(keyword, domainOntologies);
			return domainOntologies;
		}
	}
	
	private String getConceptsOf(String ontoURI) throws RemoteException {
		return watsonEngine.getConceptsOf(ontoURI);
	}
	
	private void setCacheExpireTime(HttpServletRequest request) {
		String uriPassword = request.getParameter("password");
		String adminPassword = getPassword();
		if( uriPassword.equals(adminPassword) ) {
			Integer time = new Integer(request.getParameter("time"));
			String kindOfTime = request.getParameter("kind");
			if( kindOfTime.equals("s") ) {
				cache.setExpireTimeInSeconds(time);
			} else if( kindOfTime.equals("m") ) {
				cache.setExpireTimeInMinutes(time);
			} else if( kindOfTime.equals("h") ) {
				cache.setExpireTimeInHours(time);
			}
		}
	}
	
	/**
	 * This method read the password from a configuration file
	 * in order to compare the password passed by a POST method.
	 * If the two password match, the user is allowed to do the
	 * administration operations.
	 * 
	 * @return the password
	 */
	private String getPassword() {
		// TODO write the code here
		return "";
	}

}
