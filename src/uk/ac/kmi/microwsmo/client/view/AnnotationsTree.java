package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

/**
 * 
 * @author Simone Spaccarotella
 */
public final class AnnotationsTree extends BaseTree {

	private AnnotationsContextMenu menu;
	
	public AnnotationsTree() {
		super(ComponentID.ANNOTATIONS_TREE);
		initComponents();
		addComponents();
	}
	
	/**
	 * Initializes the components.
	 */
	private void initComponents() {
		menu = new AnnotationsContextMenu();
	}
	
	/**
	 * Adds the components into the container.
	 */
	private void addComponents() {
		setContextMenu(menu);
	}

	@Override
	public void setDefaultState() {
		store.removeAll();
	}

}
