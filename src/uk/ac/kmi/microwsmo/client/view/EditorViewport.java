package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;

/**
 * This is the main container. In this panel are layouted
 * all the other component. The approach used is to nest
 * the final component inside a layout container so, you
 * keep out the component from the container layout. If you
 * want to change the layout, you simply use another design
 * and add the final component to this. 
 * 
 * @author KMi, The Open University
 */
public final class EditorViewport extends Viewport {
	
	/* ***** the layout objects ***** */
	
	// the layout
	private BorderLayout layout;
	/* the layout constrains */
	private BorderLayoutData northConstraint;
	private BorderLayoutData westConstraint;
	private BorderLayoutData centerConstraint;
	private BorderLayoutData eastConstraint;
	
	/* ***** the components ***** */

	// the menu bar which contains the commons operation of the editor
	//private EditorMenuBar menuBar;
	// contain the palette for the hREST tool
	private LeftSidePanel leftPanel;
	// contain the palette for the semantic annotation
	private RightSidePanel rightPanel;
	// contain a visualizator of the web page and a container which can have several tabs
	private CenterSidePanel centerPanel;
	
	/* *****  ***** */
	
	/**
	 * Creates a new custom viewport. It will be the
	 * content panel of the editor. Inside this viewport
	 * will be all the component which make up the MicroWSMO Editor.
	 */
	public EditorViewport() {
		super();
		setID(ComponentID.EDITOR_VIEWPORT);
		setLayout();
		initLayoutConstrains();
		initComponents();
		addComponents();
	}
	
	@Override
	protected void onAfterLayout() {
		MicroWSMOeditor.getController().updateEditorState();
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
	 * Initializes the constrains for each side of the
	 * border layout.
	 */
	private void initLayoutConstrains() {
		// 5% of the area
		northConstraint = new BorderLayoutData(LayoutRegion.NORTH, 0.05F);
		// 20% of the area and a floating size from 180px to 400px
		westConstraint = new BorderLayoutData(LayoutRegion.WEST, 0.2F, 180, 600);
		westConstraint.setCollapsible(true);
		westConstraint.setSplit(true);
		// 20% of the area and a floating size from 180px to 400px
		eastConstraint = new BorderLayoutData(LayoutRegion.EAST, 0.2F, 180, 600);
		eastConstraint.setCollapsible(true);
		eastConstraint.setSplit(true);
		// the rest of the area
		centerConstraint = new BorderLayoutData(LayoutRegion.CENTER);
		
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		//menuBar = MicroWSMOeditor.getEditorMenuBar();
		leftPanel = new LeftSidePanel();
		centerPanel = new CenterSidePanel();
		rightPanel = new RightSidePanel();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		// The menu bar for now is disabled.
		LayoutContainer northContainer = new LayoutContainer();
		northContainer.addText("<span style=\"font-size: 11px; font-family: tahoma,arial,verdana,sans-serif; color: #15428B;\">For best user experience, plese use the latest verion of Firefox.</span>");
		add(northContainer, northConstraint);
		add(leftPanel, westConstraint);
		add(centerPanel, centerConstraint);
		add(rightPanel, eastConstraint);
	}
	
}