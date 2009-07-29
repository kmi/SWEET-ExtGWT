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
public final class SemanticAnnotationContextMenu extends Menu{

	private MenuItem annotation;
	
	public SemanticAnnotationContextMenu() {
		super();
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
		setId(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU + id);
		setItemId(ComponentID.SEMANTIC_ANNOTATION_CONTEXT_MENU + id);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		annotation = new MenuItem("Semantic Annotation");
		annotation.setIconStyle(CSSIconImage.RDF);
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(annotation);
	}
	
}
