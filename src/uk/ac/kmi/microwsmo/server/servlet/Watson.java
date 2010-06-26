package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import uk.ac.kmi.microwsmo.server.DomainOntologiesRetriever;
import uk.ac.kmi.microwsmo.server.ServicePropertiesRetriever;
import uk.ac.kmi.microwsmo.server.model.SemanticTreesModel;

/**
 * This servlet receives a keyword and retrieves semantic documents.
 * Is possible manage some admin operations.
 * 
 * @author KMi, The Open University
 */
public class Watson extends HttpServlet {
	
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Watson.class.getName());
	
	private static final long serialVersionUID = 3703751098039606931L;
	
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
		// retrieve the method requested
		String method = request.getParameter("method");
		// initialize the result variable
		String result = "";
		// if the method is a service properties or a domain ontologies query
		if( method.equals("sp") || method.equals("do") ) {
			// retrieve the keyword
			String keyword = request.getParameter("keyword");
			// if the method is "sp" (Service Properties)
			if( method.equals("sp") ) {
				ServicePropertiesRetriever spRetriever = getServicePropertiesRetriever(request);
				
				result = spRetriever.getServiceProperties(keyword);
				storeServiceProperties(request, keyword, result);
				
			// else if the method is "do" (Domain Ontologies)
			} else if( method.equals("do") ) {
				DomainOntologiesRetriever doRetriever = getDomainOntologiesRetriever(request);
				
				result = doRetriever.getDomainOntologies(keyword);
				storeDomainOntologies(request, keyword, result);
			}
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			out.print(result);
			out.close();
		// otherwise if is a concept's request of an ontology
		} else if( method.equals("getConcepts") ) {
			String ontoURI = request.getParameter("ontoURI");
			ontoURI = URLDecoder.decode(ontoURI, "UTF-8");
			DomainOntologiesRetriever doRetriever = getDomainOntologiesRetriever(request);
			result = doRetriever.getConceptsOf(ontoURI);
			
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			out.print(result);
			out.close();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private ServicePropertiesRetriever getServicePropertiesRetriever(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		
		ServicePropertiesRetriever spRetriever = (ServicePropertiesRetriever) session.getAttribute("spRetriever"); 
		
		if(spRetriever == null){
			try {
				spRetriever = new ServicePropertiesRetriever();
				session.setAttribute("spRetriever", spRetriever);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
		return spRetriever;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	private DomainOntologiesRetriever getDomainOntologiesRetriever(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		DomainOntologiesRetriever doRetriever = (DomainOntologiesRetriever) session.getAttribute("doRetriever");
		
		if(doRetriever == null){
			try {
				doRetriever = new DomainOntologiesRetriever();
				session.setAttribute("doRetriever", doRetriever);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		
		return doRetriever;
	}
	
	private void storeServiceProperties(HttpServletRequest request, String keyword, String result) {
		HttpSession session = request.getSession(false);
		if( session != null ) {
			SemanticTreesModel model = (SemanticTreesModel) session.getAttribute("treesModel");
			if(model == null){
				session.setAttribute("treesModel", new SemanticTreesModel());
				model = (SemanticTreesModel) session.getAttribute("treesModel");
			}
			model.addServiceProperties(keyword, result);
			session.setAttribute("treesModel", model);
			
			logger.info(new java.util.Date() + "; get service properties; " + keyword + "; " +session);
		}
	}
	
	private void storeDomainOntologies(HttpServletRequest request, String keyword, String result) {
		HttpSession session = request.getSession(false);
		if( session != null ) {
			SemanticTreesModel model = (SemanticTreesModel) session.getAttribute("treesModel");
			if(model == null){
				session.setAttribute("treesModel", new SemanticTreesModel());
				model = (SemanticTreesModel) session.getAttribute("treesModel");
			}
			model.addDomainOntologies(keyword, result);
			session.setAttribute("treesModel", model);
			
			logger.info(new java.util.Date() + "; get domain ontologies; " + keyword + "; " +session);
		}
	}

}