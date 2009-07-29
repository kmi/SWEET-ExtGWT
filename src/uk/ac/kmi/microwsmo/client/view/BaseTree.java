package uk.ac.kmi.microwsmo.client.view;

import java.util.List;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * This tree could be utilized in all over the editor.
 * @author Simone Spaccarotella
 */
abstract class BaseTree extends TreePanel<ModelData> {
	
	public BaseTree(String id) {
		super(new TreeStore<ModelData>());
		setID(id);
		setStateful(true);
		setIconProvider(new ModelIconProvider<ModelData>() {

			@Override
			public AbstractImagePrototype getIcon(ModelData model) {
				if (model.get("icon") != null) {
					return IconHelper.createStyle((String) model.get("icon")); 
				} else {
					return null;
				}  
			}
			
		});
		setDisplayProperty("name");
	}
	
	public void setStore(TreeStore<ModelData> store) {
		this.store = store;
	}
	
	public void sortBy(String fieldName) {
		store.sort(fieldName, SortDir.ASC);
	}
	
	public boolean contains(BaseTreeItem item) {
		return store.contains(item);
	}
	
	public boolean contains(String keyword) {
		ModelData item = store.findModel("id", keyword);
		if( item == null ) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<ModelData> getChildren(BaseTreeItem item) {
		return store.getChildren(item);
	}
	
	public void addRootItem(BaseTreeItem item) {
		store.add(item, false);
	}
	
	public void addItem(BaseTreeItem parent, BaseTreeItem child) {
		store.add(parent, child, false);
	}
	
	public void removeItem(BaseTreeItem item) {
		store.remove(item);
	}
	
	public void removeSelectedItem() {
		BaseTreeItem item = getSelectedItem();
		if( item != null ) {
			store.remove(item);
		}
	}
	
	public void removeItemById(String id) {
		ModelData item = store.findModel("id", id);
		if( item != null ) {
			store.remove(item);
		}
	}
	
	public BaseTreeItem getItemById(String id) {
		return (BaseTreeItem) store.findModel("id", id);
	}
	
	public BaseTreeItem getSelectedItem() {
		return (BaseTreeItem) getSelectionModel().getSelectedItem();
	}
	
	public BaseTreeItem getParentOf(BaseTreeItem item) {
		return (BaseTreeItem) store.getParent(item);
	}

	/**
	 * Set this component to the default state
	 */
	public abstract void setDefaultState();
	
	protected void setID(String id) {
		setId(id);
		setItemId(id);
	}	
	
}