package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import uk.ac.kmi.microwsmo.server.logger.InitializeLogger;
import uk.ac.kmi.microwsmo.server.logger.URIImpl;
import uk.ac.kmi.microwsmo.server.logger.URI;

public class Save extends HttpServlet {
	
	private static final long serialVersionUID = 8039931655455080310L;
	
	/**
	 * Redirect the user to an error page. This servlet doesn't allow the direct
	 * invocation.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		if( session != null ) {
			String innerHTML = (String) session.getAttribute("html");
			response.setContentType("application/x-download");
	    	response.setHeader("content-disposition", "attachment; filename=\"hrestAnnotation.html\"");
	    	ServletOutputStream out = response.getOutputStream();
	    	out.print(innerHTML);
	    	out.close();
		} else {
			//Quick fix for the session null problem
			String innerHTML = Save.html;
			response.setContentType("application/x-download");
	    	response.setHeader("content-disposition", "attachment; filename=\"hrestAnnotation.html\"");
	    	ServletOutputStream out = response.getOutputStream();
	    	out.print(innerHTML);
	    	out.close();
		}
	}
	
	//Quick fix for the session null problem
	public static String html = "";
	private InitializeLogger loggerInit = null;
	
	/**
	 * Save the annotate page in a new html page.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
	    // retrieve the web page's html code
	    String innerHtml = request.getParameter("html");
	    String processId = "http://www.soa4all.eu/process/sessionLost";
		String documentUri = "http://www.soa4all.eu/documentUri/sessionLost";
		URI sessionId = new URIImpl("http://www.soa4all.eu/session/sessionLost");
		long currentTime = System.currentTimeMillis();
	    
	    if (session != null ){
	    	session.setAttribute("html", innerHtml);
    	
	    	processId = (String) session.getAttribute("processId");
	    	documentUri = (String) session.getAttribute("apiURL");
	    	
	    	if( processId == null || processId.trim() == "" ){
				 processId = "http://www.soa4all.eu/process/sessionEmpty" + currentTime;
			 }
			 if( documentUri == null || documentUri.trim() == "" ){
				 documentUri= "http://www.soa4all.eu/documentUri/sessionEmpty" + currentTime;
			 }
			 
	    	sessionId = new URIImpl("http://www.soa4all.eu/session/"+ session.getId());
		}
	    
	    initializeLogger();
	    loggerInit.addSavedItem("SavedDocument", sessionId, "Save", processId, documentUri);
	    
	    Save.html = innerHtml;
	    
	    response.setContentType("text/html");
	    ServletOutputStream out = response.getOutputStream();
	    out.print("ok");
	    out.close();
	}
	
	private void initializeLogger() {
		if(loggerInit ==null)
			loggerInit = new InitializeLogger();
	}

}
