package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;

/**
 * This container visualizes the web pages.
 * 
 * @author KMi, The Open University
 */
public final class WebPagesDisplay extends ContentPanel {
	
	/**
	 * Create a new web page visualizer.
	 */
	public WebPagesDisplay() {
		super();
		setID(ComponentID.WEB_PAGE_DISPLAY);
		setHeading("MicroWSMO Editor");
		setHeaderVisible(false);
		setUrl("proxyPage.html");
		layout();
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