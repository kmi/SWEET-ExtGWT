package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

final class ServiceStructureContextMenu extends Menu {

	private MenuItem delete;
	
	public ServiceStructureContextMenu() {
		super();
		setID(ComponentID.SERVICE_STRUCTURE_CONTEXT_MENU);
		initComponents();
		addComponents();
		addListener(Events.OnClick, MicroWSMOeditor.getController());
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
	 * Initializes the components.
	 */
	private void initComponents() {
		delete = new MenuItem("Delete");
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(delete);
	}
	
}
