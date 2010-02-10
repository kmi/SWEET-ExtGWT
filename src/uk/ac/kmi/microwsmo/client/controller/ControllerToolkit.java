package uk.ac.kmi.microwsmo.client.controller;

import java.util.List;

import com.extjs.gxt.ui.client.data.ModelData;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.view.BaseTreeItem;
import uk.ac.kmi.microwsmo.client.view.DomainOntologiesTree;
import uk.ac.kmi.microwsmo.client.view.ServicePropertiesTree;


/**
 * 
 * 
 * @author KMi, The Open University
 */
public final class ControllerToolkit {

	/**
	 * Returns the handler for the asynchronous
	 * comunication with the server.
	 * 
	 * @return the XMLHttpRequest javascript Object.
	 */
	private static native Object getXMLHttpRequest() /*-{
		var httpRequest = null;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			httpRequest = new XMLHttpRequest();
		} else if (window.ActiveXObject) {
			// code for IE6, IE5
			httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return httpRequest;
	}-*/;
	
	/**
	 * Return the web page inside the iframe.
	 * 
	 * @return the content document of the iframe
	 */
	private static native Object getWebPage() /*-{
		// retrieve the iframe inside the page
		return $doc.getElementById("webPageDisplay").lastChild.firstChild.firstChild.firstChild;
	}-*/;
	
	/**
	 * Returns the iFrame's html code encoded in UTF-8
	 * 
	 * @return an encoded string.
	 */
	public static native String retrieveIframeHTML() /*-{
		var webPage = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
		var stringa = webPage.contentDocument.getElementById("html").innerHTML; 
		stringa = stringa.replace(/\r\n/g, "\n");
		var utftext = "";
		for( var n = 0; n < stringa.length; n++ ) {
			var c = stringa.charCodeAt(n);
			if( c < 128 ) {
				utftext += String.fromCharCode(c);
			} else { 
				if( (c > 127) && (c < 2048) ) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				}
				else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
			}
		}
		return utftext;
	}-*/;

	
	private static String trim(String stringa) { 
		return stringa.trim();
	}
	
	/**
	 * Retrieve the text selected by the user in the iFrame.
	 * 
	 * @return the selected text of the iFrame.
	 */
	public static native String getTextSelected() /*-{
		var webPage = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
		var selection = webPage.contentDocument.getSelection(); 
		return @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::trim(Ljava/lang/String;)(selection);
	}-*/;
	
	/**
	 * Returns an object which can be manipulated for wrap the
	 * selected portion of code with a new element.
	 *  
	 * @return the Javascript Selection Object. 
	 */
	public static native Object getSelection() /*-{
		var webPage = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
		return webPage.contentWindow.getSelection();
	}-*/;
	
	public static native void createSession() /*-{
		// handle the http comunication object
		var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
		if( httpRequest != null ) {
			httpRequest.open("GET", "../updater", true);
			httpRequest.send(null);
		}
	}-*/;
	
	public static void updateState() {
		// service properties
		updateStateOf("getSp");
		// domain ontologies
		updateStateOf("getDo");
	}
	
	private static native void updateStateOf(String method) /*-{
		// handle the http comunication object
		var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
		if( httpRequest != null ) {
			httpRequest.open("POST", "../updater", true);
			var parameter = "method=" + method;
			// set the header
			httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			httpRequest.setRequestHeader("content-length", parameter.length);
			httpRequest.setRequestHeader("connection", "close");
			httpRequest.send(parameter);
			httpRequest.onreadystatechange = function() {
				if( httpRequest.readyState == 4 && httpRequest.status == 200) {
					var result = httpRequest.responseText;
					if( method == "getSp" ) {
						@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::updateServiceProperties(Ljava/lang/String;)(result);
					} else if( method == "getDo" ) {
						@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::updateDomainOntologies(Ljava/lang/String;)(result);
					}
				}
			}
		}
	}-*/;
	
