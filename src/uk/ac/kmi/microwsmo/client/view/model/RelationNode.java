package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RelationNode extends ModelNode implements Serializable {

    public RelationNode() { super(); }

    public RelationNode(String label, String URI) {
        super(label, URI);
    }

}
