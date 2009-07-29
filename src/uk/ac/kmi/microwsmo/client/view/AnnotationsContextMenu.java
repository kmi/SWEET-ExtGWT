package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * 
 * @author Simone Spaccarotella
 */
final class AnnotationsContextMenu extends Menu {

	private MenuItem delete;
	
	public AnnotationsContextMenu() {
		super();
		setID(ComponentID.ANNOTATIONS_CONTEXT_MENU);
		initComponents();
		addComponents();
		addListener(Events.OnClick, MicroWSMOeditor.getController());
	}
	
	/**
	 * Sets the id and the item id.
	 * 
	 * @param id a string which represent the component univocally.
	 */
	public void setID(String id) {
		setId(id);
		setItemId(id);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		delete = new MenuItem("Delete");
		delete.setIconStyle(CSSIconImage.RDF);
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(delete);
	}
	
}
