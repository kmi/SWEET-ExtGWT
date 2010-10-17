package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * Is the panel which contains the tree view of the hREST
 * schema annotation of the web page.
 * 
 * @author KMi, The Open University
 */
final class ServiceStructurePanel extends ContentPanel {

	/* ***** the components ***** */
	
	// the hREST tag tree
	private ServiceStructureTree tree;
	
	private FitLayout layout;
	
	/* *****  ***** */
	
	/**
	 * Creates a new panel with a tree inside. This tree
	 * represent the hREST schema annotation of the web page
	 */
	public ServiceStructurePanel() {
		super();
		setID(ComponentID.SERVICE_STRUCTURE_PANEL);
		setLayout();
		setHeaderVisible(false);
		initComponents();
		addComponents();
	}
	
	/**
	 * Sets a fit layout.
	 */
	private void setLayout() {
		layout = new FitLayout();
		setLayout(layout);
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
