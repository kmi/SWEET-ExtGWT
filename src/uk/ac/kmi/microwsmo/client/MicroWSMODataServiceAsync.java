package uk.ac.kmi.microwsmo.client;

import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MicroWSMODataServiceAsync {
 
    public void buildModelFromOntology(String restURL, AsyncCallback<OntologyNode> callBack) throws Exception;
}
