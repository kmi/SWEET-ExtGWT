package uk.ac.kmi.microwsmo.server.logger;

import uk.ac.kmi.microwsmo.server.logger.URI;
//import uk.ac.open.kmi.iserve.model.common.URI;

public interface Configuration {

	public String getHostName();

	public URI getLogRepositoryServerUri();
	
	public String getLogRepositoryName();
	
	//public URI getServiceRepositoryServerUri();
	
	//public String getServiceRepositoryName();

	//public String getLogRepositoryServerUri();

	/*public String getProxyHostName();

	public String getProxyPort();

	public String getDocumentFolder();

	public String getHRestsXsltPath();*/

}
