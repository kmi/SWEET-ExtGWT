package uk.ac.kmi.microwsmo.server;

import java.rmi.RemoteException;

import uk.ac.open.kmi.watson.clientapi.OntologySearch;
import uk.ac.open.kmi.watson.clientapi.WatsonService;

public class SemanticDocumentsCrawler {
	
	/** the engine for the watson queries  */
	private OntologySearch ontoEngine;
	
	private String keyword;
	private int startSubset;
	private int sizeSet;
	private boolean firstSearch;
	
	private static final int DELTA = 5;

	public String[] searchByKeywordPaginated(String keyword) throws RemoteException {
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
	
	public String[] searchByKeyword(String keyword) throws RemoteException {
		String[] keywords = {keyword};
		int scopeModifier = WatsonService.LOCAL_NAME + WatsonService.LABEL + WatsonService.LITERAL;
		int entityTypeModifier = WatsonService.CLASS + WatsonService.PROPERTY + WatsonService.INDIVIDUAL;
		int matchTechnique = WatsonService.EXACT_MATCH;
		return ontoEngine.getSemanticContentByKeywordsWithRestrictions(keywords, scopeModifier, entityTypeModifier, matchTechnique);
	}
	
}
