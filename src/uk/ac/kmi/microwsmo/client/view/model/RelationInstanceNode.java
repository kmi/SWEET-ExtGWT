package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RelationInstanceNode extends ModelNode implements Serializable {

    public RelationInstanceNode() { super(); }
    
    public RelationInstanceNode(String label, String URI) {
        super(label, URI);
    }

    public boolean isLeaf() {
        return true;
    }

}
