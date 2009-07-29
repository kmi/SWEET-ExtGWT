package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;

/**
 * Is the panel which contains the tree view of the hREST
 * schema annotation of the web page.
 * 
 * @author Simone Spaccarotella
 */
final class ServiceStructurePanel extends ContentPanel {

	/* ***** the components ***** */
	
	// the hREST tag tree
	private ServiceStructureTree tree;
	
	/* *****  ***** */
	
	/**
	 * Creates a new panel with a tree inside. This tree
	 * represent the hREST schema annotation of the web page
	 */
	public ServiceStructurePanel() {
		super();
		setID(ComponentID.SERVICE_STRUCTURE_PANEL);
		setHeaderVisible(false);
		initComponents();
		addComponents();
	}
	
	/**
	 * Sets the id and the item id.
	 * 
	 * @param id a string which represent the component univocally.
	 */
	private void setID(String id) {
		setId(id);
		setItemId(id);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		tree = MicroWSMOeditor.getServiceStructureTree();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(tree);
	}
	
}
