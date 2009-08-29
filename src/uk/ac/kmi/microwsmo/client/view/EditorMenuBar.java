package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuBar;
import com.extjs.gxt.ui.client.widget.menu.MenuBarItem;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

/**
 * 
 * @author KMi, The Open University
 *
 */
public final class EditorMenuBar extends MenuBar {

	/**
	 * 
	 */
	public EditorMenuBar() {
		super();
		setID(ComponentID.EDITOR_MENU_BAR);
		// creates the menu "file"
		Menu file = new Menu();
		MenuBarItem fileItem = new MenuBarItem("File", file);
			
			MenuItem apri = new MenuItem("Apri");
				Menu subApri = new Menu();
				MenuItem cosa = new MenuItem("Cosa?");
				subApri.add(cosa);
				apri.setSubMenu(subApri);	
			file.add(apri);
			MenuItem salva = new MenuItem("Salva");
			file.add(salva);
		
		add(fileItem);
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
