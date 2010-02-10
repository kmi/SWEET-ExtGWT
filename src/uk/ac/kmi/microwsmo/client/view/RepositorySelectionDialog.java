package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.controller.ControllerToolkit;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;

public class RepositorySelectionDialog extends Dialog {

	protected TextField<String> userName;

	protected TextField<String> password;

	protected TextField<String> repository;

	protected Button cancel;

	protected Button save;

	protected Status status;

	protected String type;

	public RepositorySelectionDialog(String type) {
		this.type = type;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.LEFT);
		setButtons("");
		setHeading("Select Repository");
		setModal(true);
		setBodyBorder(true);
		setResizable(false);
		setWidth(365);

		repository = new TextField<String>();
		repository.setFieldLabel("Repository URL");
		repository.setAllowBlank(false);
		repository.setWidth(350);
		repository.setEmptyText("e.g. http://repository.open.ac.uk");
		add(repository);

		userName = new TextField<String>();
		userName.setFieldLabel("User Name");
		userName.setAllowBlank(false);
		userName.setWidth(350);
		add(userName);

		password = new TextField<String>();
		password.setFieldLabel("Password");
		password.setPassword(true);
		password.setAllowBlank(false);
		password.setWidth(350);
		add(password);
	}

	protected void createButtons() {
		super.createButtons();
		status = new Status();
		status.setBusy("please wait...");
		status.hide();
		status.setAutoWidth(true);
		getButtonBar().add(status);
		getButtonBar().add(new FillToolItem());

		cancel = new Button("Cancel");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				RepositorySelectionDialog.this.hide();
			}
		});
		addButton(cancel);

		save = new Button(type);
		save.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if ( type.equalsIgnoreCase("Export") ) {
					String servicesUri = repository.getValue() + "/data/services"; // complete the services URI
					String userNameString = userName.getValue();
					String passwordString = password.getValue();
					if ( servicesUri != null && servicesUri != "" &&
						userNameString != null && userNameString != "" &&
						passwordString != null && passwordString != "" ) {
						RepositorySelectionDialog.this.hide();
						ControllerToolkit.saveToRepository(servicesUri, userNameString, passwordString);
					}
				} else if ( type.equalsIgnoreCase("Save") ) {
					String documentsUri = repository.getValue() + "/data/documents"; // complete the services URI
					String userNameString = userName.getValue();
					String passwordString = password.getValue();
					if ( documentsUri != null && documentsUri != "" &&
						userNameString != null && userNameString != "" &&
						passwordString != null && passwordString != "" ) {
						RepositorySelectionDialog.this.hide();
						ControllerToolkit.saveToRepository(documentsUri, userNameString, passwordString);
					}
				}
			}
		});
		addButton(save);
	}

}
