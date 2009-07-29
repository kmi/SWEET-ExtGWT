package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.TabPanel;

/**
 * This container have a tabbed layout, in this way
 * is possible to add component simply. It is placed
 * inside the lower side panel. 
 * 
 * @author Simone Spaccarotella
 */
final class LowerSideTabbedPanel extends TabPanel {

	/* ***** the components ***** */
	
	private InfoTabItem infoTab;
	
	/* *****  ***** */
	
	/**
	 * Create a new container with a tab layout.
	 */
	public LowerSideTabbedPanel() {
		super();
		setID(ComponentID.LOWER_SIDE_TABBED_PANEL);
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
		setCloseContextMenu(true);
		setTabScroll(true);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		infoTab = new InfoTabItem();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(infoTab);
	}
	
	
	
}