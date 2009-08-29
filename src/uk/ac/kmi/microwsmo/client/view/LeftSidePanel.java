package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;

/**
 * This panel is the content panel which layout a
 * tabbed panel and another container with two
 * buttons: save and export. This content panel
 * is placed on the left side of the editor
 * (such as the class's name means).
 * 
 * @author KMi, The Open University
 */
final class LeftSidePanel extends ContentPanel {

	/* ***** the layout objects ***** */
	
	// the layout
	private RowLayout layout;
	/* the layout constrains */
	private RowData serviceStructConstraint;
	private RowData buttonConstraint;
	
	/* ***** the components ***** */
	
	private ServiceStructurePanel serviceStructPanel;
	private ButtonPanel buttonsPanel;
		
	/* *****  ***** */
	
	/**
	 * Creates a new left side content panel. It is
	 * placed on the left side of the editor, next to
	 * the web pages viewer.
	 */
	public LeftSidePanel() {
		super();
		setID(ComponentID.LEFT_SIDE_PANEL);
		setHeading("Semantic Description");
		setLayout();
		initLayoutConstrains();
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
	 * Sets a row layout.
	 */
	private void setLayout() {
		layout = new RowLayout(Orientation.VERTICAL);
		setLayout(layout);
	}
	
	/**
	 * Initializes the constrains for both 
	 * the side (north and south) of the row layout.
	 */
	private void initLayoutConstrains() {
		serviceStructConstraint = new RowData(1, 0.9, new Margins(1));
		buttonConstraint = new RowData(1, 0.1, new Margins(1));
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		serviceStructPanel = new ServiceStructurePanel();
		buttonsPanel = new ButtonPanel();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(serviceStructPanel, serviceStructConstraint);
		add(buttonsPanel, buttonConstraint);
	}
	
}