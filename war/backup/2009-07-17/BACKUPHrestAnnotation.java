package uk.ac.kmi.microwsmo.client.controller;

public class BACKUPHrestAnnotation {
	
	/**
	 * Undo the hREST annotation. It is called when an hREST annotation
	 * is deleted from the page.
	 * 
	 * @param itemID the type of hREST tag
	 * @param id the id of the element
	 */
	private static native void undoAnnotation(String itemID, String id) /*-{
		@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::deleteElementByID(Ljava/lang/String;)(id);
		@uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::deleteItem(Ljava/lang/String;)(itemID);
		var wasTheLastService = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::wasTheLastServiceTag()();
		var wasTheLastOperation = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::wasTheLastOperationTag()();
		if( itemID == "service" && wasTheLastService ) {
			@uk.ac.kmi.microwsmo.client.controller.Controller::setFirstHrestLevel(Z)(false);
		} else if( itemID == "operation" && wasTheLastOperation ) {
			@uk.ac.kmi.microwsmo.client.controller.Controller::setSecondHrestLevel(Z)(false);
		}
	}-*/;

	/**
	 * The OLD version of the annotation method
	 */
	public static native void OLD_hrestAnnotation(String itemID) /*-{
		var selection;
		try {
			// retrieves the selection inside the iframe
			selection = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getSelection()();
			var range;
			try {
				range = selection.getRangeAt(0);
				var element;
				try {
					if (typeof range.surroundContents != "undefined") {
						// retrieve the element type related to the hREST tag
						var elementName = @uk.ac.kmi.microwsmo.client.util.ComponentID.HREST::getElement(Ljava/lang/String;)(itemID);
						// create it
						element = $doc.createElement(elementName);
						// wrap the selection with this element
						range.surroundContents(element);
					}
					else { 
						element = range.surroundContents;
					}
				} 
				catch (Error) {
					@com.extjs.gxt.ui.client.widget.MessageBox::alert(Ljava/lang/String;Ljava/lang/String;Lcom/extjs/gxt/ui/client/event/Listener;)("MicroWSMO Editor - Annotation Alert", "Highlight the text before annotate", null);
				}
				// generate a unique ID and set it to the element
				var id = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::getID(Ljava/lang/String;)(itemID);
				element.setAttribute("id", id);
				// set the class
				element.setAttribute("class", itemID);
				// handle the http comunication object
				var http_request = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
				// retrieve the html code inside the iFrame
		      	var html = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
		      	// wrap the html code inside the "html" element
		      	html = "<html>" + html + "</html>";
		      	// encode the parameter
			    var parameters = "html=" + encodeURIComponent(html);
			    // call the servlet by a POST method, in asynchronous mode 
				http_request.open("POST", "../internalModelGenerator", true);
				// set the header
				http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				http_request.setRequestHeader("Content-length", parameters.length);
				http_request.setRequestHeader("Connection", "close");
				// send the parameter
				http_request.send(parameters);
				// this function will be called when the servlet response will be ok
				http_request.onreadystatechange = function() {
					if( http_request.readyState == 4 ) {
						// if the servlet reply "ok" it means the annotation is legal
						if( http_request.responseText == "ok" ) {
							//@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::doAnnotation(Ljava/lang/String;)(itemID);
							var isTheFirstService = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::isTheFirstServiceTag()();
							var isTheFirstOperation = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::isTheFirstOperationTag()();
							if( itemID == "service" && isTheFirstService ) {
								@uk.ac.kmi.microwsmo.client.controller.Controller::setFirstHrestLevel(Z)(true);
							} else if( itemID == "operation" && isTheFirstOperation ) {
								@uk.ac.kmi.microwsmo.client.controller.Controller::setSecondHrestLevel(Z)(true);
							}
						} else {
							@com.extjs.gxt.ui.client.widget.MessageBox::alert(Ljava/lang/String;Ljava/lang/String;Lcom/extjs/gxt/ui/client/event/Listener;)("MicroWSMO Editor - hREST Requirement Violation", "The \"" + itemID + "\" element can't be inserted in this place", null);
							@uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::deleteElementByID(Ljava/lang/String;)(id);
						}
					}
				}
			} 
			catch (Error) {
				@com.extjs.gxt.ui.client.widget.MessageBox::alert(Ljava/lang/String;Ljava/lang/String;Lcom/extjs/gxt/ui/client/event/Listener;)("MicroWSMO Editor - Annotation Alert", "Highlight the text before annotate", null);
			}
		} catch (Error) {
			@com.extjs.gxt.ui.client.widget.MessageBox::alert(Ljava/lang/String;Ljava/lang/String;Lcom/extjs/gxt/ui/client/event/Listener;)("MicroWSMO Editor - Annotation Alert", "Highlight the text before annotate", null);
		}
	}-*/;
	
}