	private static void updateServiceProperties(String result) {
		if( result.equals("") ) return;
		
		ServicePropertiesTree tree = MicroWSMOeditor.getServicePropertiesTree();
		
		String[] terms = result.split("<;>");
		for( String term : terms ) {
			String[] elements = term.split("<!>");
			
			String keyword = elements[0];
			BaseTreeItem termItem = new BaseTreeItem(keyword, CSSIconImage.TERM);
			termItem.setKind(3);
			tree.addRootItem(termItem);
			
			String[] entities = elements[1].split("<&>");
			for( String entity : entities ) {
				if( !entity.equals("viewMore") ) {
					String[] chunk = entity.split("<:>");
					
					String name = chunk[1];
					String icon = CSSIconImage.getEntityIcon(chunk[0]);
					
					BaseTreeItem entityItem = new BaseTreeItem(name, icon);
					entityItem.setKind(2);
					tree.addItem(termItem, entityItem);
					
					for( int i = 2; i < chunk.length; i++ ) {
						BaseTreeItem ontologyItem = new BaseTreeItem(chunk[i], CSSIconImage.RDF);
						ontologyItem.setKind(1);
						tree.addItem(entityItem, ontologyItem);
					}
				} else {
					BaseTreeItem entityItem = new BaseTreeItem("view more");
					entityItem.setID("viewMore" + keyword);
					tree.addItem(termItem, entityItem);
				}
			}
		}
		tree.sortBy("sorterField");
	}
	
	private static void updateDomainOntologies(String result) {
		if( result.equals("") ) return;
		
		// retrieve the tree
		DomainOntologiesTree tree = MicroWSMOeditor.getDomainOntologiesTree();

		String[] terms = result.split("<;>");
		for( String term : terms ) {
			String termName = term.split("<!>")[0];
			String[] ontologies = term.split("<!>")[1].split("<&>");

			for( String ontology : ontologies ) {
				String[] elements = ontology.split("<:>");
				String ontoURI = elements[0];
				
				BaseTreeItem ontologyItem = null;
				boolean canContinue = true;
				
				// if the ontology have not been retrieved yet
				if( !tree.contains(ontoURI) ) {
					// create a new root item
					ontologyItem = new BaseTreeItem(ontoURI, CSSIconImage.RDF);
					ontologyItem.setKind(1);
					tree.addRootItem(ontologyItem);
					
					BaseTreeItem concepts = new BaseTreeItem("Concepts", CSSIconImage.FOLDER_CLOSED);
					tree.addItem(ontologyItem, concepts);
				// otherwise
				} else {
					// retrieve the root item
					ontologyItem = tree.getItemById(ontoURI);
					
					List<ModelData> children = tree.getChildren(ontologyItem);
					for( ModelData child : children ) {
						BaseTreeItem item = (BaseTreeItem) child;
						if( item.getID().equals(termName) ) {
							canContinue = false;
							break;
						}
					}
				}
				
				if( canContinue ) {
					BaseTreeItem termItem = new BaseTreeItem(termName, CSSIconImage.TERM);
					tree.addItem(ontologyItem, termItem);
					String icon = "";
					for( int i = 1; i < elements.length; i++ ) {
						if( (i % 2) != 0 ) {
							icon = CSSIconImage.getEntityIcon(elements[i]);
						} else {
							String name = elements[i];
							BaseTreeItem entity = new BaseTreeItem(name, icon);
							entity.setKind(2);
							tree.addItem(termItem, entity);
						}
					}
					
				}
			}
		}
	}
	
	/**
	 * Saves the web content of the iframe in a local file.
	 */
	public static native void save() /*-{
		// handle the http comunication object
		var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
		if( httpRequest != null ) {
			// retrieve the html code from the iFrame
		  	var html = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
		  	// wrap it inside the "html" elements
		    html = "<html>" + html + "</html>";
		    // and encode it for the URI
		    var parameter = "html=" + encodeURIComponent(html);
		    // call the servlet by a POST method, in asynchronous mode
			httpRequest.open("POST", "../save", true);
			// set the header
			httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			httpRequest.setRequestHeader("content-length", parameter.length);
			httpRequest.setRequestHeader("connection", "close");
			// send the parameter
			httpRequest.send(parameter);
			httpRequest.onreadystatechange = function(){
				if( httpRequest.readyState == 4 && httpRequest.status == 200 ) {
					if( httpRequest.responseText == "ok" ) {
						open("../save");
					}
				}
			}
	    } else {
	    	@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    	(@uk.ac.kmi.microwsmo.client.util.Message::AJAX);
		}
	}-*/;
	
	/**
	 * Exports the web content of the iframe in a local RDF/XML file.
	 */
	public static native void export() /*-{
		// handle the http comunication object
		var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
		if( httpRequest != null ) {
			// retrieve the html code from the iFrame
		  	var html = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
		  	// wrap it inside the "html" elements
		    html = "<html>" + html + "</html>";
		    // and encode it for the URI
		    var parameter = "html=" + encodeURIComponent(html);
		    // call the servlet by a POST method, in asynchronous mode
			httpRequest.open("POST", "../export", true);
			// set the header
			httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			httpRequest.setRequestHeader("content-length", parameter.length);
			httpRequest.setRequestHeader("connection", "close");
			// send the parameter
			httpRequest.send(parameter);
			httpRequest.onreadystatechange = function(){
				if( httpRequest.readyState == 4 && httpRequest.status == 200 ) {
					if( httpRequest.responseText == "ok" ) {
						open("../export");
					}
				}
			}
	    } else {
	    	@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    	(@uk.ac.kmi.microwsmo.client.util.Message::AJAX);
		}
	}-*/;
	
