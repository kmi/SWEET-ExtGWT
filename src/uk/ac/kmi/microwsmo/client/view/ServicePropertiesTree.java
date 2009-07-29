package uk.ac.kmi.microwsmo.client.view;

import com.extjs.gxt.ui.client.event.Events;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

/**
 * 
 * @author Simone Spaccarotella
 */
public final class ServicePropertiesTree extends SemanticAnnotationTree {
	
	/**
	 * Creates a new tree.
	 */
	public ServicePropertiesTree() {
		super(ComponentID.SERVICE_PROPERTIES_TREE);
		setContextMenuID(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU);
		addListener(Events.OnClick, MicroWSMOeditor.getController());
	}
	
	public void setDefaultState() {
		store.removeAll();
	}	
	
}