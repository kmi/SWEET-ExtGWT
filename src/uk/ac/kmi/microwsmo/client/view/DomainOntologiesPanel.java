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
 * This panel show all the ongologies retrieved
 * for each service property.
 * 
 * @author Simone Spaccarotella
 */
public final class DomainOntologiesPanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	private FitLayout layout;
	
	/* ***** the components ***** */
	
	private DomainOntologiesTree tree;
	
	/* *****  ***** */
	
	/**
	 * Creates a panel who contain a tree.
	 */
	public DomainOntologiesPanel() {
		super();
		setID(ComponentID.DOMAIN_ONTOLOGIES_PANEL);
		setLayout();
		setHeading("Domain Ontologies");
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
				MicroWSMOeditor.getDomainOntologiesTree().collapseAll();
			}
			
		});
		button.setToolTip("Collapse all");
		getHeader().addTool(button);
		button = new ToolButton(CSSIconImage.EXPAND, new SelectionListener<IconButtonEvent>() {

			@Override
			public void componentSelected(IconButtonEvent event) {
				MicroWSMOeditor.getDomainOntologiesTree().expandAll();
			}
			
		});
		button.setToolTip("Expand all");
		getHeader().addTool(button);
		tree = MicroWSMOeditor.getDomainOntologiesTree();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(tree);
	}
	
}