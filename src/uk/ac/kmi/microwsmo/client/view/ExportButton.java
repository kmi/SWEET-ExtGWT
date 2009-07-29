package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.button.Button;

/**
 * This button export the semantic annotation
 * in a RDF format.
 * 
 * @author Simone Spaccarotella
 */
public final class ExportButton extends Button {

	/**
	 * Creates a new button.
	 */
	public ExportButton() {
		super("Export");
		setID(ComponentID.EXPORT_BUTTON);
		setToolTip("Exports the semantic annotation of this service in the RDF format");
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