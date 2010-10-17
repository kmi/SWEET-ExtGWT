package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConceptNode extends ModelNode implements Serializable {

    public ConceptNode() { super(); }

    public ConceptNode(String label, String URI) {
        super(label, URI);
    }

}
