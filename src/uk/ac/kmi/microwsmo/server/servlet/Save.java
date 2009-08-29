package uk.ac.kmi.microwsmo.server.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		}
	}
	
	/**
	 * Save the annotate page in a new html page.
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

}
