package uk.ac.kmi.microwsmo.client.view;

import java.util.List;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.util.Message;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.event.dom.client.KeyCodes;

public class RenameDialog extends Dialog {

	protected TextField<String> name;
	
	protected Button cancel;
	protected Button ok;

	protected final String oldName;

	public RenameDialog(String n) {
		 FormLayout layout = new FormLayout();
		 layout.setLabelWidth(90);
		 layout.setDefaultWidth(155);
		 setLayout(layout);
		    
		this.oldName = n;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setHeading("Rename Property");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setResizable(true);
		setWidth(350);

		name = new TextField<String>();
		name.setFieldLabel("Property");
		name.setAllowBlank(false);
		name.setValue(oldName);
		name.setWidth(300);
		name.setAutoHeight(true);
		add(name);
		
		setFocusWidget(name);
		setOnEsc(true);
		
		this.addListener(Events.OnKeyPress, new Listener<KeyEvent>() {
			public void handleEvent(KeyEvent ce) {
				if (ce.getKeyCode() == KeyCodes.KEY_ENTER){
					
					String newName = name.getValue();
					
					if ( newName != null && newName != "" && newName.trim() != oldName.trim()) {
						if(Controller.checkUniqueId(newName)){
								newName = newName.replaceAll(" ", "_");
								newName = newName.replaceAll("\\s", "_");
							
								RenameDialog.this.hide();
						
								ServiceStructureContextMenu.renameProperty(newName);
						}
						else{
							Message.show(Message.EXISTINGID);
						}
					}
					
				} else if (ce.getKeyCode() == KeyCodes.KEY_ESCAPE){
							RenameDialog.this.hide();
				}
			}
		});
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
		ok.focus();
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
					String newName = name.getValue();
					
					if ( newName != null && newName != "" && newName.trim() != oldName.trim()) {
						if(Controller.checkUniqueId(newName)){
							
								newName = newName.replaceAll(" ", "_");
								newName = newName.replaceAll("\\s", "_");

								RenameDialog.this.hide();
						
								ServiceStructureContextMenu.renameProperty(newName);
						}
						else{
							Message.show(Message.EXISTINGID);
						}
					}
			}
		});
		
		addButton(ok);
	}
	
	
}
