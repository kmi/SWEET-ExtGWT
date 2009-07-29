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
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import com.sun.rowset.internal.XmlResolver;

public class HtmlAnnotation extends HttpServlet {

    //private String defaultResource;
    //private ClassLoader classLoader;

    @Override
    public void init() throws ServletException {
      //  defaultResource = this.getInitParameter("default.resource");
      //  classLoader = this.getClass().getClassLoader();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletOutputStream out = response.getOutputStream();
        try {
        	response.setContentType("text/plain");
        	String innerHtml = request.getParameter("pmtxt1");
        	
        	File file = new File("c:/Temp/HtmlAnnotation.html");
        	
        	java.io.PrintWriter write = new java.io.PrintWriter(new java.io.FileWriter(file));
        	write.print(innerHtml);
        	write.close();
        	
        	String message ="Your annotation was saved! Location:c:/Temp/HtmlAnnotation.html";
        	out.write(message.getBytes(), 0, message.getBytes().length);
        	
        } catch (Exception ex){
        	ex.printStackTrace();
        } finally {
            out.close();
        }
    } 
}
