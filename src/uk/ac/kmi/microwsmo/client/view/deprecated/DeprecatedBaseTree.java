package uk.ac.kmi.microwsmo.client.view.deprecated;

import java.util.List;

import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;

/**
 * This tree could be utilized in all over the editor.
 * @author KMi, The Open University
 */
public abstract class DeprecatedBaseTree extends Tree {
	
	public DeprecatedBaseTree(String id) {
		super();
		setID(id);
	}
	
	public List<TreeItem> getChildren(DeprecatedBaseTreeItem item) {
		return item.getItems();
	}
	
	public void addRootItem(DeprecatedBaseTreeItem item) {
		super.root.add(item);
	}
	
	public void addItem(DeprecatedBaseTreeItem parent, DeprecatedBaseTreeItem child) {
		parent.add(child);
	}
	
	public void removeItem(DeprecatedBaseTreeItem item) {
		super.remove(item);
	}
	
	public void removeSelectedItem() {
		TreeItem item = super.getSelectedItem();
		super.remove(item);
	}
	
	public void removeItemById(String id) {
		TreeItem item = super.getItemById(id);
		super.remove(item);
	}
	
	public DeprecatedBaseTreeItem getItemById(String id) {
		return (DeprecatedBaseTreeItem) super.getItemById(id);
	}
	
	public DeprecatedBaseTreeItem getSelectedItem() {
		return (DeprecatedBaseTreeItem) super.getSelectedItem();
	}
	
	public DeprecatedBaseTreeItem getParentOf(DeprecatedBaseTreeItem item) {
		return (DeprecatedBaseTreeItem) item.getParentItem();
	}

	/**
	 * Set this component to the default state
	 */
	public abstract void setDefaultState();
	
	protected void setID(String id) {
		super.setId(id);
		super.setItemId(id);
	}	
	
}