package uk.ac.kmi.microwsmo.client.view;

import java.util.HashMap;
import java.util.List;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.controller.ControllerToolkit;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.Message;
import uk.ac.kmi.microwsmo.client.util.ComponentID.AnnotationClass;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.extjs.gxt.ui.client.widget.menu.SeparatorMenuItem;

final class ServiceStructureContextMenu extends Menu {

	private MenuItem delete;
	private MenuItem addModelRef;
	private MenuItem rename;
	private MenuItem addSchemaMapping;
	
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
		rename.setId("Rename");
		rename.setItemId("Rename");
		rename.addSelectionListener(new SelectionListener<MenuEvent>() {  
		 public void componentSelected(MenuEvent ce) {  
			 rename();
		 }  
		}); 
		
		delete = new MenuItem("Delete");
		delete.setId("Delete");
		delete.setItemId("Delete");
		delete.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 
				 deleteAnnotation();
			 }  
		}); 
		
		addModelRef = new MenuItem("Add Model Reference");
		addModelRef.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 addModelReference();
			 }  
		}); 
		
		addSchemaMapping = new MenuItem("Add Schema Mapping");
		addSchemaMapping.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 addSchemaReference();
			 }  
		}); 
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(rename);
		add(new SeparatorMenuItem());
		add(addModelRef);
		add(addSchemaMapping);
		add(new SeparatorMenuItem());
		add(delete);
	}
	
	private void deleteAnnotation(){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		//BaseTreeItem parent = tree.getParentOf(selectedItem);
			
		if(selectedItem.getNodeType().equals(AnnotationClass.TREEROOT)){
			 MessageBox.alert("MicroWSMO editor - delete", "You cannot delete the root element.", null);
		 } else {
			//|| (selectedItem.getNodeType() != AnnotationClass.MODELREFERENCE && 
				//	parent.getNodeType() != AnnotationClass.ENTITY)){
			 
			 Controller.deleteAnnotation();
		}
	}
	
	private void rename(){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		if(selectedItem.getNodeType().equals(AnnotationClass.TREEROOT)
				|| selectedItem.getNodeType().equals(AnnotationClass.MODELREFERENCE)
				|| selectedItem.getNodeType().equals(AnnotationClass.SCHEMAMAPPING)){
			
			 if(selectedItem.getNodeType().equals(AnnotationClass.TREEROOT)){
				 MessageBox.alert("MicroWSMO editor - rename", "You cannot rename the root element.", null);
			 } else if (selectedItem.getNodeType().equals(AnnotationClass.MODELREFERENCE)
				|| selectedItem.getNodeType().equals(AnnotationClass.SCHEMAMAPPING)){
				 MessageBox.alert("MicroWSMO editor - rename", "You cannot rename this element. Please use the Add Model " +
						 "Reference or Add Schema Mapping options on the parent element.", null);
			 }
			 
		 } else {
			 RenameDialog renameDialog = new RenameDialog(selectedItem.getID());
				renameDialog.setModal(true);
				renameDialog.show();
		}
	}
	
	public static void renameProperty(String newName){
		
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		newName = newName.replaceAll(" ", "_");
		newName = newName.replaceAll("\\s", "_");
		
		//Update HTML DOM
		ControllerToolkit.renameProperty(selectedItem.getID(), newName);
		
		//Update Tree
		selectedItem.setID(newName);
		selectedItem.setName(newName);
		
		//Force to refresh the tree
		tree.recalculate();
		tree.collapseAll();
		tree.expandAll();
		tree.repaint();
		
		
		tree.expandAll();
	}
	
	private void addModelReference(){
		
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		if(selectedItem.getNodeType() != AnnotationClass.INPUT && selectedItem.getNodeType() != AnnotationClass.OUTPUT){
			
			AddModelReferenceDialog addModelReferenceDialog = new AddModelReferenceDialog(selectedItem.getModelReference());
			addModelReferenceDialog.setModal(true);
			addModelReferenceDialog.show();
			
		} else {
			Message.show(Message.ADDMREF1);
		}
	}
	
	private void addSchemaReference(){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		if (selectedItem.getNodeType() == AnnotationClass.INPUT){
			
			AddLiftingLoweringSchema addModelReferenceDialog = new AddLiftingLoweringSchema("lowering", selectedItem.getLoweringSchemaMapping());
			addModelReferenceDialog.setModal(true);
			addModelReferenceDialog.show();
			
		} else if (selectedItem.getNodeType() == AnnotationClass.OUTPUT){
			
			AddLiftingLoweringSchema addModelReferenceDialog = new AddLiftingLoweringSchema("lifting", selectedItem.getLiftingSchemaMapping());
			addModelReferenceDialog.setModal(true);
			addModelReferenceDialog.show();
			
		} else {
			Message.show(Message.ADDLFSCHEMA);
		}
	}
	
	public static void createSchemaReference(String newUri){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		if (selectedItem.getNodeType() == AnnotationClass.INPUT){
			//Update HTML DOM
			ControllerToolkit.addSchemaMapping(selectedItem.getID(), newUri, "lower");
			tree.addItem(selectedItem, new BaseTreeItem(selectedItem.getID() + "_lowering:" + newUri, CSSIconImage.RDF, AnnotationClass.SCHEMAMAPPING));
			tree.sortBy("nodeType");
			
			//Update Tree
			selectedItem.setLoweringSchemaMapping(newUri);
			
			//Force to refresh the tree
			forceFrefreshTree(tree);
			
		} else if (selectedItem.getNodeType() == AnnotationClass.OUTPUT) {
			//Update HTML DOM
			ControllerToolkit.addSchemaMapping(selectedItem.getID(), newUri, "lift");
			tree.addItem(selectedItem, new BaseTreeItem(selectedItem.getID() + "_lifting:" + newUri, CSSIconImage.RDF, AnnotationClass.SCHEMAMAPPING));
			tree.sortBy("nodeType");
			
			//Update Tree
			selectedItem.setLiftingSchemaMapping(newUri);
			
			//Force to refresh the tree
			forceFrefreshTree(tree);
			
		} else {
			Message.show(Message.ADDLFSCHEMA);
		}
	}
	
	public static void createModelReference(String newUri){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		//Update HTML DOM
		ControllerToolkit.addModelReference(selectedItem.getID(), newUri);
		tree.addItem(selectedItem, new BaseTreeItem(selectedItem.getID() + ":" + newUri, CSSIconImage.MREF, AnnotationClass.MODELREFERENCE));
		tree.sortBy("nodeType");
		
		//Update Tree
		selectedItem.setModelReference(newUri);
		
		//Force to refresh the tree
		forceFrefreshTree(tree);
	}
	
	public static void updateModelReference(String newUri){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		//Update HTML DOM
		ControllerToolkit.updateModelReference(selectedItem.getID(), newUri);
		
		//Update Tree
		List<ModelData> children = tree.getChildren(selectedItem);
		if( children != null ) {
			for( ModelData childElement : children ) {
				BaseTreeItem childElementItem = (BaseTreeItem) childElement;
				
				if(childElementItem.getNodeType().equals(AnnotationClass.MODELREFERENCE)){
					childElementItem.setID(selectedItem.getID() + ":" + newUri);
					childElementItem.setName(selectedItem.getID() + ":" + newUri);
				}
			}
		}
		
		selectedItem.setModelReference(newUri);
		
		//Force to refresh the tree
		forceFrefreshTree(tree);
	}
	
	public static void updateSchemaReference(String newUri){
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		
		
		//Update Tree
		List<ModelData> children = tree.getChildren(selectedItem);
		if( children != null ) {
			for( ModelData childElement : children ) {
				BaseTreeItem childElementItem = (BaseTreeItem) childElement;
				
				if(childElementItem.getNodeType().equals(AnnotationClass.SCHEMAMAPPING)){
					if(selectedItem.getNodeType().equals(AnnotationClass.INPUT)){
						
						childElementItem.setID(selectedItem.getID() + "_lifting:" + newUri);
						childElementItem.setName(selectedItem.getID() + "_lifting:" + newUri);
						
						selectedItem.setLiftingSchemaMapping(newUri);
						
						//Update HTML DOM
						ControllerToolkit.updateSchemaReference(selectedItem.getID(), newUri, "lift");
					} else {
						
						childElementItem.setID(selectedItem.getID() + "_lowering:" + newUri);
						childElementItem.setName(selectedItem.getID() + "_lowering:" + newUri);
						
						selectedItem.setLoweringSchemaMapping(newUri);
						
						//Update HTML DOM
						ControllerToolkit.updateSchemaReference(selectedItem.getID(), newUri, "lower");
					}
				}
			}
		}
		
		//Force to refresh the tree
		forceFrefreshTree(tree);
	}
	
	private static void forceFrefreshTree(ServiceStructureTree tree){
		tree.recalculate();
		tree.collapseAll();
		tree.expandAll();
		tree.repaint();
		
		tree.expandAll();
	}
	
}
