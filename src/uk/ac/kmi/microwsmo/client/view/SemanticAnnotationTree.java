package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;


public abstract class SemanticAnnotationTree extends BaseTree {

	private SemanticAnnotationContextMenu contextMenu;

	
	public SemanticAnnotationTree(String id) {
		super(ComponentID.SEMANTIC_ANNOTATION_TREE + id);
		initComponents();
		addComponents();
		addListener(Events.OnContextMenu, MicroWSMOeditor.getController());
	}
	
	public void setContextMenuID(String id) {
		contextMenu.setID(id);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		contextMenu = new SemanticAnnotationContextMenu();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		setContextMenu(contextMenu);
	}
	
	@Override
	public synchronized void addRootItem(BaseTreeItem item) {
		super.addRootItem(item);
	}

	@Override
	public synchronized void addItem(BaseTreeItem parent, BaseTreeItem child) {
		super.addItem(parent, child);
	}
	
	@Override
	public synchronized void removeItem(BaseTreeItem item) {
		super.removeItem(item);
	}
	
	@Override
	public synchronized BaseTreeItem getItemById(String id) {
		return super.getItemById(id);
	}
	
	@Override
	public synchronized BaseTreeItem getSelectedItem() {
		return super.getSelectedItem();
	}
	
	@Override
	public synchronized BaseTreeItem getParentOf(BaseTreeItem item) {
		return super.getParentOf(item);
	}
	
	@Override
	public synchronized void removeItemById(String id) {
		super.removeItemById(id);
	}

}