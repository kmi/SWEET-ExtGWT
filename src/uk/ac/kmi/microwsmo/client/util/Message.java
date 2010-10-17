package uk.ac.kmi.microwsmo.client.util;

import com.extjs.gxt.ui.client.widget.MessageBox;

public final class Message {
	
	private static final String title = "MicroWSMO editor - ";
	
	public final static String AJAX = "ajax";
	public final static String SEMANTIC_ANNOTATION = "semantic_annotation";
	public final static String EMPTY_URI = "empty_URI";
	public final static String INVALID_URI = "invalid_URI";
	public final static String ANNOTATION_SELECTION = "hrest_selection";
	public final static String WATSON_SELECTION = "watson_selection";
	public final static String TERM_ALREADY_RETRIEVED = "term_already_retrieved";
	public final static String NO_WATSON_RESULT = "no_watson_result";
	public final static String STORE_SUCCESS = "store_success";
	public final static String STORE_FAIL = "store_fail";
	public final static String ERROR = "error";
	public final static String DIV_ELEMENT = "div_element";
	public final static String EXISTINGID = "existing_id";
	public final static String DELETEMREF = "deletemref";
	public final static String ADDMREF = "addmref";
	public final static String ADDMREF1 = "addmref1";
	public final static String ADDLFSCHEMA = "addliftingloweringschema";
	
	public static void show(String command) {
		show(command, "");
	}
	
	public static void show(String command, String param) {
		if( command.equals(AJAX) ) {
			MessageBox.alert(title + "AJAX error", "The browser doesn't support the AJAX object!", null).setIcon(MessageBox.ERROR);
		} else if( command.equals(SEMANTIC_ANNOTATION) ) {
			MessageBox.alert(title + "Semantic Annotation warning", "The highlighted keyword doe not correspond to the chosen entity! Do you want to do the annotation anyway?", null);
		} else if( command.equals(EMPTY_URI) ) {
			MessageBox.alert(title + "Empty URI warning", "The empty string is not a valid URI!", null);
		} else if( command.equals(INVALID_URI) ) {
			MessageBox.alert(title + "Invalid URI warning", "The typed string is not a valid URI!", null);
		} else if( command.equals(ANNOTATION_SELECTION) ) {
			MessageBox.alert(title + "Annotation warning", "You should select the text before annotating.", null);
		} else if( command.equals(WATSON_SELECTION) ) {
			MessageBox.alert(title + "Watson warning", "You should highlight a term from the text, before querying Watson.", null);
		} else if( command.equals(TERM_ALREADY_RETRIEVED) ) {
			MessageBox.alert(title + "Watson warning", "The keyword \"" + param + "\" is already retrieved!", null);
		} else if( command.equals(NO_WATSON_RESULT) ) {
			MessageBox.info(title + "Watson info", "Watson returned no search results for this keyword!", null);
		} else if( command.equals(STORE_SUCCESS) ) {
			MessageBox.info(title + "Repository info", "Successfully save to the repository. The URI is " + param, null);
		} else if( command.equals(STORE_FAIL) ) {
			MessageBox.alert(title + "Repository warning", "Fail to save to the repository", null);
		} else if( command.equals(DIV_ELEMENT) ) {
			MessageBox.alert(title + "Annotation warning", "Your selection will breakup the HTML code.", null);
		} else if( command.equals(EXISTINGID) ) {
			MessageBox.alert(title + "Existing Id warning", "Please choose a different name! This element id already exists.", null);
		} else if( command.equals(DELETEMREF) ) {
			MessageBox.alert(title + "Delete", "Please delete the entity node in order to remove the model reference.", null);
		} else if( command.equals(ADDMREF) ) {
			MessageBox.alert(title + "Model reference", "You can add model reference only to input and output elements.", null);
		} else if( command.equals(ADDMREF1) ) {
			MessageBox.alert(title + "Model reference", "You can add model reference only to service and operation elements.", null);
		} else if( command.equals(ADDLFSCHEMA) ) {
			MessageBox.alert(title + "Schema mapping", "You can add lifting and lowering schemas only to input and output elements.", null);
		}
		
		
		
	}
	
}
