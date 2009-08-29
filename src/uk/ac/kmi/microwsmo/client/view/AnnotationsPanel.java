package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

final class AnnotationsPanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	private FitLayout layout;
	
	/* ***** the components ***** */
	
	private AnnotationsTree tree;
	
	/* *****  ***** */
	
	public AnnotationsPanel() {
		super();
		setID(ComponentID.ANNOTATIONS_PANEL);
		setLayout();
		setHeading("Annotations Overview");
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
		tree = MicroWSMOeditor.getAnnotationsTree();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(tree);
	}
	
}