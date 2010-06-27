package uk.ac.kmi.microwsmo.server.servlet;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.rpc.ServiceException;

import org.w3c.dom.Document;

import uk.ac.kmi.microwsmo.server.DomainOntologiesRetriever;
import uk.ac.kmi.microwsmo.server.ServicePropertiesRetriever;
import uk.ac.kmi.microwsmo.server.logger.InitializeLogger;
import uk.ac.kmi.microwsmo.server.logger.LOG;
import uk.ac.kmi.microwsmo.server.logger.LogManager;
import uk.ac.kmi.microwsmo.server.logger.RDFRepositoryException;
import uk.ac.kmi.microwsmo.server.logger.URI;
import uk.ac.kmi.microwsmo.server.logger.URIImpl;
import uk.ac.kmi.microwsmo.server.model.SemanticTreesModel;


	/**
	 * 
	 * @author KMi, The Open University
	 */
	public class Logger extends HttpServlet {

		private InitializeLogger loggerInit = null;
		private URI sessionId;
		
		/**
		 * Redirects the user to an error page. It means the user is not allowed to
		 * calls directly the page by a GET method.
		 */
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.sendRedirect("405-method-not-allowed.html");
		}
		
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			String item = request.getParameter("item");
			String method = request.getParameter("method");
			String element = request.getParameter("element");
			String result = "";
			String processId = "http://www.soa4all.eu/process/sessionLost";
			String documentUri = "http://www.soa4all.eu/documentUri/sessionLost";
			sessionId = new URIImpl("http://www.soa4all.eu/session/sessionLost");
			long currentTime = System.currentTimeMillis();
			
			initializeLogger();
			
			HttpSession session = request.getSession(true);
			if (session != null ){
			 processId = (String) session.getAttribute("processId");
			 documentUri = (String) session.getAttribute("apiURL");
			 
			 if( processId == null || processId.trim() == "" ){
				 processId = "http://www.soa4all.eu/process/sessionEmpty"+currentTime;
			 }
			 if( documentUri == null || documentUri.trim() == "" ){
				 documentUri= "http://www.soa4all.eu/documentUri/sessionEmpty"+currentTime;
			 }
			 
			 sessionId = new URIImpl("http://www.soa4all.eu/session/"+ session.getId());
			}			
				
			if ( (LOG.Item_Export).endsWith(item) ) {
				loggerInit.addExportedItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_HTMLAnnotation).endsWith(item)) {
				loggerInit.addHTMLAnnotatedItem(element, method, sessionId, method, processId, documentUri);
				
			} else if ( (LOG.Item_Save).endsWith(item) ) {
				loggerInit.addSavedItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_SaveToRepository).endsWith(item) ) {
				loggerInit.addSavedToRepositoryItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_SearchInWatson).endsWith(item) ) {
				loggerInit.addSearchedInWatsonItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_SemanticAnnotation).endsWith(item) ) {
				loggerInit.addSemanticalyAnnotatedItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_CallProxy).endsWith(item) ) {
				loggerInit.addCalledProxyItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_DeleteHTMLAnnotation).endsWith(item) ) {
				loggerInit.addDeletedHTMLAnnotationItem(element, sessionId,  method, processId, documentUri);
				
			} else if ( (LOG.Item_DeleteSemAnnotation).endsWith(item) ) {
				loggerInit.addDeletedSemAnnotationItem(element, sessionId,  method, processId, documentUri);
			}
						
			
			//String element, String content, URI agentUri, String method
			
			
			response.setContentType("text/plain");
			ServletOutputStream out = response.getOutputStream();
			result = item;
			out.print(result);
			out.close();
		}
		
		private void initializeLogger() {
			if(loggerInit ==null)
				loggerInit = new InitializeLogger();
		}
	}

