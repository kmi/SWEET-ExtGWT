package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;

/**
 * This container is at the bottom of the editor. Is 
 * placed under the web pages display.
 * 
 * @author KMi, The Open University
 */
final class LowerSidePanel extends ContentPanel {

	/* ***** the components ***** */
	
	private LowerSideTabbedPanel tabbedPanel;
	
	/* *****  ***** */
	
	/**
	 * Create a new container for the lower side of the
	 * editor.
	 */
	public LowerSidePanel() {
		super();
		setID(ComponentID.LOWER_SIDE_PANEL);
		setHeading("Details");
		// for now this tabbed panel is disabled
		/*
			tabbedPanel = new LowerSideTabbedPanel();
			add(tabbedPanel);
		*/
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
	
}