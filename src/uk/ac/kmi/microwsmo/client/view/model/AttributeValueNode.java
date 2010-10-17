package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AttributeValueNode extends ModelNode implements Serializable {

    public AttributeValueNode() { super(); }
    
    public AttributeValueNode(String label, String URI) {
        super(label, URI);
    }

    public boolean isLeaf() {
        return true;
    }
}
