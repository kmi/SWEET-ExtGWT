package uk.ac.kmi.microwsmo.server;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;
import uk.ac.open.kmi.watson.clientapi.EntitySearchServiceLocator;
import uk.ac.open.kmi.watson.clientapi.OntologySearch;
import uk.ac.open.kmi.watson.clientapi.OntologySearchServiceLocator;

public class WatsonEngine {
	
	private String currentKeyword;

	private OntologySearch ontoEngine;
	private EntitySearch entityEngine;
	
	private OntologyDomain ontoDomain;
	private ServiceProperties servProperties;
	
	public WatsonEngine() throws ServiceException {
		currentKeyword = "";
		ontoEngine = new OntologySearchServiceLocator().getUrnOntologySearch();
		entityEngine = new EntitySearchServiceLocator().getUrnEntitySearch();
	}
	
	public void searchByKeyword(String keyword) throws RemoteException {
		if( keyword != currentKeyword ) {
			currentKeyword = keyword;
			// initialize the data structures
			ontoDomain = new OntologyDomain(entityEngine);
			ontoDomain.setKeyword(keyword);
			servProperties = new ServiceProperties(entityEngine);
			servProperties.setKeyword(keyword);
			// retrieve the semantic documents
			String[] keywords = {keyword};
			String[] semanticDocuments = ontoEngine.getSemanticContentByKeywords(keywords);
			// generate the domain ontologies
			ontoDomain.generateOntologyDomain(semanticDocuments);
			// generate the service properties
			servProperties.generateServiceProperties(semanticDocuments);
		}
	}
	
	public String getOntologyDomain() {
		return ontoDomain.getOntologyDomain();
	}
	
	public String getServiceProperties() {
		return servProperties.getServiceProperties();
	}
	
}