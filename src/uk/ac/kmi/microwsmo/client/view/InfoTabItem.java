package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.TabItem;

/**
 * This panel will show info messages about the
 * operation the user need to do to annotate the
 * web pages. It will show dynamically a different
 * message related to the item which is pressed in
 * that moment.
 * 
 * @author Simone Spaccarotella
 */
final class InfoTabItem extends TabItem {
	
	/**
	 * Create a new empty panel.
	 */
	public InfoTabItem() {
		super("Info");
		setID(ComponentID.INFO_TAB_ITEM);
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
