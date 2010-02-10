package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.ControllerToolkit;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * This button export the semantic annotation
 * in a RDF format.
 * 
 * @author KMi, The Open University
 */
public final class ExportButton extends Button {

	/**
	 * Creates a new button.
	 */
	public ExportButton() {
		super("Export");
//		setID(ComponentID.EXPORT_BUTTON);
//		setToolTip("Exports the semantic annotation of this service in the RDF format");
		Menu exportMenu = new Menu();
		MenuItem exportLocal = new MenuItem();
		exportLocal.setId(ComponentID.EXPORT_LOCAL_MENU);
		exportLocal.setItemId(ComponentID.EXPORT_LOCAL_MENU);
		exportLocal.setText("Export to local machine");
		exportLocal.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				ControllerToolkit.export();
			}
		});

		// save to sevice repository
		MenuItem exportToRepo = new MenuItem();
		exportToRepo.setId(ComponentID.EXPORT_REPOSITORY_MENU);
		exportToRepo.setItemId(ComponentID.EXPORT_REPOSITORY_MENU);
		exportToRepo.setText("Export to service repository");
		exportToRepo.addSelectionListener(new SelectionListener<MenuEvent>() {
			@Override
			public void componentSelected(MenuEvent ce) {
				RepositorySelectionDialog repositorySelectionDialog = new RepositorySelectionDialog("Export");
				repositorySelectionDialog.setModal(true);
				repositorySelectionDialog.show();
			}
			
		});

		exportMenu.add(exportLocal);
		exportMenu.add(exportToRepo);
		this.setMenu(exportMenu);
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
	 * Sets the suitable listener.
	 */
	private void setListener() {
//		addListener(Events.OnClick, MicroWSMOeditor.getController());
	}
	
}