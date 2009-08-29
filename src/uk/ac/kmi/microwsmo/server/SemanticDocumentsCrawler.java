package uk.ac.kmi.microwsmo.server;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import uk.ac.open.kmi.watson.clientapi.OntologySearch;
import uk.ac.open.kmi.watson.clientapi.OntologySearchServiceLocator;
import uk.ac.open.kmi.watson.clientapi.WatsonService;

public class SemanticDocumentsCrawler {

	/** the engine for the watson queries  */
	private OntologySearch ontoEngine;
	private SubsetStore subsetStore;
	
	private static final int DELTA = 10;
	
	public SemanticDocumentsCrawler() throws ServiceException {
		ontoEngine = new OntologySearchServiceLocator().getUrnOntologySearch();
		subsetStore = new SubsetStore();
	}
	
	/**
	 * Retrieve the semantic documents related to the keyword.
	 * 
	 * @param keyword the keyword which is contained into the retrieved ontologies.
	 * @throws RemoteException
	 */
	public String[] searchByKeywordPaginated(String keyword) throws RemoteException {
		Subset subset = subsetStore.getSubset(keyword);
		String[] keywords = {keyword};
		int scopeModifier = WatsonService.LOCAL_NAME + WatsonService.LABEL + WatsonService.LITERAL;
		int entityTypeModifier = WatsonService.CLASS + WatsonService.PROPERTY + WatsonService.INDIVIDUAL;
		int matchTechnique = WatsonService.EXACT_MATCH;
		String[] result = ontoEngine.getSemanticContentByKeywordsWithRestrictionsPaginated(keywords, scopeModifier, entityTypeModifier, matchTechnique, subset.getStartPosition(), DELTA);
		if( subset.isFirstSearch() ) {
			subset.setSize(result[result.length - 1]);
			subset.setFirstSearch(false);
		}
		subset.incrementStartPositionOf(DELTA);
		if( subset.getStartPosition() >= subset.getSize() ) {
			result[result.length - 1] = "0";
		} else {
			result[result.length - 1] = String.valueOf(subset.getSize() - subset.getStartPosition());
		}
		return result;
	}
	
}
