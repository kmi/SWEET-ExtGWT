package uk.ac.kmi.microwsmo.server.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet shows the file passed such as parameter.
 * 
 * @author KMi, The Open University
 */
public class ShowFile extends HttpServlet {
	
	private static final long serialVersionUID = -5653219922964779345L;
	
	/**
	 * Shows the file.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getParameter("path");
		ServletOutputStream out = response.getOutputStream();
		// if the path is valid and is one of the two file then
		if( path != null && !path.equals("") && ( path.endsWith("hrestAnnotation.html") || path.endsWith("rdfExport.xml") ) ) {
			if( path.endsWith("hrestAnnotation.html") ) {
				response.setContentType("text/plain");
			} else {
				response.setContentType("text/xml");
			}
			File file = new File(path);
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = input.readLine();
			while( line != null ) {
				out.println(line);
				line = input.readLine();
			}
			input.close();
			out.close();
		} else {
			response.setContentType("text/html");
			out.print("File not found or not allowed to be shown");
		}
	}

}
