package uk.ac.kmi.microwsmo.client.view;

import java.io.Console;
import java.util.List;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.controller.HRESTController;
import uk.ac.kmi.microwsmo.client.controller.ModelController;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

final class ServiceStructureContextMenu extends Menu {

	private MenuItem delete;
	private MenuItem addModelRef;
	private MenuItem rename;
	private static BaseTreeItem item;
	
	public ServiceStructureContextMenu() {
		super();
		setID(ComponentID.SERVICE_STRUCTURE_CONTEXT_MENU);
		initComponents();
		addComponents();
		//addListener(Events.OnClick, MicroWSMOeditor.getController());
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
		rename = new MenuItem("Rename");
		rename.addSelectionListener(new SelectionListener<MenuEvent>() {  
		 public void componentSelected(MenuEvent ce) {  
			 rename();
		 }  
		}); 
		
		delete = new MenuItem("Delete");
		delete.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 Controller.deleteAnnotation();
			 }  
		}); 
		
		addModelRef = new MenuItem("Add Model Reference");
		addModelRef.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 addModelReference();
			 }  
		}); 
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(rename);
		add(delete);
		add(addModelRef);
	}
	
	private void rename(){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		item = selectedItem;
		
		RenameDialog renameDialog = new RenameDialog(selectedItem.getID());
		renameDialog.setModal(true);
		renameDialog.show();
	}
	
	public static void renameProperty(String newName){
		item.setID(newName);
		ModelController.renameProperty(newName);
	}
	
	private void addModelReference(){
		
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		AddModelReferenceDialog addModelReferenceDialog = new AddModelReferenceDialog(selectedItem.getID());
		addModelReferenceDialog.setModal(true);
		addModelReferenceDialog.show();
	}
	
}
