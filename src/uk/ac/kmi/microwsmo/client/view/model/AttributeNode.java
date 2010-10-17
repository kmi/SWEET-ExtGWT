package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AttributeNode extends ModelNode implements Serializable {

    public AttributeNode() { super(); }
    
    public AttributeNode(String label, String URI, boolean isDatatype) {
        super(label, URI);
        setDatatypeProperty(isDatatype);
    }
    
    public String getRange() {
    	return get("range");
    }
    public void setRange(String newRangeURI) {
    	set("range", newRangeURI);
    }

    public boolean isLeaf() {
        return true;
    }
    
    public boolean isDatatypeProperty() {
    	if (get("datatypeprop") == null) {
    		return false;
    	}
    	return Boolean.valueOf(get("datatypeprop").toString());
    }
    public void setDatatypeProperty(boolean isDatatype) {
    	set("datatypeprop", String.valueOf(isDatatype));
    }
}
