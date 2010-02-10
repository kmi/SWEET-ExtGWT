package uk.ac.kmi.microwsmo.server.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.Client;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

public class SaveToRepository extends HttpServlet {

	private static final long serialVersionUID = -3241642094625985342L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getParameter("uri");
		String userName = request.getParameter("user");
		String password = request.getParameter("password");
		String html = request.getParameter("html");
		Request restRequest = new Request(Method.POST, uri);

		Form form = new Form();
		if ( uri.endsWith("services")) {
			form.add("format", "HTML");
			form.add("description", html);
			form.add("user", userName);
		} else {
			form.add("name", "hRest.html");
			form.add("content", html);
			form.add("user", userName);
		}
		Representation rep = form.getWebRepresentation();
		restRequest.setEntity(rep);

		// Add the client authentication to the call
		ChallengeScheme scheme = ChallengeScheme.HTTP_BASIC;
		ChallengeResponse authentication = new ChallengeResponse(scheme, userName, password.toCharArray());
		restRequest.setChallengeResponse(authentication);

		// Ask to the HTTP client connector to handle the call
		Client client = new Client(Protocol.HTTP);
		String serviceId = "";
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
}
