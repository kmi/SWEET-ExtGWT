package uk.ac.kmi.microwsmo.client.view;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

/**
 * 
 * @author KMi, The Open University
 */
public final class DomainOntologiesTree extends SemanticAnnotationTree {

	/**
	 * 
	 */
	public DomainOntologiesTree() {
		super(ComponentID.DOMAIN_ONTOLOGIES_TREE);
		setContextMenuID(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU);
		addListener(Events.OnClick, MicroWSMOeditor.getController());
	}
	
	@Override
	public void setDefaultState() {
		store.removeAll();
	}
	
	public BaseTreeItem getChildOfConcepts(BaseTreeItem parent) {
		return (BaseTreeItem) store.getChild(parent, 0);
	}

}
