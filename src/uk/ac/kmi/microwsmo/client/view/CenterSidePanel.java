package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

/**
 * This container have mainly two components: one is the
 * web page visualizer and the other is another container
 * which have inside other components.
 *  
 * @author KMi, The Open University
 */
final class CenterSidePanel extends LayoutContainer {

	/* ***** the layout objects ***** */
	
	// the layout
	private BorderLayout layout;
	/* the layout constrains */
	private BorderLayoutData upperConstraint;
	private BorderLayoutData centerConstraint;
	private BorderLayoutData lowerConstraint;
	
	/* ***** the components ***** */
	
	// contain the text field and the button for navigate through the web 
	private NavigatorPanel navigator;
	// show the web page
	private WebPagesDisplay viewer;
	// contain other components
	private LowerSidePanel lowerPanel;
	
	/* *****  ***** */
	
	/**
	 * Create a new container which have two
	 * component.
	 */
	public CenterSidePanel() {
		super();
		setID(ComponentID.CENTER_SIDE_PANEL);
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
	 * Sets a border layout.
	 */
	private void setLayout() {
		layout = new BorderLayout();
		setLayout(layout);
	}
	
	/**
	 * Initializes the constrains for both 
	 * the side (center and south) of the border layout.
	 */
	private void initLayoutConstrains() {
		upperConstraint = new BorderLayoutData(LayoutRegion.NORTH, 50F);
		upperConstraint.setCollapsible(true);
		centerConstraint = new BorderLayoutData(LayoutRegion.CENTER, 0.8F);
		lowerConstraint = new BorderLayoutData(LayoutRegion.SOUTH, 0.2F);
		lowerConstraint.setCollapsible(true);
		lowerConstraint.setSplit(true);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		navigator = new NavigatorPanel();
		viewer = new WebPagesDisplay();
		lowerPanel = new LowerSidePanel();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(navigator, upperConstraint);
		add(viewer, centerConstraint);
		add(lowerPanel, lowerConstraint);
	}
	
}