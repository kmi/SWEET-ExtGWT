package uk.ac.kmi.microwsmo.client.util;

import uk.ac.kmi.microwsmo.client.view.model.AttributeNode;
import uk.ac.kmi.microwsmo.client.view.model.AttributeValueNode;
import uk.ac.kmi.microwsmo.client.view.model.AxiomNode;
import uk.ac.kmi.microwsmo.client.view.model.ConceptNode;
import uk.ac.kmi.microwsmo.client.view.model.InstanceNode;
import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;
import uk.ac.kmi.microwsmo.client.view.model.RelationInstanceNode;
import uk.ac.kmi.microwsmo.client.view.model.RelationNode;

import com.extjs.gxt.ui.client.widget.tree.TreeItem;

public class TreeVisualizer {

	
	 public static void setServiceModelIcons(TreeItem root) {
	        
        if (root.getModel() instanceof OntologyNode) {
            root.setIconStyle(CSSIconImage.ROOT);
        }
        else if (root.getModel() instanceof ConceptNode) {
            root.setIconStyle(CSSIconImage.ONTO_CLASS);
        }
        else if (root.getModel() instanceof InstanceNode) {
            root.setIconStyle(CSSIconImage.ONTO_INDIVIDUAL);
        }
        else if (root.getModel() instanceof AttributeNode) {
            root.setIconStyle(CSSIconImage.ONTO_PROPERTY);
        }
        else if (root.getModel() instanceof AxiomNode) {
            root.setIconStyle(CSSIconImage.TERM);
        }
        else if (root.getModel() instanceof AttributeValueNode) {
            root.setIconStyle(CSSIconImage.TERM);
        }
        else if (root.getModel() instanceof RelationNode) {
            root.setIconStyle(CSSIconImage.TERM);
        }
        else if (root.getModel() instanceof RelationInstanceNode) {
            root.setIconStyle(CSSIconImage.TERM);
        }
	 }
}
