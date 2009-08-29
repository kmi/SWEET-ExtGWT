package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;

/**
 * 
 * @author KMi, The Open University
 */
final class NavigatorPanel extends ContentPanel {
	
	/* ***** the components ***** */
	
	private NavigatorTextField navigatorTextField;
	
	/* *****  ***** */
	
	/**
	 * Create a new panel which contains a text field
	 * for navigate through the web.
	 */
	public NavigatorPanel() {
		super();
		setLayout(new FitLayout());
		setID(ComponentID.NAVIGATOR_PANEL);
		setHeading("Navigator");
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
	
	@Override
	protected void onResize(int width, int height) {
		super.onResize(width, height);
		navigatorTextField.setSize(width, height);
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		navigatorTextField = MicroWSMOeditor.getNavigatorTextField();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		add(navigatorTextField);
	}
	
}