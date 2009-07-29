package uk.ac.kmi.microwsmo.client.view;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.ComponentID.HREST;
import uk.ac.kmi.microwsmo.client.view.deprecated.DeprecatedBaseTree;

import com.extjs.gxt.ui.client.event.Events;

/**
 * 
 * @author Simone Spaccarotella
 */
public final class HrestTagsTree extends DeprecatedBaseTree {
	
	/* the tree's item */
	
	private HrestTagsTreeItem hrest;
	private HrestTagsTreeItem service;
	private HrestTagsTreeItem label;
	private HrestTagsTreeItem method;
	private HrestTagsTreeItem address;
	private HrestTagsTreeItem operation;
	private HrestTagsTreeItem input;
	private HrestTagsTreeItem output;
	
	/**
	 * Creates a new tree with only "k" children hardcoded, which
	 * represent the hREST tags
	 */
	public HrestTagsTree() {
		super(ComponentID.HREST_TAGS_TREE);
		initRoot();
		initService();
		initLabel();
		initMethod();
		initAddress();
		initOperation();
		initInput();
		initOutput();
		setDefaultState();
		addListener(Events.OnDoubleClick, MicroWSMOeditor.getController());
	}
	
	/**
	 * Set this component to the default state
	 */
	@Override
	public void setDefaultState() {
		setFirstLevel(false);
		setSecondLevel(false);
	}
	
	/**
	 * It visualize the following nodes:
	 * <ul>
	 * 	<li>label</li>
	 * 	<li>method</li>
	 *  <li>address</li>
	 *  <li>operation</li>
	 * </ul>
	 * 
	 * @param enabled
	 */
	public void setFirstLevel(boolean enabled) {
		label.setEnabled(enabled);
		method.setEnabled(enabled);
		address.setEnabled(enabled);
		operation.setEnabled(enabled);
	}
	
	/**
	 * It show the following nodes:
	 * <ul>
	 * 	<li>input</li>
	 * 	<li>output</li>
	 * </ul>
	 * @param visible
	 */
	public void setSecondLevel(boolean enabled) {
		input.setEnabled(enabled);
		output.setEnabled(enabled);
	}
	
	/* ********************************************************************* */
	/* ******* The names of the follow methods are auto descriptive. ******* */
	/* ****** They initializes each tree item and sets the properties. ***** */
	/* ********************************************************************* */
	
	private void initRoot() {
		hrest = new HrestTagsTreeItem("http://www.wsmo.org/ns/hrests#", CSSIconImage.RDF);
		addRootItem(hrest);
	}
	
	private void initService() {
		service = new HrestTagsTreeItem("Service", CSSIconImage.ONTO_CLASS);
		service.setID(HREST.SERVICE);
		addItem(hrest, service);
	}
	
	private void initLabel() {
		label = new HrestTagsTreeItem("Label", CSSIconImage.ONTO_CLASS);
		label.setID(HREST.LABEL);
		addItem(hrest, label);
	}
	
	private void initMethod() {
		method = new HrestTagsTreeItem("Method", CSSIconImage.ONTO_CLASS);
		method.setID(HREST.METHOD);
		addItem(hrest, method);
	}
	
	private void initAddress() {
		address = new HrestTagsTreeItem("Address", CSSIconImage.ONTO_CLASS);
		address.setID(HREST.ADDRESS);
		addItem(hrest, address);
	}
	
	private void initOperation() {
		operation = new HrestTagsTreeItem("Operation", CSSIconImage.ONTO_CLASS);
		operation.setID(HREST.OPERATION);
		addItem(hrest, operation);
	}
	
	private void initInput() {
		input = new HrestTagsTreeItem("Input", CSSIconImage.ONTO_CLASS);
		input.setID(HREST.INPUT);
		addItem(hrest, input);
	}
	
	private void initOutput() {
		output = new HrestTagsTreeItem("Output", CSSIconImage.ONTO_CLASS);
		output.setID(HREST.OUTPUT);
		addItem(hrest, output);
	}

}