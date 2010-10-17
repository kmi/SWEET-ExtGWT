package uk.ac.kmi.microwsmo.server.servlet;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.*;
import org.restlet.data.Response;

import uk.ac.kmi.microwsmo.client.MicroWSMODataService;
import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;
import uk.ac.kmi.microwsmo.server.GuiModelBuilder;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MicroWSMODataServiceImpl extends RemoteServiceServlet implements MicroWSMODataService {
	
	public OntologyNode buildModelFromOntology(String restURL) throws Exception {
        String rawData = fetchContent(restURL);
        if (restURL.toLowerCase().endsWith(".wsml")) {
            return GuiModelBuilder.buildFromWSML(rawData);
        }
        else if (restURL.toLowerCase().endsWith(".rdf") 
        		|| restURL.toLowerCase().endsWith(".rdfs")
                || restURL.toLowerCase().endsWith(".xml") 
                || restURL.toLowerCase().endsWith(".owl")) {
        	try {
            return GuiModelBuilder.buildFromRDF(rawData);
        	}
        	catch(Throwable err) {
        		System.out.println("ERROR " + err.getMessage());
        		err.printStackTrace();
        		
        		throw new Exception(err.getMessage());
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
