package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AxiomNode extends ModelNode implements Serializable {

    public AxiomNode() { super(); }
    
    public AxiomNode(String label, String URI) {
        super(label, URI);
    }

    public boolean isLeaf() {
        return true;
    }

}
