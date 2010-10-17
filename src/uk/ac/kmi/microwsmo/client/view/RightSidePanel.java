package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.toolbar.SeparatorToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

/**
 * 
 * @author KMi, The Open University
 *
 */
final class RightSidePanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	// the layout
	private AccordionLayout layout;
	
	/* ***** the components ***** */
	
	private ToolBar toolBar;
	// contains the hrest tags
	private HrestTagsPanel hrestTagsPanel;
	// contains the service properties keywords
	private ServicePropertiesPanel servicePropertiesPanel;
	// is another visualization of the service properties
	private DomainOntologiesPanel domainOntologiesPanel;
	// contains the keyword annotated
	//private OwnOntologyPanel ownOntologyPanel;
	
	/* *****  ***** */
	
	/**
	 * Create a new panel which contains a tree.
	 * This tree is the hREST tags tree, which organize
	 * the hREST tags in a hierarchical way.
	 */
	public RightSidePanel() {
		super();
		setID(ComponentID.RIGHT_SIDE_PANEL);
		setHeading("Annotation Editor");
		setLayout();
		initComponents();
		addComponents();
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
	 * Sets an accordion layout.
	 */
	private void setLayout() {
		layout = new AccordionLayout();
		setLayout(layout);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		toolBar = new ToolBar();  
		hrestTagsPanel = new HrestTagsPanel();
		servicePropertiesPanel = MicroWSMOeditor.getServicePropertiesPanel();
		domainOntologiesPanel = MicroWSMOeditor.getDomainOntologiesPanel();
		//ownOntologyPanel = new OwnOntologyPanel();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		QueryWatsonButton item = MicroWSMOeditor.getQueryWatsonButton();
		//LoadOntologyButton loadOntologyButton = MicroWSMOeditor.getLoadOntologyButton();
		toolBar.add(new SeparatorToolItem());
		toolBar.add(item);  
		toolBar.add(new SeparatorToolItem());
		//toolBar.add(loadOntologyButton);
		toolBar.add(new SeparatorToolItem());
		setTopComponent(toolBar);
		add(hrestTagsPanel);
		add(servicePropertiesPanel);
		add(domainOntologiesPanel);
		//add(ownOntologyPanel);
	}
	
}
