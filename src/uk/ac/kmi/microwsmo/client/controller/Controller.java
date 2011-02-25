/**
 * Copyright (c) 2010 KMi, The Open University <m.maleshkova@open.ac.uk>
 * 
 * This file is part of SWEET
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 **/

package uk.ac.kmi.microwsmo.client.controller;

import java.util.List;

import org.eclipse.swt.internal.carbon.AlertStdCFStringAlertParamRec;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.Message;
import uk.ac.kmi.microwsmo.client.util.ComponentID.AnnotationClass;
import uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator;
import uk.ac.kmi.microwsmo.client.view.AddLiftingLoweringSchema;
import uk.ac.kmi.microwsmo.client.view.AnnotationsTree;
import uk.ac.kmi.microwsmo.client.view.BaseTreeItem;
import uk.ac.kmi.microwsmo.client.view.DomainOntologiesTree;
import uk.ac.kmi.microwsmo.client.view.HrestTagsTree;
import uk.ac.kmi.microwsmo.client.view.NavigatorTextField;
import uk.ac.kmi.microwsmo.client.view.SemanticAnnotationTree;
import uk.ac.kmi.microwsmo.client.view.ServicePropertiesTree;
import uk.ac.kmi.microwsmo.client.view.ServiceStructureTree;
import uk.ac.kmi.microwsmo.client.view.deprecated.DeprecatedBaseTreeItem;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Window;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * Is the controller of the Editor. This class implements
 * the listener of all the events. Every fired event is
 * retrieved by the unique instance of this class and managed
 * in different way, depend to the kind of the event.
 * 
 * @author Maria Maleshkova, The Open University
 * @author Simone Spaccarotella, The Open University
 */
public final class Controller extends KeyListener implements Listener<ComponentEvent> {
	
	// the code of the "Enter" key
	private static final int ENTER = 13;
	//public static Object currentSelection = null;
	
	//Logging
	//static Category cat = Category.getInstance(Controller.class.getName());
	//Logger logger = Logger.getLogger("Updater");

	/**
	 * Init the controller.
	 */
	public Controller() {
		ComponentID.IDGenerator.init();
		//cat.debug("Start of Controller()");
	}

	public static native void logEvents(String item, String method, String element) /*-{
	var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
	if( httpRequest != null ) {
		var parameters = "method=" +method + "&element=" + element + "&item=" + item;
		httpRequest.open("POST", "../logger", true);
		// set the header
		httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		httpRequest.setRequestHeader("content-length", parameters.length);
		httpRequest.setRequestHeader("connection", "close");
		httpRequest.send(parameters);
	}
	}-*/;
	
