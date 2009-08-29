/*	
 * MicroWSMO Editor - web based editor for semantic annotation of RESTful web services.
 * Copyright (C) 2009  KMi, The Open University
 * Knowledge Media Institute (KMI) - The Open University
 * Walton Hall - Milton Keynes, MK7 6AA - United Kingdom
 * http://kmi.open.ac.uk/
 */
package uk.ac.kmi.microwsmo.client;

import uk.ac.kmi.microwsmo.client.controller.Controller;
import uk.ac.kmi.microwsmo.client.view.AnnotationsTree;
import uk.ac.kmi.microwsmo.client.view.DomainOntologiesPanel;
import uk.ac.kmi.microwsmo.client.view.DomainOntologiesTree;
import uk.ac.kmi.microwsmo.client.view.EditorMenuBar;
import uk.ac.kmi.microwsmo.client.view.EditorViewport;
import uk.ac.kmi.microwsmo.client.view.ExportButton;
import uk.ac.kmi.microwsmo.client.view.HrestTagsTree;
import uk.ac.kmi.microwsmo.client.view.NavigatorTextField;
import uk.ac.kmi.microwsmo.client.view.QueryWatsonButton;
import uk.ac.kmi.microwsmo.client.view.SaveButton;
import uk.ac.kmi.microwsmo.client.view.SemanticAnnotationContextMenu;
import uk.ac.kmi.microwsmo.client.view.ServicePropertiesPanel;
import uk.ac.kmi.microwsmo.client.view.ServicePropertiesTree;
import uk.ac.kmi.microwsmo.client.view.ServiceStructureTree;
import uk.ac.kmi.microwsmo.client.view.WebPagesDisplay;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * This is the entry point of the application. It contains
 * the component of the editor as well.
 * 
 * @author KMi, The Open University
 */
public final class MicroWSMOeditor implements EntryPoint {
	
	/* VIEW */
	private static AnnotationsTree annotationsTree;
	private static EditorMenuBar editorMenuBar;
	private static WebPagesDisplay webPageDisplay;
	private static SaveButton saveButton;
	private static ExportButton exportButton;
	private static HrestTagsTree hrestTagsTree;
	private static SemanticAnnotationContextMenu semanticAnnotationContextMenu;
	private static ServicePropertiesPanel servicePropertiesPanel;
	private static ServiceStructureTree serviceStructureTree;
	private static ServicePropertiesTree servicePropertiesTree;
	private static DomainOntologiesPanel domainOntologiesPanel;
	private static DomainOntologiesTree domainOntologiesTree;
	private static QueryWatsonButton queryWatsonButton;
	private static NavigatorTextField navigatorTextField;
	private EditorViewport editorViewPort;
	
	/* CONTROLLER */
	private static Controller controller;
	
	
	/* *************** VIEW *************** */
	
	/**
	 * Returns the tree where are shown the annotated terms.
	 */
	public static AnnotationsTree getAnnotationsTree() {
		if( annotationsTree == null ) {
			annotationsTree = new AnnotationsTree();
		}
		return annotationsTree;
	}
	
	/**
	 * Returns the menu bar of the editor.
	 *  
	 * @return the menu bar.
	 */
	public static EditorMenuBar getEditorMenuBar() {
		if( editorMenuBar == null ) {
			editorMenuBar = new EditorMenuBar();
		}
		return editorMenuBar;
	}
	
	/**
	 * Returns the component which display the web service
	 * description.
	 * 
	 * @return the web page display.
	 */
	public static WebPagesDisplay getWebPageDisplay() {
		if( webPageDisplay == null ) {
			webPageDisplay = new WebPagesDisplay();
		}
		return webPageDisplay;
	}
	
	/**
	 * Returns the button which save the hrest
	 * annotation.
	 * 
	 * @return the save button.
	 */
	public static SaveButton getSaveButton() {
		if( saveButton == null ) {
			saveButton = new SaveButton();
		}
		return saveButton;
	}
	
