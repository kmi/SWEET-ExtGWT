package uk.ac.kmi.microwsmo.client.controller;

import java.util.List;
import java.lang.*;


import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.Message;
import uk.ac.kmi.microwsmo.client.view.AnnotationsTree;
import uk.ac.kmi.microwsmo.client.view.BaseTreeItem;
import uk.ac.kmi.microwsmo.client.view.DomainOntologiesTree;
import uk.ac.kmi.microwsmo.client.view.HrestTagsTree;
import uk.ac.kmi.microwsmo.client.view.NavigatorTextField;
import uk.ac.kmi.microwsmo.client.view.SemanticAnnotationTree;
import uk.ac.kmi.microwsmo.client.view.ServicePropertiesTree;
import uk.ac.kmi.microwsmo.client.view.ServiceStructureTree;
import uk.ac.kmi.microwsmo.client.view.deprecated.DeprecatedBaseTreeItem;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.Info;
import com.google.gwt.user.client.Window;

/**
 * Is the controller of the Editor. This class implements
 * the listener of all the events. Every fired event is
 * retrieved by the unique istance of this class and managed
 * in different way, depend to the kind of the event.
 * 
 * @author KMi, The Open University
 */
public final class Controller extends KeyListener implements Listener<ComponentEvent> {
	
	// the code of the "Enter" key
	private static final int ENTER = 13;
	
	/**
	 * Init the controller.
	 */
	public Controller() {
		ComponentID.IDGenerator.init();
	}

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
			} else if( componentID.equals(ComponentID.SERVICE_STRUCTURE_CONTEXT_MENU) ) {
				deleteAnnotation();
			}
			
		} else if( eventType.equals(Events.OnDoubleClick) ) {
			
			if( componentID.equals(ComponentID.HREST_TAGS_TREE) ) {
				hrestAnnotation(sourceComponent);
			}
			
		} else if( eventType.equals(Events.OnKeyPress) ) {
			
			int keyCode = event.getKeyCode();
			if( componentID.equals(ComponentID.NAVIGATOR_TEXT_FIELD) && keyCode == ENTER) {
				browse();
			}
		} else if( eventType.equals(Events.OnMouseOver) ) {
			if( componentID.contains(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU) ) {
				disableEnableContextMenu(componentID);
			} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
				disableEnableContextMenu(componentID);}
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
		// reset the view and the model of the Service Structur tree and of the
		resetDefaultState();
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
		ComponentID.IDGenerator.init();
	}
	
	/**
	 * Annotate the text wrapped with the hREST tag. This
	 * method is used by every hREST tag item, and the kind
	 * of the annotation is choosen by the id of it. 
	 * 
	 * @param component the item of the tree which fired the event.
	 */
	private void hrestAnnotation(Component component) {
		// retrieve the item which fired the event
		HrestTagsTree tree = (HrestTagsTree) component;
		DeprecatedBaseTreeItem item = tree.getSelectedItem();
		// if is enabled can executes the annotation
		if( item.isEnabled() ) {
			String itemId = item.getID();
			HRESTController.annotate(itemId);
		}
	}
	
	/**
	 * 
	 * @param componentID
	 */
	private void semanticAnnotation(String componentID) {
		SemanticAnnotationTree tree = null;
		if( componentID.contains(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getServicePropertiesTree();	
		} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getDomainOntologiesTree();
		}
		BaseTreeItem item = tree.getSelectedItem();
				
		String keyword = tree.getParentOf(item).getID();
		String selection = ControllerToolkit.getTextSelected();
		if( item.getKind() == BaseTreeItem.ENTITY) {
			if( keyword.toLowerCase().trim().equals(selection.toLowerCase().trim())||
					keyword.toLowerCase().trim().contains(selection.toLowerCase().trim())) {
				SemanticController.annotate(item.getID(), item.getIcon());
			} else {
				Message.show(Message.SEMANTIC_ANNOTATION);
			}
		}	
	}
	
	private void disableEnableContextMenu(String componentID) {
		SemanticAnnotationTree tree = null;
		if( componentID.contains(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getServicePropertiesTree();	
		} else if( componentID.contains(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU) ) {
			tree = MicroWSMOeditor.getDomainOntologiesTree();
		}
		BaseTreeItem item = tree.getSelectedItem();
		
		if( item.getKind() == BaseTreeItem.ENTITY) {
			tree.getContextMenu().setVisible(true);
			MicroWSMOeditor.getSemanticAnnotationContextMenu().setVisible(true);
		}
		else{
			tree.getContextMenu().setVisible(false);
			MicroWSMOeditor.getSemanticAnnotationContextMenu().setVisible(false);
		}
	}
	
	private void queryWatson() {
		SemanticController.queryWatson();
	}
	
	private void save() {
		ControllerToolkit.save();
	}
	
	private void export() {
		ControllerToolkit.export();
	}
	
	private void deleteEntity() {
		AnnotationsTree tree = MicroWSMOeditor.getAnnotationsTree();
		BaseTreeItem item = tree.getSelectedItem();
		if( item.getKind() == BaseTreeItem.TERM ) {
			ControllerToolkit.deleteElementByID(item.getID());
			tree.removeItem(item);
		}
	}
	
	/**
	 * 
	 */
	private void deleteAnnotation() {
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
									}
								}
								HRESTController.deleteAnnotation(thirdLevelItem.getID());
							}
						}
						HRESTController.deleteAnnotation(secondLevelItem.getID());
					}
				}
				HRESTController.deleteAnnotation(firstLevelItem.getID());
			}
		}
		HRESTController.deleteAnnotation(selectedItem.getID());
		/*
		 * delete the element inside the tree
		 */
		if( selectedItem.getID().equals("hrest") ) {
			// if the element is the hrest root then set the default state
			tree.setDefaultState();
		} else {
			// otherwise delete the selected element
			tree.removeSelectedItem();
		}
	}
	
	private void viewMore() {
		ServicePropertiesTree tree = MicroWSMOeditor.getServicePropertiesTree(); 
		BaseTreeItem item = tree.getSelectedItem();	
		
		if( item.getID().startsWith("viewMore") ) {
			BaseTreeItem termItem = tree.getParentOf(item);
			String keyword = termItem.getID();
			SemanticController.viewMore(keyword);
		}
	}
	
	private void retrieveConcepts() {
		DomainOntologiesTree tree = MicroWSMOeditor.getDomainOntologiesTree();
		BaseTreeItem item = tree.getSelectedItem();
		if( item.getID().equals("Concepts") ) {
			item = tree.getParentOf(item);
			SemanticController.retreiveConceptsOf(item.getID());
		}
	}
	
}