package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * 
 * @author KMi, The Open University
 */
public final class SemanticAnnotationContextMenu extends Menu{

	private MenuItem annotation;
	//private MenuItem loadOntology;
	
	public SemanticAnnotationContextMenu() {
		super();
		initComponents();
		addComponents();
		addListener(Events.OnClick, MicroWSMOeditor.getController());
		addListener(Events.OnMouseOver, MicroWSMOeditor.getController());
	}
	
	/**
	 * Sets the id and the item id.
	 * 
	 * @param id a string which represent the component univocally.
	 */
	public void setID(String id) {
		
		setId(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU + id);
		setItemId(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU + id);
		
		addListeners(id);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		annotation = new MenuItem("Semantic Annotation");
		annotation.setIconStyle(CSSIconImage.RDF); 
		annotation.setId("0");
		annotation.setItemId("0");
		/*loadOntology = new MenuItem("Load Ontology");
		loadOntology.setIconStyle(CSSIconImage.FOLDER_OPENED);
		loadOntology.setId("1");
		loadOntology.setItemId("1");*/
	}
	
	private String componentId = "";
	private void addListeners(String id){
		componentId = id;
		annotation.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 if( componentId.equals(ComponentID.DOMAIN_ONTOLOGIES_TREE)){
					 Controller.semanticAnnotation(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU);
				 } else if (componentId.equals(ComponentID.SERVICE_PROPERTIES_TREE)){
					 Controller.semanticAnnotation(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU);
				 }
			 }  
		});
		
		/*loadOntology.addSelectionListener(new SelectionListener<MenuEvent>() {  
			 public void componentSelected(MenuEvent ce) {  
				 //if( componentId.equals(ComponentID.DOMAIN_ONTOLOGIES_TREE)){
					 Controller.loadOntology(ComponentID.DOMAIN_ONTOLOGIES_CONTEXT_MENU);
				 //} else if (componentId.equals(ComponentID.SERVICE_PROPERTIES_TREE)){
					// Controller.loadOntology(ComponentID.SERVICE_PROPERTIES_CONTEXT_MENU);
				 //}
			 }  
		});*/
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(annotation);
		//add(loadOntology);
	}
	
}
