package uk.ac.kmi.microwsmo.server.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SemanticTreesModel {

	private Map<String, String> serviceProperties;
	private Map<String, String> domainOntologies;

	public SemanticTreesModel() {
		serviceProperties = new HashMap<String, String>();
		domainOntologies = new HashMap<String, String>();
	}

	public void addServiceProperties(String keyword, String value) {
		// extract the number at the beginning of the string
		String[] splitted = value.split("<!>");
		String number = splitted[0];
		// if the number is not equal to zero
		if( !number.equals("0") ) {
			value = splitted[1] + "<&>viewMore";
		} else {
			value = splitted[1];
		}
		String result = "";
		// if the string is already stored
		if( serviceProperties.containsKey(keyword) ) {
			result = serviceProperties.get(keyword);
			result = result.split("<&>viewMore")[0];
			result += "<&>" + value;
			serviceProperties.put(keyword, result);
		} else {
			serviceProperties.put(keyword, value);
		}
	}

	public String getServiceProperties() {
		String result = "";
		Iterator<String> i = serviceProperties.keySet().iterator();
		while( i.hasNext() ) {
			String term = i.next();
			result += term + "<!>" + serviceProperties.get(term) + "<;>";			
		}
		return result;
	}

	public void addDomainOntologies(String keyword, String value) {
		value = value.split("<!>")[1];
		String result = "";
		// if the string is already stored
		if( domainOntologies.containsKey(keyword) ) {
			result = domainOntologies.get(keyword);
			result += "<&>" + value;
			domainOntologies.put(keyword, result);
		} else {
			domainOntologies.put(keyword, value);
		}
	}
	
	public String getDomainOntologies() {
		String result = "";
		Iterator<String> i = domainOntologies.keySet().iterator();
		while( i.hasNext() ) {
			String term = i.next();
			result += term + "<!>" + domainOntologies.get(term) + "<;>";			
		}
		return result;
	}

	public void showServiceProperties(String result) {
		String[] terms = result.split("<;>");
		for( String term : terms ) {
			String[] elements = term.split("<!>");
			System.out.println(elements[0]);
			String[] entities = elements[1].split("<&>");
			for( String entity : entities ) {
				if( !entity.equals("viewMore") ) {
					String[] chunk = entity.split("<:>");
					System.out.println("--- " + chunk[0] + " - " + chunk[1]);
					for( int i = 2; i < chunk.length; i++ ) {
						System.out.println("--- --- " + chunk[i]);
					}
				}
			}
			System.out.println();
		}
	}

	public void showDomainOntologies(String result) {
		String[] terms = result.split("<;>");
		for( String term : terms ) {
			String termName = term.split("<!>")[0];
			String[] ontologies = term.split("<!>")[1].split("<&>");
			System.out.println(termName);
			for( String ontology : ontologies ) {
				String[] elements = ontology.split("<:>");
				System.out.println("--- " + elements[0]);
				for( int i = 1; i < elements.length; i++ ) {
					if( (i % 2) != 0 ) {
						System.out.print("--- --- " + elements[i] + " - ");
					} else {
						System.out.println(elements[i]);
					}
				}
			}
		}
	}

}
