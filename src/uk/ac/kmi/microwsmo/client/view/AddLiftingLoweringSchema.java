package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.util.Message;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;


public class AddLiftingLoweringSchema extends Dialog {

	protected TextField<String> uri;
	
	protected Button cancel;
	protected Button ok;

	protected String oldValue;;

	public AddLiftingLoweringSchema(String dialogName, String oldValue) {
		FormLayout layout = new FormLayout();
		 layout.setLabelWidth(90);
		 layout.setDefaultWidth(155);
		 setLayout(layout);
		    
		this.oldValue = oldValue;
		setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setHeading("Add a " + dialogName+ " Schema");
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
		
		setFocusWidget(ok);
		setOnEsc(true);
	}

	private boolean goOn = false;
	
	protected void createButtons() {
		super.createButtons();
	
		cancel = new Button("Cancel");
		cancel.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				AddLiftingLoweringSchema.this.hide();
			}
		});
		addButton(cancel);

		ok = new Button("Ok");
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				String newUri = uri.getValue();
				
					if ( uri.getValue() != null && uri.getValue() != "") {
						
						
						//OK button problem
						/*boolean urlIsValid = checkURL(newUri);
						if(!urlIsValid){
							final Listener<MessageBoxEvent> l = new Listener<MessageBoxEvent>(){
						      public void handleEvent(MessageBoxEvent ce) {
						          Button btn = ce.getButtonClicked();
						          if (btn.getText().toLowerCase().equals("no")){
						          }  else {
						        	  if(oldValue == null){
											AddLiftingLoweringSchema.this.hide();
											
											//Create schema mapping
											ServiceStructureContextMenu.createSchemaReference(uri.getValue());
											
										}else if(oldValue.trim() == ""){
											AddLiftingLoweringSchema.this.hide();
											
											//Create schema mapping
											ServiceStructureContextMenu.createSchemaReference(uri.getValue());
											
										} else if (uri.getValue().trim() != oldValue.trim()){
											AddLiftingLoweringSchema.this.hide();
											
											//Update schema mapping								
											ServiceStructureContextMenu.updateSchemaReference(uri.getValue());
										}
						          }
						      
						        }
							};
								
							MessageBox.confirm("Url Error", "The input does not seem to be a valid URL. Continue anyway?", l);
						} else {*/
							  if(oldValue == null){
									AddLiftingLoweringSchema.this.hide();
									
									//Create schema mapping
									ServiceStructureContextMenu.createSchemaReference(uri.getValue());
									
								}else if(oldValue.trim() == ""){
									AddLiftingLoweringSchema.this.hide();
									
									//Create schema mapping
									ServiceStructureContextMenu.createSchemaReference(uri.getValue());
									
								} else if (uri.getValue().trim() != oldValue.trim()){
									AddLiftingLoweringSchema.this.hide();
									
									//Update schema mapping								
									ServiceStructureContextMenu.updateSchemaReference(uri.getValue());
								}
						//}
						
					} else{
						Message.show(Message.EXISTINGID);
					}
			}
		});
		addButton(ok);
	}
	

	public static native boolean checkURL(String s)  /*-{
		var regexp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/
		
		var testResult = regexp.test(s);
		
		console.log(testResult);
		return testResult;
	}-*/;

	
	
	//private Boolean checkURL(String url) {
	/*	 Pattern p = Pattern.compile("^((https?|ftp)://|(www|ftp)\\.)[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$");
	  //   Matcher m = p.matcher(url);
	     if(m.matches()){
	    	 return true;
	     }
	     else{
	    	 return false;
	     }
	     
		//(http|https)://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?");
		//^((https?|ftp)://|(www|ftp)\.)[a-z0-9-]+(\.[a-z0-9-]+)+([/?].*)?$
		//^(https?|ftp|file)://.+$
	 
	}*/
	//	return true;
	//}

}

