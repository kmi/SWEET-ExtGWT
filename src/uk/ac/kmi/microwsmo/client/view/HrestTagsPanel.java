package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * Is the panel which contains the tree view of the hREST tags.
 * 
 * @author KMi, The Open University
 */
final class HrestTagsPanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	private FitLayout layout;
	
	/* ***** the components ***** */
	
	private HrestTagsTree tree;
	
	/* *****  ***** */
	
	/**
	 * Creates a new panel with a tree inside.
	 * This tree represent the tags hREST.
	 */
	public HrestTagsPanel() {
		super();
		setID(ComponentID.HREST_TAGS_PANEL);
		setLayout();
		setHeading("Service Properties");
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
	 * Sets a fit layout.
	 */
	private void setLayout() {
		layout = new FitLayout();
		setLayout(layout);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		tree = MicroWSMOeditor.getHrestTagsTree();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(tree);
	}

}