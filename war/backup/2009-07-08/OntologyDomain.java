package uk.ac.kmi.microwsmo.server;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import uk.ac.open.kmi.watson.clientapi.EntitySearch;

public class OntologyDomain {

	private EntitySearch entityEngine;
	private String keyword;
	private Map<String, LinkedList<String>> ontologyEntityMap;
	
	public OntologyDomain(EntitySearch entityEngine) {
		this.entityEngine = entityEngine;
		keyword = "";
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public void generateOntologyDomain(String[] semanticDocuments) throws RemoteException {
		ontologyEntityMap = new LinkedHashMap<String, LinkedList<String>>();
		for( String ontoURI : semanticDocuments ) {
			String[] entities = entityEngine.getEntitiesByKeyword(ontoURI, keyword);
			LinkedList<String> entitiesList = new LinkedList<String>();
			for( String entityURI : entities ) {
				String type = entityEngine.getType(ontoURI, entityURI);
				entitiesList.add(type + "<:>" + entityURI);
			}
			ontologyEntityMap.put(ontoURI, entitiesList);
		}
	}
	
	public String getOntologyDomain() {
		String result = "";
		Set<String> keys = ontologyEntityMap.keySet();
		Iterator<String> iterator = keys.iterator();
		while( iterator.hasNext() ) {
			String ontology = iterator.next();
			result += ontology;
			LinkedList<String> entities = ontologyEntityMap.get(ontology);
			for( String entityURI : entities ) {
				result += "<:>" + entityURI;
			}
			result += "<&>";
		}
		return result;
	}
	
	
}
