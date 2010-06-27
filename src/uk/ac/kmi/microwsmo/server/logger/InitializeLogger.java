package uk.ac.kmi.microwsmo.server.logger;

//import uk.ac.kmi.microwsmo.server.logger.ManagerRDF2GO;
import uk.ac.kmi.microwsmo.server.logger.Configuration;
import uk.ac.kmi.microwsmo.server.logger.URIImpl;
import java.util.UUID;

import java.util.Date;

public class InitializeLogger {

	//private ManagerRDF2GO managerRDF2GO;

	private URI savedItem;
	private URI exportedItem;
	private URI savedToRepositoryItem;
	private URI searchedInWatsonItem;
	private URI HTMLAnnotatedItem;
	private URI semanticalyAnnotatedItem;
	private URI calledProxyItem;
	private URI deletedHTMLAnnotationItem;
	private URI deletedSemAnnotationItem;

	public LogManager logger;
	
	public RDFRepositoryConnector logRdfRepositoryConnector;
	public URI repositoryURI; 

	// singleton
	private static InitializeLogger initializeLogger;

	public Configuration config;

	public InitializeLogger() {
		String baseDir = InitializeLogger.class.getResource("/").getPath();
		baseDir = baseDir.replaceAll("%20", " ");
		String configPath = baseDir + "../config.properties";
		
		config = new ConfigurationImpl(configPath);
		try {
			
			//RDFRepositoryConnector rdfRepositoryConnector = new RDFRepositoryConnector(config.getLogRepositoryServerUri(), config.getLogRepositoryName());
			//repositoryURI = config.getLogRepositoryServerUri();
			
			logger = new LogManagerImpl(config.getLogRepositoryServerUri(), config.getLogRepositoryName());
			
		} catch (RDFRepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logRdfRepositoryConnector = ((LogManagerImpl)logger).getConnector();
		
		savedItem = new URIImpl(LOG.Item_Save);
		exportedItem = new URIImpl(LOG.Item_Export);
		savedToRepositoryItem = new URIImpl(LOG.Item_SaveToRepository);
		searchedInWatsonItem = new URIImpl(LOG.Item_SearchInWatson);
		HTMLAnnotatedItem = new URIImpl(LOG.Item_HTMLAnnotation);
		semanticalyAnnotatedItem = new URIImpl(LOG.Item_SemanticAnnotation);
		calledProxyItem = new URIImpl(LOG.Item_CallProxy);
		deletedHTMLAnnotationItem = new URIImpl(LOG.Item_DeleteHTMLAnnotation);
		deletedSemAnnotationItem = new URIImpl(LOG.Item_DeleteSemAnnotation);
	}

	
	public static InitializeLogger get() throws RDFRepositoryException {
		if ( null == initializeLogger ) {
			initializeLogger = new InitializeLogger();
		}
		return initializeLogger;
	}
	
	//savedItem, exportedItem, savedToRepositoryItem, searchedInWatsonItem,
	//HTMLAnnotatedItem, semanticalyAnnotatedItem, calledProxyItem, deletedHTMLAnnotationItem 
	//deletedSemAnnotationItem
	public void addSavedItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, savedItem, documentUri, "http://www.soa4all.eu/Save/" + element, new Date(), method);
		}
		//return result;
	}
	
	public void addExportedItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, exportedItem, documentUri, "http://www.soa4all.eu/Export/" + element, new Date(), method);
		}
		//return result;
	}
	
	public void addSavedToRepositoryItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, savedToRepositoryItem, documentUri, "http://www.soa4all.eu/SaveToRepository/" + element, new Date(), method);
		}
		//return result;
	}
	
	public void addSearchedInWatsonItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, searchedInWatsonItem, documentUri, "http://www.soa4all.eu/WatsonSearch/" + element, new Date(), method);
		}
		//return result;
	}
	
	//OK (element, method, sessionId, method, processId, documentUri);
	public void addHTMLAnnotatedItem(String element, String content, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, HTMLAnnotatedItem, documentUri, "http://www.soa4all.eu/hRESTSAnnotation/"+ element, new Date(), method);
		}
		//return result;
	}
	
	//TO Test
	public void addSemanticalyAnnotatedItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ){
			logger.log(agentUri, processId, semanticalyAnnotatedItem, documentUri, "http://www.soa4all.eu/SemAnnotation/"+ element, new Date(), method);
		}
		//return result;
	}
	
	//URI agentUri, String processId, URI actionUri, String documentUri, String object, Date time, String method
	public void addCalledProxyItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, calledProxyItem, documentUri, element, new Date(), method);
		}
		//return result;
	}
	
	public void addDeletedHTMLAnnotationItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, deletedHTMLAnnotationItem, documentUri, "http://www.soa4all.eu/DeletedHTMLAnnotation/" + element, new Date(), method);
		}
		//return result;
	}
	
	public void addDeletedSemAnnotationItem(String element, URI agentUri, String method, String processId, String documentUri) {
		//URI result = managerFacade2Go.addDocument(name, content);
		//String documentId = UUID.randomUUID().toString();
		if ( logger != null ) {
			//agent,action,object,time,method
			logger.log(agentUri, processId, deletedSemAnnotationItem, documentUri, "http://www.soa4all.eu/DeletedSemAnnotation/" + element, new Date(), method);
		}
		//return result;
	}
}
