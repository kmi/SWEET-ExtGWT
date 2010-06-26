package uk.ac.kmi.microwsmo.server.logger;

import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;
//import org.openrdf.;

import uk.ac.kmi.microwsmo.server.logger.RDFRepositoryException;
import uk.ac.kmi.microwsmo.server.logger.URI;
//import uk.ac.open.kmi.iserve.model.common.URI;

public class RDFRepositoryConnector {

	private Repository sesameRepository;

	//String serverUri = "http://example.org/sesame2";
	//String repositoryName = "example-db";

	
	public RDFRepositoryConnector(URI serverUri, String repositoryName) throws RDFRepositoryException {
		if ( null == serverUri || null == serverUri.toString() || "" == serverUri.toString() ||
				null == repositoryName || "" == repositoryName ) {
			throw new RDFRepositoryException("Can not initialize the connector");
		}
		sesameRepository  = new HTTPRepository(serverUri.toString(), repositoryName);
		try {
			sesameRepository.initialize();
		} catch (RepositoryException e) {
			throw new RDFRepositoryException("Can not initialize the connector");
		}
	}

	public RepositoryModel openRepositoryModel(URI contextUri) throws RDFRepositoryException {
		if ( null == sesameRepository ) {
			throw new RDFRepositoryException("The connector has not been initialized correctly");
		}
		RepositoryModel result = null;
		if ( contextUri != null && contextUri.toString() != null && contextUri.toString() != "" ) {
			URIImpl contextUriInRepository = new URIImpl(contextUri.toString());
			result = new RepositoryModel(contextUriInRepository, sesameRepository);
		} else {
			result = new RepositoryModel(sesameRepository);
		}
		if ( null == result ) {
			throw new RDFRepositoryException("Can not open the repository");
		}
		result.open();
		return result;
	}

	public RepositoryModel openRepositoryModel() throws RDFRepositoryException {
		if ( null == sesameRepository ) {
			throw new RDFRepositoryException("The connector has not been initialized correctly");
		}
		RepositoryModel result = new RepositoryModel(sesameRepository);
		if ( null == result ) {
			throw new RDFRepositoryException("Can not open the repository");
		}
		result.open();
		return result;
	}

	public void closeRepositoryModel(RepositoryModel modelToClose) {
		if ( modelToClose != null ) {
			modelToClose.close();
		}
	}

	public HTTPRepository getRepository() {
		return (HTTPRepository) sesameRepository;
	}

}
