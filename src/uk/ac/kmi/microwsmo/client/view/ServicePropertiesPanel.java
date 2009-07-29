package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.IconButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.ToolButton;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * This panel show the terms, with the related
 * ontologies.
 * 
 * @author Simone Spaccarotella
 */
public final class ServicePropertiesPanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	private FitLayout layout;
	
	/* ***** the components ***** */
	
	// the service properties tree
	private ServicePropertiesTree tree;
	// the button which query watson
	private QueryWatsonButton queryWatsonButton;
	
	/* *****  ***** */
	
	
	/**
	 * Create a panel with a tree inside.
	 */
	public ServicePropertiesPanel() {
		super();
		setID(ComponentID.SERVICE_PROPERTIES_PANEL);
		setLayout();
		setHeading("Service Properties");
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
	 * Sets a fit layout.
	 */
	private void setLayout() {
		layout = new FitLayout();
		setLayout(layout);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		ToolButton button = new ToolButton(CSSIconImage.COLLAPS, new SelectionListener<IconButtonEvent>() {

			@Override
			public void componentSelected(IconButtonEvent event) {
				MicroWSMOeditor.getServicePropertiesTree().collapseAll();
			}
			
		});
		button.setToolTip("Collapse all");
		getHeader().addTool(button);
		button = new ToolButton(CSSIconImage.EXPAND, new SelectionListener<IconButtonEvent>() {

			@Override
			public void componentSelected(IconButtonEvent event) {
				MicroWSMOeditor.getServicePropertiesTree().expandAll();
			}
			
		});
		button.setToolTip("Expand all");
		getHeader().addTool(button);
		tree = MicroWSMOeditor.getServicePropertiesTree();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(tree);
	}
	
}