	/**
	 * Shows the source file in a new window's tab.
	 *  
	 * @param path the path of the file.
	 */
	public static native void showFile(String path) /*-{
		open("../showFile?path=" + path);
	}-*/;
	
	/**
	 * Calls the proxy. The client can't call directly
	 * the hosts out its domain, so the proxy has to retrieve
	 * the web page and send it back to the client. 
	 * 
	 * @param url the url of the web resource
	 */
	public static native void callProxy(String url) /*-{
		if( url != null ) {
			if( url != "" ) {
				// handle the http comunication object
				var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
				if( httpRequest != null ) {
					var parameters = "url=" + encodeURIComponent(url);
					// call the servlet by a POST method, in asynchronous mode
					httpRequest.open("POST", "../proxy", true);
					// set the header
					httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
					httpRequest.setRequestHeader("Content-length", parameters.length);
					httpRequest.setRequestHeader("Connection", "close");
					httpRequest.onreadystatechange = function(){
						if( httpRequest.readyState == 4 && httpRequest.status == 200 ) {
							var webPage = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
							webPage.contentDocument.getElementById("html").innerHTML = httpRequest.responseText;
						}
					}
					// and send the parameter
					httpRequest.send(parameters);
				} else {
					@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    			(@uk.ac.kmi.microwsmo.client.util.Message::AJAX);
				}
			} else {
				@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    		(@uk.ac.kmi.microwsmo.client.util.Message::EMPTY_URI);
			}
		} else {
			@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    	(@uk.ac.kmi.microwsmo.client.util.Message::INVALID_URI);
		}
	}-*/;
	
	/**
	 * Deletes the element inside the iFrame html page with
	 * the id {@code id}.
	 * 
	 * @param id the element's ID
	 */
	public static native void deleteElementByID(String id) /*-{
		// get the web page
		var iframe = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
		// retrieve the element inside the iFrame with the ID specified in input
		var element = iframe.contentDocument.getElementById(id)
		// call the method wich delete the element
		@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::deleteElement(Ljava/lang/Object;)(element);
	}-*/;
	
	/**
	 * Detele the element from the web page.
	 * 
	 * @param element the element to be deleted
	 */
	public static native void deleteElement(Object element) /*-{
		var children = element.childNodes;
		var parent = element.parentNode;
		var j = children.length;
		// insert the children of thi element one level up
		for( var i = 0; i < j; i++ ) {
			var item = children[0];
			parent.insertBefore(item, element);
		}
		// and delete it
		parent.removeChild(element);
	}-*/;

	/**
	 * Saves the web content of the iframe in a local file.
	 */
	public static native void saveToRepository(String uri, String username, String password) /*-{
		// handle the http comunication object
		var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
		if( httpRequest != null ) {
			// retrieve the html code from the iFrame
	  		var html = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
	  		// wrap it inside the "html" elements
	    	html = "<html>" + html + "</html>";
	    	// and encode it for the URI
	    	var parameter = "uri=" + encodeURIComponent(uri)
	    		+ "&user=" + username + "&password=" + password
	    		+ "&html=" + encodeURIComponent(html);
	    	// call the servlet by a POST method, in asynchronous mode
			httpRequest.open("POST", "../savetorepo", true);
			// set the header
			httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			httpRequest.setRequestHeader("content-length", parameter.length);
			httpRequest.setRequestHeader("connection", "close");
			httpRequest.onreadystatechange = function(){
				if( httpRequest.readyState == 4 && httpRequest.status == 200 ) {
					if( httpRequest.responseText != "null" && httpRequest.responseText != "" ) {
						@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;Ljava/lang/String;)
						(@uk.ac.kmi.microwsmo.client.util.Message::STORE_SUCCESS, httpRequest.responseText);
					} else {
						@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
						(@uk.ac.kmi.microwsmo.client.util.Message::STORE_FAIL);
					}
				}
			}
			// send the parameter
			httpRequest.send(parameter);
		} else {
			@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
			(@uk.ac.kmi.microwsmo.client.util.Message::STORE_FAIL);
		}
	}-*/;
}