package uk.ac.kmi.microwsmo.client.view;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.Message;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.ContentPanel;

public class AddModelReferenceDialog extends Dialog {

	protected TextField<String> uri;
	
	protected Button cancel;
	protected Button ok;
	
	private OwnOntologyTree tree;
	private FitLayout layout1;

	protected String oldValue;

	public AddModelReferenceDialog(String oldValue) {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);
		    
		this.oldValue = oldValue;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setHeading("Add a Model Reference");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setResizable(true);
		setWidth(450);

		uri = new TextField<String>();
		uri.setFieldLabel("URI");
		uri.setAllowBlank(false);
		uri.setValue(oldValue);
		uri.setWidth(400);
		uri.setAutoHeight(true);
		add(uri);
		
		/*ContentPanel ontologiesPanel = new ContentPanel();
		tree = MicroWSMOeditor.getOwnOntologyTree();
		ontologiesPanel.setHeading("Loaded Ontologies");
		ontologiesPanel.add(tree);
		layout1 = new FitLayout();
		ontologiesPanel.setLayout(layout1);
		add(ontologiesPanel);*/
		
		setFocusWidget(ok);
		setOnEsc(true);
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
				String newUri = uri.getValue();
				
					if ( newUri != null && newUri != "") {
						boolean urlIsValid = checkURL(newUri);
						if(!urlIsValid){final Listener<MessageBoxEvent> l = new Listener<MessageBoxEvent>(){
						      
							public void handleEvent(MessageBoxEvent ce) {
						          Button btn = ce.getButtonClicked();
						          if (btn.getText().toLowerCase().equals("no")){
						          }  else {
						        	  if(oldValue == null){
											//Create model reference
											AddModelReferenceDialog.this.hide();
											
											ServiceStructureContextMenu.createModelReference(uri.getValue());
											
										}else if(oldValue.trim() == ""){
											//Create model reference
											
											AddModelReferenceDialog.this.hide();
											
											ServiceStructureContextMenu.createModelReference(uri.getValue());
											
										} else if (uri.getValue().trim() != oldValue.trim()){
											AddModelReferenceDialog.this.hide();
											
											//Update model reference								
											ServiceStructureContextMenu.updateModelReference(uri.getValue());
										}
						          }
						      
						        }
							};
								
							MessageBox.confirm("Url Error", "The input does not seem to be a valid URL. Continue anyway?", l);
						} else{
							if(oldValue == null){
								//Create model reference
								AddModelReferenceDialog.this.hide();
								
								ServiceStructureContextMenu.createModelReference(newUri);
								
							}else if(oldValue.trim() == ""){
								//Create model reference
								
								AddModelReferenceDialog.this.hide();
								
								ServiceStructureContextMenu.createModelReference(newUri);
								
							} else if (newUri.trim() != oldValue.trim()){
								AddModelReferenceDialog.this.hide();
								
								//Update model reference								
								ServiceStructureContextMenu.updateModelReference(newUri);
							}
						}
						//Controller.renameProperty(servicesUri, userNameString, passwordString);
					} else{
						Message.show(Message.EXISTINGID);
					}
			}
		});
		addButton(ok);
	}
	
	public static native boolean checkURL(String s)  /*-{
		var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
		
		var testResult = regexp.test(s);console.log(testResult);
		return testResult;
	}-*/;
}
