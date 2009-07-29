package uk.ac.kmi.microwsmo.client.view.deprecated;

import com.extjs.gxt.ui.client.widget.tree.TreeItem;

/**
 * 
 * @author Simone Spaccarotella
 */
public class DeprecatedBaseTreeItem extends TreeItem {
	
	private static final long serialVersionUID = 7818119315960531449L;
	private static long ID = 0;
	
	/**
	 * 
	 */
	public DeprecatedBaseTreeItem() {
		this("BaseTreeItem-" + ID, "");
	}
	
	public DeprecatedBaseTreeItem(String name) {
		this(name, "");
	}
	
	/**
	 * 
	 * @param name
	 * @param iconStyle
	 */
	public DeprecatedBaseTreeItem(String name, String iconStyle) {
		super(name);
		setID(name);
		super.setIconStyle(iconStyle);
	}
	
	public void setID(String id) {
		super.setId(id);
		super.setItemId(id);
	}
	
	public String getID() {
		return super.getId();
	}
	
}