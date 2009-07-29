package uk.ac.kmi.microwsmo.server;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;

public class ServiceProperties {

	private EntitySearch entityEngine;
	private String keyword;
	private Map<String, LinkedList<String>> entityOntologyMap;
	
	public ServiceProperties(EntitySearch entityEngine) {
		this.entityEngine = entityEngine;
		keyword = "";
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void generateServiceProperties(String[] semanticDocuments) throws RemoteException {
		// create the new data structure
		entityOntologyMap = new LinkedHashMap<String, LinkedList<String>>();
		for( int i = 0; i < semanticDocuments.length; i++ ) {
			String ontoURI = semanticDocuments[i];
			String[] entities = entityEngine.getEntitiesByKeyword(ontoURI, keyword);
			store(ontoURI, entities);
		}
	}
	
	public String getServiceProperties() {
		String result = "";
		Set<String> keys = entityOntologyMap.keySet();
		Iterator<String> iterator = keys.iterator();
		while( iterator.hasNext() ) {
			String entity = iterator.next();
			result += entity;
			LinkedList<String> ontologies = entityOntologyMap.get(entity);
			for( String ontoURI : ontologies ) {
				result += "<:>" + ontoURI;
			}
			result += "<&>";
		}
		return result;
	}
	
	private void store(String ontoURI, String[] entitiesURI) throws RemoteException {
		LinkedList<String> ontologiesList = null;
		String type = "";
		for( String entityURI : entitiesURI ) {
			type = entityEngine.getType(ontoURI, entityURI);
			entityURI = type + "<:>" + entityURI;
			if( entityOntologyMap.containsKey(entityURI) ) {
				ontologiesList = entityOntologyMap.get(entityURI);
				ontologiesList.add(ontoURI);
				entityOntologyMap.put(entityURI, ontologiesList);
			} else {
				ontologiesList = new LinkedList<String>();
				ontologiesList.add(ontoURI);
				entityOntologyMap.put(entityURI, ontologiesList);
			}
		}
	}
	
}
