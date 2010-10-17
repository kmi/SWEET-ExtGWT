package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.view.model.*;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.ComponentID.AnnotationClass;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;

/**
 * 
 * @author KMi, The Open University
 */
public final class OwnOntologyTree extends Tree {

	//private SemanticAnnotationContextMenu menu;
	private TreeItem root;
	
	public OwnOntologyTree() {
		//super(ComponentID.OWNONTOLOGY_TREE);
		setId(ComponentID.OWNONTOLOGY_TREE);
		initComponents();
		addListener(Events.OnDoubleClick, MicroWSMOeditor.getController());
		addComponents();
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		//menu = new SemanticAnnotationContextMenu();
		//menu.setID(ComponentID.OWNONTOLOGY_TREE_CONTEXT_MENU);
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		initTree();
		//setContextMenu(menu);
	}

	private void initTree() {
		//store.removeAll();
		root = new TreeItem("Loaded Ontologies");
		root.setIconStyle(CSSIconImage.ROOT);
		root.setId("root");
		add(root);
	}
	
	//@Override
	//public void setDefaultState() {
		//store.removeAll();
	//}
}
