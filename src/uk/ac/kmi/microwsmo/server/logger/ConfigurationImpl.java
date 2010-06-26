package uk.ac.kmi.microwsmo.server.logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import uk.ac.kmi.microwsmo.server.logger.Configuration;
//import uk.ac.open.kmi.iserve.api.exceptions.ConfigurationException;
import uk.ac.kmi.microwsmo.server.logger.URIImpl;
import uk.ac.kmi.microwsmo.server.logger.URI;

public class ConfigurationImpl implements Configuration {

	private Properties props;

	//private String hRestsXsltPath;

	public ConfigurationImpl(String filePath) {
		//this.hRestsXsltPath = hRestsXsltPath;
		props = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));
			props.load(in);
		} catch (FileNotFoundException e) {
		//	throw new ConfigurationException(e);
		} catch (IOException e) {
		//	throw new ConfigurationException(e);
		}
	}

	public String getHostName() {
		if ( props != null )
			return props.getProperty ("serverName");
		return null;
	}
	
	public URI getLogRepositoryServerUri() {
		if ( props != null )
			return new URIImpl(props.getProperty ("logServerURL"));
		return null;
	}

	public String getLogRepositoryName() {
		if ( props != null )
			return props.getProperty ("logRepoName");
		return null;
	}

	/*public URI getServiceRepositoryServerUri() {
		if ( props != null )
			return new URIImpl(props.getProperty ("sesameServerURL"));
		return null;
	}

	public String getServiceRepositoryName() {
		if ( props != null )
			return props.getProperty ("repoName");
		return null;
	}

	public String getProxyHostName() {
		if ( props != null )
			return props.getProperty ("proxyHostName");
		return null;
	}

	public String getProxyPort() {
		if ( props != null )
			return props.getProperty ("proxyPort");
		return null;
	}

	public String getDocumentFolder() {
		if ( props != null )
			return props.getProperty ("docFolder");
		return null;
	}

	public String getHRestsXsltPath() {
		return hRestsXsltPath;
	}*/
}
