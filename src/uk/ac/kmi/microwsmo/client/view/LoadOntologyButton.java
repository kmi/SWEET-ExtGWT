package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.controller.SemanticController;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.button.Button;

public final class LoadOntologyButton extends Button {
	
	public LoadOntologyButton() {
		super("Load Ontology");
		setID(ComponentID.LOAD_ONTOLOGY_BUTTON);
		setToolTip("Load ontology from an URL");
		setBorders(true);
		
		addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				SemanticController.showOntoURLDialog("");
			}
		});
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
