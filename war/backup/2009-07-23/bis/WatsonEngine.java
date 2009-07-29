package uk.ac.kmi.microwsmo.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;
import uk.ac.open.kmi.watson.clientapi.EntitySearchServiceLocator;
import uk.ac.open.kmi.watson.clientapi.OntologySearch;
import uk.ac.open.kmi.watson.clientapi.OntologySearchServiceLocator;
import uk.ac.open.kmi.watson.clientapi.WatsonService;

/**
 * 
 * @author Simone Spaccarotella
 */
public final class WatsonEngine {

	/** the engine for the watson queries  */
	private OntologySearch ontoEngine;
	private EntitySearch entityEngine;
	
	/* Service Properties attributes */
	private String keyword;
	private int startSubset;
	private int sizeSet;
	private boolean firstSearch;
	
	/* Domain Ontologies attributes */
	
	private static final int DELTA = 5;
	
	/**
	 * Create a new engine.
	 * 
	 * @throws ServiceException
	 */
	public WatsonEngine() throws ServiceException {
		ontoEngine = new OntologySearchServiceLocator().getUrnOntologySearch();
		entityEngine = new EntitySearchServiceLocator().getUrnEntitySearch();
		keyword = "";
	}
	
	/**
	 * Retrieve the semantic documents related to the keyword.
	 * 
	 * @param keyword the keyword which is contained into the retrieved ontologies.
	 * @throws RemoteException
	 */
	public String[] searchByKeyword(String keyword) throws RemoteException {
		if( !keyword.equals(this.keyword) ) {
			this.keyword = keyword;
			startSubset = 0;
			firstSearch = true;
		}
		String[] keywords = {keyword};
		int scopeModifier = WatsonService.LOCAL_NAME + WatsonService.LABEL + WatsonService.LITERAL;
		int entityTypeModifier = WatsonService.CLASS + WatsonService.PROPERTY + WatsonService.INDIVIDUAL;
		int matchTechnique = WatsonService.EXACT_MATCH;
		String[] result = ontoEngine.getSemanticContentByKeywordsWithRestrictionsPaginated(keywords, scopeModifier, entityTypeModifier, matchTechnique, startSubset, DELTA);
		if( firstSearch ) {
			sizeSet = new Integer(result[result.length - 1]);
			firstSearch = false;
		}
		startSubset += DELTA;
		if( startSubset >= sizeSet ) {
			result[result.length - 1] = "0";
			return result;
		} else {
			result[result.length - 1] = String.valueOf(sizeSet - startSubset);
			return result;
		}
	}
	
	/**
	 * Get a list of entity, which are 
	 * @return
	 * @throws IOException
	 */
	public String getServiceProperties(String[] semanticDocuments) throws IOException {
		// this check if "searchByKeyword" method is already called
		if (semanticDocuments == null || semanticDocuments.length == 0 || keyword == null)
			return "";
		// create the data structure
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
		ArrayList<String> ontologies = null;
		// connect to the server
		for( String ontoURI : semanticDocuments ) {
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
		String result = "";
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
		// drops off the last "<&>" from the string 
		result = result.substring(0, result.length() - 3);
		// returns the result
		return result;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	public String getDomainOntologies(String[] semanticDocuments) throws IOException {
		// this check if "searchByKeyword" method is already called
		if (semanticDocuments == null || semanticDocuments.length == 0 || keyword == null)
			return "";
		String result = "";
		for( String ontoURI : semanticDocuments ) {
			result += ontoURI;
			String[] entities = entityEngine.getEntitiesByKeyword(ontoURI, keyword);
			for( String entityURI : entities ) {
				String type = entityEngine.getType(ontoURI, entityURI);
				result += "<:>" + type + "<:>" + entityURI;
			}
			result += "<&>";
		}
		// drops off the last "<&>" from the string 
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
	protected void showServiceProperties(String result) {
		// check if the input is empty
		if( result.equals("") ) return;
		// if is not empty then, show the result
		String[] split = result.split("<&>");
		for( String s : split ) {
			String[] chunk = s.split("<:>");
			System.out.println(chunk[0] + " - " + chunk[1]);
			for( int i = 2; i < chunk.length; i++ ) {
				System.out.println("    " + chunk[i]);
			}
			System.out.println();
		}
	}
	
	/**
	 * 
	 * @param result
	 */
	protected void showDomainOntologies(String result) {
		// check if the input is empty
		if( result.equals("") ) return;
		// if is not empty then, show the result
		String[] split = result.split("<&>");
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