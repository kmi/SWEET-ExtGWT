package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class InstanceNode extends ModelNode implements Serializable {

    public InstanceNode() { super(); }
    
    public InstanceNode(String label, String URI) {
        super(label, URI);
    }

    public boolean isLeaf() {
        return true;
    }

}
