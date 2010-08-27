package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.controller.Controller;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

public class RenameDialog extends Dialog {

	protected TextField<String> name;
	
	protected Button cancel;
	protected Button ok;

	protected String oldName;

	public RenameDialog(String oldName) {
		this.oldName = oldName;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setHeading("Rename Property");
		setModal(true);
		setBodyBorder(true);
		setResizable(false);
		setWidth(350);

		name = new TextField<String>();
		name.setFieldLabel("Property");
		name.setAllowBlank(false);
		name.setValue(oldName);
		name.setWidth(350);
		add(name);
	}

	protected void createButtons() {
		super.createButtons();
	
		cancel = new Button("Cancel");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				RenameDialog.this.hide();
			}
		});
		addButton(cancel);

		ok = new Button("Ok");
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
					String newName = name.getValue();
					
					if ( newName != null && newName != "") {
						RenameDialog.this.hide();
						
						ServiceStructureContextMenu.renameProperty(newName);
					}
			}
		});
		addButton(ok);
	}

}
