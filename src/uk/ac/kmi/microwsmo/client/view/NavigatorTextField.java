package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.form.TextField;

/**
 * 
 * @author KMi, The Open University
 */
public final class NavigatorTextField extends TextField<String> {

	/**
	 * 
	 */
	public NavigatorTextField() {
		super();
		setID(ComponentID.NAVIGATOR_TEXT_FIELD);
		setEmptyText("insert the the web page's URI (e.g. http://www.domain.org) and press Enter");
		addListener(Events.OnKeyPress, MicroWSMOeditor.getController());
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
