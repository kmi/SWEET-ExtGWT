package uk.ac.kmi.microwsmo.client;

import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;

import com.google.gwt.user.client.rpc.RemoteService;


public interface MicroWSMODataService extends RemoteService {
    
    public OntologyNode buildModelFromOntology(String restURL) throws Exception;
}
