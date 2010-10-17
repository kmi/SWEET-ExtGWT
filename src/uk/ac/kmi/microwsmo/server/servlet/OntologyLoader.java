package uk.ac.kmi.microwsmo.server.servlet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.*;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;
import uk.ac.kmi.microwsmo.server.GuiModelBuilder;

 /*******************
 *  Currently not used !!!
 * @author msm324
 *
 */
public class OntologyLoader extends HttpServlet {
	
static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Watson.class.getName());
	
	private static final long serialVersionUID = 3703751448039606931L;
	
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
		String url = request.getParameter("url");
		// initialize the result variable
		String result = "";
		// if the method is a service properties or a domain ontologies query
		if( url.trim() != "" && url != "") {
			
			try {
				OntologyNode node = buildModelFromOntology(url);
				
				result = node.toString();
				
				response.setContentType("text/plain");
				ServletOutputStream out = response.getOutputStream();
				out.print(result);
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public OntologyNode buildModelFromOntology(String restURL) throws Exception {
        String rawData = fetchContent(restURL);
        if (restURL.toLowerCase().endsWith(".wsml")) {
            return GuiModelBuilder.buildFromWSML(rawData);
        }
        else if (restURL.toLowerCase().endsWith(".rdf") 
                || restURL.toLowerCase().endsWith(".xml") 
                || restURL.toLowerCase().endsWith(".owl")) {
        	try {
            return GuiModelBuilder.buildFromRDF(rawData);
        	}
        	catch(Throwable err) {
        		System.out.println("ERROR " + err.getMessage());
        		err.printStackTrace();
        	}
        }
        throw new Exception("Unsupported data format!");
    }
	
	private String fetchContent(String url) throws Exception {
        GetMethod getMtd = new GetMethod(url);
        HttpClient httpclient = new HttpClient();
        try {
            int result = httpclient.executeMethod(getMtd);
            if (result != 200) {
                if (getMtd.getResponseHeader("Error") != null) {
                    throw new Exception("Error : " + getMtd.getResponseHeader("Error").getValue());
                }
                throw new Exception("Storage service error code - " + result);
            }
            BufferedInputStream response = new BufferedInputStream(getMtd.getResponseBodyAsStream());
            ByteArrayOutputStream resultBuffer = new ByteArrayOutputStream();

            byte[] buffer = new byte[1000];
            int i;
            do {
                i = response.read(buffer);
                if (i > 0) {
                    resultBuffer.write(buffer, 0, i);
                }
            }
            while(i > 0);
            getMtd.releaseConnection();
            
            return resultBuffer.toString("UTF-8");
        }
        catch(Exception exc) {
            getMtd.releaseConnection();
            throw new Exception(exc.getClass().getSimpleName() + " : " + exc.getMessage());
        }
    }
	
}