	/**
	 * Handles all the fired events.
	 */
	@Override
	public void handleEvent(ComponentEvent event) {
		// retrieve the component which fired the event
		Component sourceComponent = (Component) event.getSource();
		// retrieve the ID of the component
		String componentID = sourceComponent.getId();
		// retrieve the code of the event
		EventType eventType = event.getType();
		
		if( eventType.equals(Events.OnClick) ) {
			if( componentID.equals(ComponentID.QUERY_WATSON_BUTTON) ) {
				queryWatson();
			} else if( componentID.equals(ComponentID.SAVE_BUTTON)) {
				//save();
			} else if( componentID.equals(ComponentID.EXPORT_BUTTON) ) {
				export();
			} else if( componentID.equals(ComponentID.ANNOTATIONS_CONTEXT_MENU) ) {
				deleteEntity();
			} else if( componentID.contains(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU) ) {
				semanticAnnotation(componentID);
			} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
				semanticAnnotation(componentID);
			} else if( componentID.contains(ComponentID.SERVICE_PROPERTIES_TREE) ) {
				viewMore();
			} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_TREE) ) {
				retrieveConcepts();
			}  //else if( componentID.contains(ComponentID.SERVICE_STRUCTURE_CONTEXT_MENU) ) {
				//deleteAnnotation();
			//}
		} else if( eventType.equals(Events.OnDoubleClick) ) {
			if( componentID.equals(ComponentID.HREST_TAGS_TREE) ) {
				saveSelection();
				
				hrestAnnotation(sourceComponent);
			} else if(componentID.equals(ComponentID.OWNONTOLOGY_TREE)){
				
				//TODO Event
			}
			
		} else if( eventType.equals(Events.OnKeyPress) ) {
			
			int keyCode = event.getKeyCode();
			if( componentID.equals(ComponentID.NAVIGATOR_TEXT_FIELD) && keyCode == ENTER) {
				browse();
			}
		} else if( eventType.equals(Events.OnMouseOver) ) {
			if( componentID.contains(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU) ) {
				disableEnableContextMenu(componentID);
			}/* else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
				disableEnableContextMenu(componentID);
			} *//*else if( componentID.contains(ComponentID.SERVICE_STRUCTURE_CONTEXT_MENU) ) {
				filterContextMenu(componentID);
			}*/
		} 
	}
	
	public native void prova() /*-{
		var iframe = $doc.getElementById("webPageDisplay").lastChild.firstChild.firstChild.firstChild;
		iframe.contentWindow.callProxy('http://www.google.it');
	}-*/;
	
	public void updateEditorState() {
		ControllerToolkit.createSession();
		ControllerToolkit.updateState();
	}
	
	public void storeStateOf(String method) {
		Window.alert("update " + method);
	}
	
	/**
	 * Is called when the user insert an URI inside the navigation bar and press
	 * the "ENTER" key.
	 */
	public void browse() {
		NavigatorTextField textField = MicroWSMOeditor.getNavigatorTextField();
		// retrieves the URI
		String url = textField.getValue();
		// check if is a valid URI
		url = checkURL(url);
		// and set again the value inside the field (i.e. if the user insert www.domain.org without http://)
		textField.setValue(url);
		
		// call the proxy
		ControllerToolkit.callProxy(url);
		
		//Logger
		logEvents("ItemCallProxy", "callProxy", url);
		// reset the view and the model of the Service Structur tree and of the
		resetDefaultState();

		//cat.info(new java.util.Date() + " browse()");
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	private String checkURL(String url) {
		if( url == null || url.equals("") || url.matches("\\s") )
			return url;
		if( url.startsWith("http") ) {
			return url;
		} else {
			return "http://" + url;
		}
	}
	
	/**
	 * Reset the GUI every time the user change the page
	 * to annotate.
	 */
	private void resetDefaultState() {
		MicroWSMOeditor.getHrestTagsTree().setDefaultState();
		MicroWSMOeditor.getAnnotationsTree().setDefaultState();
		MicroWSMOeditor.getServiceStructureTree().setDefaultState();
		//MicroWSMOeditor.getOwnOntologyTree().setDefaultState();
		ComponentID.IDGenerator.init();
	}
	
	/**
	 * This method stores the currently selected text in a variable,
	 * so that if the text selection is lost (browser problems), it can be restored. 
	 * 
	 * @param component the item of the tree which fired the event.
	 */
	private void saveSelection() {
		// retrieve the item which fired the event
		//Message.show(Message.ERROR);//Toremove
		
		ControllerToolkit.setSelectionParams();
		//currentSelection = selection;
		//System.out.println(currentSelection.get(0) + "" + currentSelection.get(1));
		
		//cat.info(new java.util.Date() + " saveSelection()");
	}
	
	/**
	 * Annotate the text wrapped with the hREST tag. This
	 * method is used by every hREST tag item, and the kind
	 * of the annotation is choosen by the id of it. 
	 * 
	 * @param component the item of the tree which fired the event.
	 */
	private void hrestAnnotation(Component component) {
		saveSelection();
		
		HrestTagsTree tree = (HrestTagsTree) component;
		DeprecatedBaseTreeItem item = tree.getSelectedItem();
		// if is enabled can executes the annotation
		if( item.isEnabled() ) {
			String itemId = item.getID();
			HRESTController.annotate(itemId);
		}
		
		//cat.info(new java.util.Date() + " hrestAnnotation()");
	}
	
	public static void loadOntology(String componentID) {
		SemanticAnnotationTree tree = null;
		MessageBox.alert("componentId", componentID, null);
		if( componentID.contains(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getServicePropertiesTree();	
		} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getDomainOntologiesTree();
		}
		MessageBox.alert("tree", tree.toString(), null);
		
		BaseTreeItem treeItem = tree.getSelectedItem();
		
		MessageBox.alert("treeItem kind",treeItem.toString(), null);
		
		if( treeItem.getKind() == BaseTreeItem.ONTOLOGY) {
			MessageBox.alert("treeItem id", treeItem.getID().toString(), null);
			SemanticController.showOntoURLDialog(treeItem.getID());
		}
	}
	
	
	
	/**
	 * 
	 * @param componentID
	 */
	public static void semanticAnnotation(String componentID) {
		SemanticAnnotationTree tree = null;
		if( componentID.contains(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getServicePropertiesTree();	
		} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getDomainOntologiesTree();
		}
		final BaseTreeItem treeItem = tree.getSelectedItem();
		
		String keyword = tree.getParentOf(treeItem).getID();
		final String sel = ControllerToolkit.getTextSelected();
		
		final String id = treeItem.getID();
		final String icon = treeItem.getIcon();
		
		//was entity, key word paramm, parameter
		final String newId = IDGenerator.getID("parameter");
		
		//Update HTML DOM
		if( treeItem.getKind() == BaseTreeItem.ENTITY) {
			if( !(keyword.toLowerCase().trim().equals(sel.toLowerCase().trim())||
					keyword.toLowerCase().trim().contains(sel.toLowerCase().trim()))) {
				
				final Listener<MessageBoxEvent> l = new Listener<MessageBoxEvent>(){
				      public void handleEvent(MessageBoxEvent ce) {
				          Button btn = ce.getButtonClicked();
				          if (btn.getText().toLowerCase().equals("no")){
				          } else {
				        	SemanticController.annotate(id, icon, sel.toLowerCase().trim(), newId);
							
							//Update semantic description tree
							addSemanticAnnotationtoHRESTTree(sel, treeItem, newId);
							//Logger
							logEvents("ItemSemanticAnnotation", "semanticAnnotation", sel);
				          }
				        }
					};
						
					MessageBox.confirm("Url Error", "The highlighted keyword doe not correspond to the chosen entity! Do you want to do the annotation anyway?", l);
			} else {
				SemanticController.annotate(id, icon, sel.toLowerCase().trim(), newId);
				
				//Update semantic description tree
				addSemanticAnnotationtoHRESTTree(sel, treeItem, newId);
				//Logger
				logEvents("ItemSemanticAnnotation", "semanticAnnotation", sel);
			}
			
		}
	}
	
	public static void addSemanticAnnotationtoHRESTTree(String selection, BaseTreeItem item, String newId){
		String parentId = HRESTController.addSemanticEntityToTree();
		
		ServiceStructureTree treeHREST = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem parent = treeHREST.getItemById(parentId);
		
		//if(parent.getNodeType().equals(AnnotationClass.INPUT) || parent.getNodeType().equals(AnnotationClass.OUTPUT)){
			String label = selection.toLowerCase().trim(); 
			if(!checkUniqueId(label)){
				label = label + Math.floor(Math.random()*100);
			}
			
			treeHREST.addItem(parent, new BaseTreeItem(newId, CSSIconImage.TERM, AnnotationClass.ENTITY));
			BaseTreeItem newEntityElement = treeHREST.getItemById(newId);
			
			treeHREST.addItem(newEntityElement, new BaseTreeItem(label + ":" + item.getID(), CSSIconImage.MREF, AnnotationClass.MODELREFERENCE));
			treeHREST.sortBy("nodeType");
			
			newEntityElement.setModelReference(item.getID());
			
			//Force to refresh the tree
			treeHREST.recalculate();
			treeHREST.collapseAll();
			treeHREST.expandAll();
			treeHREST.repaint();
			
			treeHREST.expandAll();
	}
	
	public static boolean checkUniqueId(String item) {
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		List<ModelData> firstLevelList = tree.getChildren(tree.getItemById(AnnotationClass.TREEROOT));
		
		//Service nodes
		if( firstLevelList != null ) {
			for( ModelData firstLevelElement : firstLevelList ) {
				BaseTreeItem firstLevelItem = (BaseTreeItem) firstLevelElement;
				
				if(firstLevelItem.getID().trim() == item.trim()){
					return false;
				}
				
				//Operation nodes
				List<ModelData> secondLevelList = tree.getChildren(firstLevelItem);
				if( secondLevelList != null ) {
					for( ModelData secondLevelElement : secondLevelList ) {
						BaseTreeItem secondLevelItem = (BaseTreeItem) secondLevelElement;
						
						if(secondLevelItem.getID().trim() == item.trim()){
							return false;
						}
						
						//Input/Output nodes
						List<ModelData> thirdLevelList = tree.getChildren(secondLevelItem);
						if( thirdLevelList != null ) {
							for( ModelData thirdLevelElement : thirdLevelList ) {
								BaseTreeItem thirdLevelItem = (BaseTreeItem) thirdLevelElement;
								
								if(thirdLevelItem.getID().trim() == item.trim()){
									return false;
								}
							}
						}
			
					}
				}
				
			}
		}
		
		return true;
	}

	
	private void disableEnableContextMenu(String componentID) {
		SemanticAnnotationTree tree = null;
		if( componentID.contains(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getServicePropertiesTree();	
		} /*else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getDomainOntologiesTree();
		}*/
		BaseTreeItem item = tree.getSelectedItem();
		
		if( item.getKind() == BaseTreeItem.ENTITY) {
			//tree.getContextMenu().setVisible(true);
			MenuItem annotaiton = (MenuItem) tree.getContextMenu().getItemByItemId("0");
			//annotaiton.setEnabled(true);
			annotaiton.setVisible(true);
			//MenuItem loadontology = (MenuItem) tree.getContextMenu().getItemByItemId("1");
			//loadontology.setEnabled(false);
			//loadontology.setVisible(false);
			
			//MicroWSMOeditor.getSemanticAnnotationContextMenu().setVisible(true);
			/*MenuItem annotaiton1 = (MenuItem) MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("0");
			annotaiton1.setEnabled(true);
			annotaiton1.setVisible(true);
			MenuItem loadontology1 = (MenuItem) MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("1");
			loadontology1.setEnabled(false);
			loadontology1.setVisible(false);*/
		}
		/*else if (item.getKind() == BaseTreeItem.ONTOLOGY ){
			MenuItem annotaiton = (MenuItem) tree.getContextMenu().getItemByItemId("0");
			//annotaiton.setEnabled(false);
			annotaiton.setVisible(false);
			MenuItem loadontology = (MenuItem) tree.getContextMenu().getItemByItemId("1");
			//loadontology.setEnabled(true);
			loadontology.setVisible(true);*/
			
			//MicroWSMOeditor.getSemanticAnnotationContextMenu().setVisible(true);
			/*MenuItem annotaiton1 = (MenuItem) MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("0");
			annotaiton1.setEnabled(false);
			annotaiton1.setVisible(false);
			MenuItem loadontology1 = (MenuItem) MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("1");
			loadontology1.setEnabled(true);
			loadontology1.setVisible(true);*/
			
		//}
			else {
			tree.getContextMenu().setVisible(false);
			MicroWSMOeditor.getSemanticAnnotationContextMenu().setVisible(false);
		}
	}
	
	//Does not work properly
	/*private void filterContextMenu(String componentID) {
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();	
		
		BaseTreeItem item = tree.getSelectedItem();
		
		if( item.getNodeType().equals(AnnotationClass.MODELREFERENCE) 
				|| item.getNodeType().equals(AnnotationClass.SCHEMAMAPPING)) {
			
			tree.getContextMenu().getItemByItemId("Rename").disable();
			MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("Rename").disable();
		
		} else if (item.getNodeType().equals(AnnotationClass.MODELREFERENCE) || item.getID() == AnnotationClass.TREEROOT){
			
			if(tree.getParentOf(item).getNodeType().equals(AnnotationClass.ENTITY)){
				tree.getContextMenu().getItemByItemId("Delete").disable();
				MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("Delete").disable();
			}
		} else {
			
			tree.getContextMenu().getItemByItemId("Delete").enable();
			MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("Delete").enable();
			tree.getContextMenu().getItemByItemId("Rename").enable();
			MicroWSMOeditor.getSemanticAnnotationContextMenu().getItemByItemId("Rename").enable();
		}
	}*/
	
	private void queryWatson() {
		SemanticController.queryWatson();
		
		//cat.info(new java.util.Date() + " queryWatson()");
	}
	
	private void save() {
		ControllerToolkit.save();
		//Logger
		logEvents("ItemSave", "save", "AnnotatedDocument");
		//cat.info(new java.util.Date() + " save()");
	}
	
	private void export() {
		ControllerToolkit.export();
		//Logger
		logEvents("ItemExport", "export", "AnnotatedDocument");
		//cat.info(new java.util.Date() + " export()");
	}
	
	private void deleteEntity() {
		AnnotationsTree tree = MicroWSMOeditor.getAnnotationsTree();
		BaseTreeItem item = tree.getSelectedItem();
		if( item.getKind() == BaseTreeItem.TERM ) {
			ControllerToolkit.deleteElementByID(item.getID());
			tree.removeItem(item);
			
			//Logger
			logEvents("ItemDeleteSemAnnotation", "deleteEntity", item.getName());
		}
		
		//cat.info(new java.util.Date() + " deleteEntity()");
	}

	
	/**
	 * 
	 */
	public static void deleteAnnotation() {
		// retrieve the service structure tree
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem selectedItem = tree.getSelectedItem();
		
		/*
		 * delete the element inside the web page. There are 4 level because
		 * we need to delete all the hrest children of this element and the maximum
		 * deep of the tree is 4.
		 */
		List<ModelData> firstLevelList = tree.getChildren(selectedItem);
		if( firstLevelList != null ) {
			for( ModelData firstLevelElement : firstLevelList ) {
				BaseTreeItem firstLevelItem = (BaseTreeItem) firstLevelElement;
				List<ModelData> secondLevelList = tree.getChildren(firstLevelItem);
				if( secondLevelList != null ) {
					for( ModelData secondLevelElement : secondLevelList ) {
						BaseTreeItem secondLevelItem = (BaseTreeItem) secondLevelElement;
						List<ModelData> thirdLevelList = tree.getChildren(secondLevelItem);
						if( thirdLevelList != null ) {
							for( ModelData thirdLevelElement : thirdLevelList ) {
								BaseTreeItem thirdLevelItem = (BaseTreeItem) thirdLevelElement;
								List<ModelData> fourthLevelList = tree.getChildren(thirdLevelItem);
								if( fourthLevelList != null ) {
									for( ModelData fourthLevelElement : fourthLevelList ) {
										BaseTreeItem fourthLevelItem = (BaseTreeItem) fourthLevelElement;
										HRESTController.deleteAnnotation(fourthLevelItem.getID());
										
										//Logger
										logEvents("ItemDeleteHTMLAnnotation", "deleteAnnotation", fourthLevelItem.getID());
									}
								}
								HRESTController.deleteAnnotation(thirdLevelItem.getID());
								//Logger
								logEvents("ItemDeleteHTMLAnnotation", "deleteAnnotation", thirdLevelItem.getID());
							}
						}
						HRESTController.deleteAnnotation(secondLevelItem.getID());
						//Logger
						logEvents("ItemDeleteHTMLAnnotation", "deleteAnnotation", secondLevelItem.getID());
					}
				}
				HRESTController.deleteAnnotation(firstLevelItem.getID());
				//Logger
				logEvents("ItemDeleteHTMLAnnotation", "deleteAnnotation", firstLevelItem.getID());
			}
		}
		HRESTController.deleteAnnotation(selectedItem.getID());
		//Logger
		logEvents("ItemDeleteHTMLAnnotation", "deleteAnnotation", selectedItem.getID());
		/*
		 * delete the element inside the tree
		 */
		if( selectedItem.getNodeType().equals(AnnotationClass.TREEROOT)) {
			// if the element is the hrest root then set the default state
			tree.setDefaultState();
			
			List<ModelData> children = tree.getChildren(selectedItem);
			if( children != null ) {
				for( ModelData childElement : children ) {
					BaseTreeItem childItem = (BaseTreeItem) childElement;
					tree.removeItemById(childItem.getID());
				}
			}
			//Force refresh tree;
			tree.recalculate();
			tree.collapseAll();
			tree.expandAll();
			tree.repaint();
			
			tree.expandAll();
			
		} /*else if (selectedItem.getNodeType().equals(AnnotationClass.MODELREFERENCE)){
			
				BaseTreeItem parent = tree.getParentOf(selectedItem);
				
				//If the parent is an ENTITY, it should be deleted as well
				if(parent.getNodeType().equals(AnnotationClass.ENTITY)){
					//DO not delete
				}else {
					tree.removeSelectedItem();
				}
				
		}*/ else {
			// otherwise delete the selected element
			tree.removeSelectedItem();
		}
		
		//cat.info(new java.util.Date() + " deleteAnnotation()");
	}
	
	private void viewMore() {
		ServicePropertiesTree tree = MicroWSMOeditor.getServicePropertiesTree(); 
		BaseTreeItem item = tree.getSelectedItem();	
		
		if( item.getID().startsWith("viewMore") ) {
			BaseTreeItem termItem = tree.getParentOf(item);
			String keyword = termItem.getID();
			SemanticController.viewMore(keyword);
		}
		
		//cat.info(new java.util.Date() + " viewMore()");
	}
	
	private void retrieveConcepts() {
		DomainOntologiesTree tree = MicroWSMOeditor.getDomainOntologiesTree();
		BaseTreeItem item = tree.getSelectedItem();
		if( item.getID().equals("Concepts") ) {
			item = tree.getParentOf(item);
			SemanticController.retreiveConceptsOf(item.getID());
		}
		
		//cat.info(new java.util.Date() + " retrieveConcepts()");
	}
	
}