package uk.ac.kmi.microwsmo.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.Padding;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;

public class AboutDialog extends Dialog {

	private Html aboutIServe;

	private Button okButton;

	public AboutDialog() {
		VBoxLayout layout = new VBoxLayout();  
        layout.setPadding(new Padding(5));  
        layout.setVBoxLayoutAlign(VBoxLayoutAlign.CENTER);  
		setLayout(layout);

		setButtons("");
		setButtonAlign(HorizontalAlignment.CENTER);
		setModal(true);
		setBodyBorder(true);
		setResizable(false);
		setClosable(false);
		setWidth(300);
		setHeight(150);

		setHeading("About SWEET");
		aboutIServe = new Html("<div align=center><b>SWEET v0.2</b></div>" +
				"<div align=center><a href='http://kmi.open.ac.uk/' target='_blank'><img src=\"kmi-logo-sm.png\" alt=\"KMi logo\" /></a>&nbsp;&nbsp;" +
				"<a href='http://www.soa4all.eu/' target='_blank'><img src=\"soa4all-logo-sm.png\" alt=\"SOA4All logo\" /></a></div>" +
				"<div align=center>Maria Maleshkova, Carlos Pedrinaci</div>" +
				"<div align=center>Knowledge Media Institute, The Open University</div>");
		add(aboutIServe);
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		okButton = new Button("OK");
		okButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				AboutDialog.this.hide();
			}
			
		});
		addButton(okButton);
	}

}

