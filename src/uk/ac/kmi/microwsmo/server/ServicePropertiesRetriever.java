package uk.ac.kmi.microwsmo.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;
import uk.ac.open.kmi.watson.clientapi.EntitySearchServiceLocator;

public class ServicePropertiesRetriever {
	
	private EntitySearch entityEngine;
	private SemanticDocumentsCrawler sdCrawler;
	
	public ServicePropertiesRetriever() throws ServiceException {
		entityEngine = new EntitySearchServiceLocator().getUrnEntitySearch();
		sdCrawler = new SemanticDocumentsCrawler();
	}

	/**
	 * Get a list of entity, which are 
	 * @return
	 * @throws IOException
	 * @throws ServiceException 
	 */
	public String getServiceProperties(String keyword) throws IOException {
		String[] semanticDocuments = sdCrawler.searchByKeywordPaginated(keyword);
		// create the data structure
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> ontologies = null;
		// connect to the server
		for( int i = 0; i < semanticDocuments.length - 1; i++ ) {
			String ontoURI = semanticDocuments[i];
			String[] entities = entityEngine.getEntitiesByKeyword(ontoURI, keyword);
			for( String entityURI : entities ) {
				String type = entityEngine.getType(ontoURI, entityURI);
				String key = type + "<:>" + entityURI;
				// if the map doesn't contain the entity
				if( !map.containsKey(key) ) {
					// add this entity with the new ontology retrieved
					ontologies = new ArrayList<String>();
					ontologies.add(ontoURI);
					map.put(key, ontologies);
				// otherwise, if it contains the entity
				} else {
					// get the list of ontologies already retrieved
					// and add the new one to it
					ontologies = map.get(key);
					ontologies.add(ontoURI);
					map.put(key, ontologies);
				}
			}
		}
		
		/*
		 * prepare the result to return
		 */
		String result = semanticDocuments[semanticDocuments.length-1] + "<!>";
		Iterator<String> i = map.keySet().iterator();
		while( i.hasNext() ) {
			String key = i.next();
			ontologies = map.get(key);
			result += key;
			for( String ontoURI : ontologies ) {
				result += "<:>" + ontoURI;
			} 
			result += "<&>";
		}
		// drops off the last "<&>" or "<!>" from the string 
		result = result.substring(0, result.length() - 3);
		// returns the result
		return result;
	}
	
	/**
	 * 
	 * @param result
	 */
	public void showServiceProperties(String result) {
		if( result.startsWith("0") ) {
			System.out.println("no results");
			return;
		}
		// if is not empty then, show the result
		String[] split = result.split("<!>")[1].split("<&>");
		for( String s : split ) {
			String[] chunk = s.split("<:>");
			System.out.println(chunk[0] + " - " + chunk[1]);
			for( int i = 2; i < chunk.length; i++ ) {
				System.out.println("    " + chunk[i]);
			}
			System.out.println();
		}
	}
	
}