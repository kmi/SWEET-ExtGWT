package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.controller.ControllerToolkit;
import uk.ac.kmi.microwsmo.client.util.ComponentID;


import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * This button save the hREST annotation in a
 * local file.
 * 
 * @author KMi, The Open University
 */
public final class SaveButton extends Button {

	/**
	 * Creates a new button.
	 */
	public SaveButton() {
		super("Save");
		//setID(ComponentID.SAVE_BUTTON);
		//setToolTip("Saves the hREST annotation added to the document");
		Menu saveMenu = new Menu();
		MenuItem saveLocal = new MenuItem();
		saveLocal.setId(ComponentID.SAVE_LOCAL_MENU);
		saveLocal.setItemId(ComponentID.SAVE_LOCAL_MENU);
		saveLocal.setText("Save to local machine");

		saveLocal.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				ControllerToolkit.save();
			}
			
		});

		// save to sevice repository
		MenuItem saveToRepo = new MenuItem();
		saveToRepo.setId(ComponentID.SAVE_REPOSITORY_MENU);
		saveToRepo.setItemId(ComponentID.SAVE_REPOSITORY_MENU);
		saveToRepo.setText("Save to service repository");
		saveToRepo.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				RepositorySelectionDialog repositorySelectionDialog = new RepositorySelectionDialog("Save");
				repositorySelectionDialog.setModal(true);
				repositorySelectionDialog.show();
			}
			
		});

		saveMenu.add(saveLocal);
		saveMenu.add(saveToRepo);
		this.setMenu(saveMenu);
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
	
}