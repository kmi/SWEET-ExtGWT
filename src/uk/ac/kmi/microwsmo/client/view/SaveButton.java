package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.button.Button;

/**
 * This button save the hREST annotation in a
 * local file.
 * 
 * @author KMi, The Open University
 */
public final class SaveButton extends Button {

	/**
	 * Creates a new button.
	 */
	public SaveButton() {
		super("Save");
		setID(ComponentID.SAVE_BUTTON);
		setToolTip("Saves the hREST annotation added to the document");
		setListener();
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
	 * Sets the suitable listener.
	 */
	private void setListener() {
		addListener(Events.OnClick, MicroWSMOeditor.getController());
	}
	
}