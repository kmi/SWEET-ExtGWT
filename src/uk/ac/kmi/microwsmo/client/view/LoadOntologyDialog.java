package uk.ac.kmi.microwsmo.client.view;

//import java.util.regex.Matcher;
//import java.util.regex.Pattern;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.controller.SemanticController;
import uk.ac.kmi.microwsmo.client.util.Message;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.VerticalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class LoadOntologyDialog extends Dialog {

	protected TextField<String> uri;
	
	protected Button ok;
	private static PopupPanel progressPanel;

	public LoadOntologyDialog() {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);
		    
		//setLayout(new FormLayout());

		setButtonAlign(HorizontalAlignment.RIGHT);
		setButtons("");
		setHeading("Load Ontology");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setResizable(true);
		setWidth(450);

		uri = new TextField<String>();
		uri.setFieldLabel("URI");
		uri.setAllowBlank(false);
		uri.setValue("");
		uri.setWidth(400);
		uri.setAutoHeight(true);
		add(uri);

		setOnEsc(true);
	}

	protected void createButtons() {
		super.createButtons();
		
		ok = new Button("Ok");
		ok.addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override
			public void componentSelected(ButtonEvent ce) {
				String newUri = uri.getValue();
				
				if (newUri == null || newUri.trim().length() == 0) {
					MessageBox.alert("Error123", newUri, null);
					
					return; // no error, no response
				}
				showProgressDialog("Loading resource, please wait ...");
				
				//FIXME very dirty impl
				SemanticController.loadOntology(newUri);
				
				setVisible(false);
			}
		});
		addButton(ok);
	}
	
	public void showProgressDialog(String message) {
		if (progressPanel == null) {
			progressPanel = new PopupPanel(false, true);
			//Image.prefetch("WSMOLiteModule/images/default/shared/large-loading.gif");
			HorizontalPanel container = new HorizontalPanel();
			//container.add(new Image("WSMOLiteModule/images/default/shared/large-loading.gif"));
			TableData td = new TableData();
			td.setVerticalAlign(VerticalAlignment.MIDDLE);
			td.setMargin(10);
			container.add(new Label(message), td);
			progressPanel.setWidget(container);
			progressPanel.center();
		}
		else {
			((Label)((HorizontalPanel)progressPanel.getWidget()).getWidget(1)).setText(message);
		}
		
		progressPanel.show();
	}
	
	public void clearProgressDialog() {
		if (progressPanel != null 
				&& progressPanel.isVisible()) {
			progressPanel.hide();
		}
	}
}
