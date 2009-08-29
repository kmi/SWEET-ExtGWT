package uk.ac.kmi.microwsmo.server;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;
import uk.ac.open.kmi.watson.clientapi.EntitySearchServiceLocator;
import uk.ac.open.kmi.watson.clientapi.OntologySearch;
import uk.ac.open.kmi.watson.clientapi.OntologySearchServiceLocator;

public class DomainOntologiesRetriever {

	private EntitySearch entityEngine;
	private OntologySearch ontoEngine;
	private SemanticDocumentsCrawler sdCrawler;
	
	public DomainOntologiesRetriever() throws ServiceException {
		entityEngine = new EntitySearchServiceLocator().getUrnEntitySearch();
		ontoEngine = new OntologySearchServiceLocator().getUrnOntologySearch();
		sdCrawler = new SemanticDocumentsCrawler();
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getDomainOntologies(String keyword) throws IOException {
		String[] semanticDocuments = sdCrawler.searchByKeywordPaginated(keyword);
		String result = semanticDocuments[semanticDocuments.length - 1] + "<!>";
		for( int i = 0; i < semanticDocuments.length - 1; i++ ) {
			String ontoURI = semanticDocuments[i];
			result += ontoURI;
			String[] entities = entityEngine.getEntitiesByKeyword(ontoURI, keyword);
			for( String entityURI : entities ) {
				String type = entityEngine.getType(ontoURI, entityURI);
				result += "<:>" + type + "<:>" + entityURI;
			}
			result += "<&>";
		}
		// drops off the last "<&>" or "<!>" from the string 
		result = result.substring(0, result.length() - 3);
		// returns the result
		return result;
	}
	
	public String getConceptsOf(String url) throws RemoteException {
		String[] concepts = ontoEngine.listClasses(url);
		String result = "";
		for( String concept : concepts ) {
			result += concept.split("#")[1] + "<:>";
		}
		if( !result.equals("") ) {
			result = result.substring(0, result.length() - 3);
		}
		return result;
	}
	
	/**
	 * 
	 * @param result
	 */
	public void showDomainOntologies(String result) {
		if( result.startsWith("0") ) {
			System.out.println("no results");
			return;
		}
		// if is not empty then, show the result
		String[] split = result.split("<!>")[1].split("<&>");
		for( String s : split ) {
			String[] chunk = s.split("<:>");
			System.out.println("Ontology: " + chunk[0]);
			for( int i = 1; i < chunk.length; i++ ) {
				if( (i % 2) != 0 ) {
					System.out.print("    " + chunk[i]  + " - ");
				} else {
					System.out.println(chunk[i]);
				}
			}
			System.out.println();
		}
	}
	
}