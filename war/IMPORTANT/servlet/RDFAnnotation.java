package uk.ac.open.powermagpie.servlet;

import java.io.*;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.thoughtworks.xstream.XStream;
import javax.xml.soap.Node;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
//import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.sun.rowset.internal.XmlResolver;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import javax.xml.parsers.FactoryConfigurationError;
import  javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileOutputStream;




/**
 *
 * @author Laurian Gridinoc, Simone Spaccarotella
 */
public class RDFAnnotation extends HttpServlet {

   // private String defaultResource;
  // private ClassLoader classLoader;

    @Override
    public void init() throws ServletException {
        //defaultResource = this.getInitParameter("default.resource");
        //classLoader = this.getClass().getClassLoader();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletOutputStream out = response.getOutputStream();
        try {
        	response.setContentType("text/xml");
        	String innerHtml = request.getParameter("pmtxt2");
        	
        	DocumentBuilderFactory docBuildFactory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder parser = docBuildFactory.newDocumentBuilder();
        	Document document = parser.parse(new InputSource(new ByteArrayInputStream(innerHtml.getBytes())));
        	
        	TransformerFactory xformFactory =   TransformerFactory.newInstance();
        	Transformer transformer = xformFactory.newTransformer(new javax.xml.transform.stream.StreamSource("c:/Temp/hrests.xslt"));
   
        	DOMSource source = new DOMSource(document);
        	
        	PrintWriter outStream = new PrintWriter(new FileOutputStream("c:/Temp/xmlAnnotation.xml"));
        	StreamResult fileResult = new StreamResult(outStream);
        	
        	transformer.transform(source, fileResult); 
        	transformer.transform(source, new javax.xml.transform.stream.StreamResult(out));  
        	
           	outStream.close();
        	
        } catch (Exception ex){
        	ex.printStackTrace();
        } finally {
            out.close();
        }
    } 
    @Override
    public String getServletInfo() {
        return "Mounts classloader accessible resources";
    }
}
