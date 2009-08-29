package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.CSSIconImage;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.extjs.gxt.ui.client.util.IconHelper;

/**
 * 
 * @author KMi, The Open University
 */
public class BaseTreeItem extends BaseModelData {
	
	private static final long serialVersionUID = 7818119315960531449L;
	private static long ID = 0;
	private int kind;
	
	public static final int	SIMPLE_ITEM = 0;
	public static final int ONTOLOGY = 1;
	public static final int ENTITY = 2;
	public static final int TERM = 3;
	
	/**
	 * 
	 */
	public BaseTreeItem() {
		this("BaseTreeItem-" + ID, "");
	}
	
	public BaseTreeItem(String name) {
		this(name, "");
	}
	
	/**
	 * 
	 * @param name
	 * @param iconStyle
	 */
	public BaseTreeItem(String name, String iconStyle) {
		super();
		setID(name);
		setName(name);
		setIcon(iconStyle);
		setEnabled(true);
		setVisible(true);
		if(iconStyle.equals(CSSIconImage.RDF) ) {
			setSorterField("d");
			kind = ONTOLOGY;
		} else if( iconStyle.equals(CSSIconImage.ONTO_CLASS) ) {
			setSorterField("a");
			kind = ENTITY;
		} else if( iconStyle.equals(CSSIconImage.ONTO_PROPERTY) ) {
			setSorterField("b");
			kind = ENTITY;
		} else if( iconStyle.equals(CSSIconImage.ONTO_INDIVIDUAL) ) {
			setSorterField("c");
			kind = ENTITY;
		} else if( iconStyle.equals(CSSIconImage.TERM) ) {
			setSorterField("d");
			kind = TERM;
		} else {
			setSorterField("d");
			kind = SIMPLE_ITEM;
		}
	}
	
	public int getKind() {
		return kind;
	}
	
	public int setKind(int k) {
		 kind = k;
		 return kind;
	}
	
	public void setIcon(String icon) {
		set("icon", icon);
	}
	
	public String getIcon() {
		return get("icon");
	}
	
	public void setSorterField(String nameField) {
		set("sorterField", nameField);
	}
	
	public String getSorterField() {
		return get("sorterField");
	}
	
	/**
	 * Set the enabled state of the item. If an item is disabled, 
	 * it can't fire any event. By default the item is enabled.
	 * 
	 * @param enabled true if is enabled
	 */
	public void setEnabled(boolean enabled) {
		set("enabled", enabled);
		if( enabled ) {
			setVisible(true);
		}
	}
	
	/**
	 * Returns the enabled state of this item.
	 * 
	 * @return true if is enabled.
	 */
	public boolean isEnabled() {
		return get("enabled");
	}
	
	/**
	 * Set the visible state of the item. By default is {@code true}.
	 * When a component is invisible, it can't fire any event because is
	 * disabled too.
	 * 
	 * @param visible true if the item is visible.
	 */
	public void setVisible(boolean visible) {
		set("visible", visible);
		if( !visible ) {
			setEnabled(false);
		}
	}
	
	/**
	 * Returns the visible state of this item.
	 * 
	 * @return true if is visible.
	 */
	public boolean isVisible() {
		return get("visible");
	}
	
	/**
	 * Sets the id and the item id.
	 * 
	 * @param id a string which represent the component univocally.
	 */
	public void setID(String id) {
		set("id", id);
	}
	
	/**
	 * Returns the id.
	 * 
	 * @return a string which represent the unique id of this item.
	 */
	public String getID() {
		return get("id");
	}
	
	/**
	 * Sets the name.
	 * 
	 * @param name is the visible property of the item.
	 */
	public void setName(String name) {
		set("name", name);
	}
	
	/**
	 * Returns the name of the item.
	 * 
	 * @return the string which represent the name of the item.  
	 */
	public String getName() {
		return get("name");
	}
	
}