	/**
	 * Returns the component which export the annotation
	 * in a RDF format.
	 * 
	 * @return the export button.
	 */
	public static ExportButton getExportButton() {
		if( exportButton == null ) {
			exportButton = new ExportButton();
		}
		return exportButton;
	}
	
	/**
	 * Returns the tree which visualize the hREST tags
	 * for the hREST annotation of the web page.
	 * 
	 * @return the hREST tags tree
	 */
	public static HrestTagsTree getHrestTagsTree() {
		if( hrestTagsTree == null ) {
			hrestTagsTree = new HrestTagsTree();
		}
		return hrestTagsTree;
	}
	
	/**
	 * 
	 * @return
	 */
	public static SemanticAnnotationContextMenu getSemanticAnnotationContextMenu() {
		if( semanticAnnotationContextMenu == null ) {
			semanticAnnotationContextMenu = new SemanticAnnotationContextMenu();
		}
		return semanticAnnotationContextMenu;
	}
	
	/**
	 * Returns the tree which visualize the schema of
	 * the hREST elements used inside the web document.
	 * 
	 * @return the hREST DOM tree
	 */
	public static ServiceStructureTree getServiceStructureTree() {
		if( serviceStructureTree == null ) {
			serviceStructureTree = new ServiceStructureTree();
		}
		return serviceStructureTree;
	}
	
	/**
	 * 
	 * @return
	 */
	public static ServicePropertiesPanel getServicePropertiesPanel() {
		if( servicePropertiesPanel == null ) {
			servicePropertiesPanel = new ServicePropertiesPanel();
		}
		return servicePropertiesPanel;
	}
	
	/**
	 * Returns the tree which visualize the service
	 * properties with all the entities retrieved with the related
	 * ontologies.
	 * 
	 * @return the servie properties tree
	 */
	public static ServicePropertiesTree getServicePropertiesTree() {
		if( servicePropertiesTree == null ) {
			servicePropertiesTree = new ServicePropertiesTree();
		}
		return servicePropertiesTree;
	}
	
	public static DomainOntologiesPanel getDomainOntologiesPanel() {
		if( domainOntologiesPanel == null ) {
			domainOntologiesPanel = new DomainOntologiesPanel();
		}
		return domainOntologiesPanel;
	}
	
	/**
	 * Returns the tree which visualize all the ontologies
	 * retrieved during every annotation session.
	 * 
	 * @return the domain ontology tree
	 */
	public static DomainOntologiesTree getDomainOntologiesTree() {
		if( domainOntologiesTree == null ) {
			domainOntologiesTree = new DomainOntologiesTree();
		}
		return domainOntologiesTree;
	}
	
	/**
	 * Returns the button which query Watson, to
	 * retrieve the ontologies of a given string
	 * of properties.
	 * 
	 * @return the query Watson button
	 */
	public static QueryWatsonButton getQueryWatsonButton() {
		if( queryWatsonButton == null ) {
			queryWatsonButton = new QueryWatsonButton();
		}
		return queryWatsonButton;
	}
	
	/**
	 * Returns the text field where the user have to write
	 * the URI of a resource, to navigate through the web.
	 * 
	 * @return the navigator textfield.
	 */
	public static NavigatorTextField getNavigatorTextField() {
		if( navigatorTextField == null ) {
			navigatorTextField = new NavigatorTextField();
		}
		return navigatorTextField;
	}
	
	/* *************** CONTROLLER *************** */
	
	/**
	 * Returns the controller.
	 * 
	 * @return the listener of the GUI.
	 */
	public static Controller getController() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}

	@Override
	public void onModuleLoad() {
		// creates the viewport
		editorViewPort = new EditorViewport();
		// adds the viewport to a DIV element of the html page
		// which have "editor" such as ID.
		RootPanel.get("editor").add(editorViewPort);
	}	

}