package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.restlet.Client;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.StringRepresentation;

import uk.ac.kmi.microwsmo.server.logger.InitializeLogger;
import uk.ac.kmi.microwsmo.server.logger.URI;
import uk.ac.kmi.microwsmo.server.logger.URIImpl;

public class SaveToRepository extends HttpServlet {

	private static final long serialVersionUID = -3241642094625985342L;
	private InitializeLogger loggerInit = null;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		
		String uri = request.getParameter("uri");
		String userName = request.getParameter("user");
		String password = request.getParameter("password");
		String html = request.getParameter("html");
		Request restRequest = new Request(Method.POST, uri);

		StringRepresentation eintity = new StringRepresentation(html, MediaType.TEXT_HTML);
		restRequest.setEntity(eintity);

		// Add the client authentication to the call
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme, userName, password.toCharArray());
		restRequest.setChallengeResponse(authentication);

		// Ask to the HTTP client connector to handle the call
		Client client = new Client(Protocol.HTTP);
		String serviceId = "";
		
		String processId = "http://www.soa4all.eu/process/sessionLost";
		String documentUri = "http://www.soa4all.eu/documentUri/sessionLost";
		URI sessionId = new URIImpl("http://www.soa4all.eu/session/sessionLost");
		long currentTime = System.currentTimeMillis();
		
		if (session != null ){
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
	    loggerInit.addSavedToRepositoryItem("SavedDocument", sessionId, "Save", processId, documentUri);
	    
			
		Response restResponse = client.handle(restRequest);

		if (restResponse.getStatus().isSuccess()) {
			serviceId = restResponse.getEntity().getText();
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/plain");
			response.getWriter().print(serviceId);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setContentType("text/plain");
			response.getWriter().print(serviceId);
		}
	}
	
	private void initializeLogger() {
		if(loggerInit ==null)
			loggerInit = new InitializeLogger();
	}
}
