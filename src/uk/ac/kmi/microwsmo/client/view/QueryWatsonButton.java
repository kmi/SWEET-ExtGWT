package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.ComponentID;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.widget.button.Button;

public final class QueryWatsonButton extends Button {

	public QueryWatsonButton() {
		//super("x-tool-gear");
		super("Search Watson");
		setID(ComponentID.QUERY_WATSON_BUTTON);
		setToolTip("Query Watson");
		addListener(Events.OnClick, MicroWSMOeditor.getController());
		setBorders(true);
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
