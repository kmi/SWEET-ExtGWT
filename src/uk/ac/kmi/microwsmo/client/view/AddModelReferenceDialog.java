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

public class AddModelReferenceDialog extends Dialog {

	protected TextField<String> uri;
	
	protected Button cancel;
	protected Button ok;

	protected String element;

	public AddModelReferenceDialog(String element) {
		this.element = element;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setHeading("Add a Model Reference");
		setModal(true);
		setBodyBorder(true);
		setResizable(false);
		setWidth(350);

		uri = new TextField<String>();
		uri.setFieldLabel("URI");
		uri.setAllowBlank(false);
		//uri.setValue(oldName);
		uri.setWidth(400);
		add(uri);
	}

	protected void createButtons() {
		super.createButtons();
	
		cancel = new Button("Cancel");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				AddModelReferenceDialog.this.hide();
			}
		});
		addButton(cancel);

		ok = new Button("Ok");
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
					String servicesUri = "/data/services"; // complete the services URI
					String newName = uri.getValue();
					String passwordString = "";
					if ( newName != null && newName != "") {
						AddModelReferenceDialog.this.hide();
						
						//Controller.renameProperty(servicesUri, userNameString, passwordString);
					}
			}
		});
		addButton(ok);
	}

}
