package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;

/**
 * This container layout the two button
 * on the same row in the middle of it.
 * 
 * @author Simone Spaccarotella
 */
final class ButtonPanel extends HorizontalPanel {

	/* ***** the components ***** */
	
	private SaveButton save;
	private ExportButton export;
	
	/* *****  ***** */
	
	/**
	 * Create a new button panel.
	 */
	public ButtonPanel() {
		super();
		setID(ComponentID.BUTTON_PANEL);
		setProperties();
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
	 * Sets the properties of this container.
	 */
	private void setProperties() {
		setBorders(true);
		/*
		 * enlarge the width and the height, 
		 * of the cell, so the component can
		 * be setted in the center of all the panel
		 */
		setTableWidth("100%");
		setTableHeight("100%");
		// sets the alignment of the panel to the center
		setHorizontalAlign(HorizontalAlignment.CENTER);
		setVerticalAlign(VerticalAlignment.MIDDLE);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		save = MicroWSMOeditor.getSaveButton();
		export = MicroWSMOeditor.getExportButton();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(save);
		add(export);
	}
	
}