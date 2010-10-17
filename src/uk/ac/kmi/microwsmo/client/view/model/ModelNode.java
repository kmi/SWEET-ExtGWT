package uk.ac.kmi.microwsmo.client.view.model;

import java.io.Serializable;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

@SuppressWarnings("serial")
public class ModelNode extends BaseTreeModel implements Serializable {

    public ModelNode() {}
    
    public ModelNode(String label, String URI) {
        set("lab", label);
        set("uri", URI);
        set("fnd", label + " (" + URI + ")");
    }
    
    public String getURI() {
        return get("uri");
    }
    
    public String toString() {
        return get("lab");
    }
    
    public void setTooltip(String data) {
        set("tooltip", data);
    }
    public String getTooltip() {
        return get("tooltip");
    }
    
    public boolean equals(Object o) {
        if (false == o instanceof ModelNode) {
            return false;
        }
        return getURI().equals(((ModelNode)o).getURI());
    }
}
