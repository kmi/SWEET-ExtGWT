package uk.ac.kmi.microwsmo.client.controller;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.view.BaseTreeItem;
import uk.ac.kmi.microwsmo.client.view.HrestTagsTree;
import uk.ac.kmi.microwsmo.client.view.ServiceStructureTree;

/**
 * 
 * 
 * @author KMi, The Open University
 */
final class HRESTController {
	
	/**
	 * Annotate the web page with the hrest tag.
	 */
	public static native void annotate(String hrestTag) /*-{
		try {
            // retrieves the selection inside the iframe
			var selection = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getSelection()();
            try {
                var range = selection.getRangeAt(0);
                try {
                    if (typeof range.surroundContents != "undefined") {
                        var elementName = @uk.ac.kmi.microwsmo.client.util.ComponentID.HREST::getElement(Ljava/lang/String;)(hrestTag);
                        if (elementName != null) {
                        	var oldCode = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
                            var element = $doc.createElement(elementName);
                            range.surroundContents(element);
                            var isOK = @uk.ac.kmi.microwsmo.client.controller.HRESTController::parse(Ljava/lang/Object;Ljava/lang/String;)(element,hrestTag);
                            if( isOK ) {
                            	// annotate the web page
                            	var parentID = @uk.ac.kmi.microwsmo.client.controller.HRESTController::annotateWebPage(Ljava/lang/Object;Ljava/lang/String;)(element,hrestTag);
                            	// update the service structure tree
                            	@uk.ac.kmi.microwsmo.client.controller.HRESTController::updateServiceStructureTree(Ljava/lang/String;Ljava/lang/String;)(parentID, element.id);
                            	// update the hrest tags tree
                            	@uk.ac.kmi.microwsmo.client.controller.HRESTController::updateHrestTagsTree(Ljava/lang/String;)(hrestTag);
                            } else {
                            	var webPage = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getWebPage()();
                            	webPage.contentDocument.getElementById("html").innerHTML = oldCode;
                            }
                        }
                    }
                } catch (Error) {
                    @uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    			(@uk.ac.kmi.microwsmo.client.util.Message::ANNOTATION_SELECTION);
                }
            } catch (Error) {
                @uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    			(@uk.ac.kmi.microwsmo.client.util.Message::ANNOTATION_SELECTION);
            }
        } catch (Error) {
            @uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
	    			(@uk.ac.kmi.microwsmo.client.util.Message::ANNOTATION_SELECTION);
        }
	}-*/;
	
	/**
	 * Parse the document checking if the hrest tag could be inserted or not.
	 * 
	 * @param element the element inserted.
	 * @param hrestTag the hrest tag.
	 * @return true if the hrest tag can be inserted into the web page.
	 */
	private static native boolean parse(Object element, String hrestType) /*-{
		if (hrestType == "service") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkService(Ljava/lang/Object;)(element);
        } else if (hrestType == "label") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkLabel(Ljava/lang/Object;)(element);
		} else if (hrestType == "method") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkMethod(Ljava/lang/Object;)(element);
		} else if (hrestType == "address") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkAddress(Ljava/lang/Object;)(element);
		} else if (hrestType == "operation") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkOperation(Ljava/lang/Object;)(element);
		} else if (hrestType == "input") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkInput(Ljava/lang/Object;)(element);
		} else if (hrestType == "output") {
			return @uk.ac.kmi.microwsmo.client.controller.HRESTController::checkOutput(Ljava/lang/Object;)(element);
		} else {
			return false;
		}
	}-*/;
	
	/**
	 * This is the real method which annotate the web page. This method
	 * add the id and the class property to the element.
	 * 
	 * @param element The new element inserted.
	 * @param hrestTag The hrest tag inserted.
	 * @return return The id of the hrest parent. 
	 */
	private static native String annotateWebPage(Object element, String hrestTag) /*-{
		// set the ID
    	var id = @uk.ac.kmi.microwsmo.client.util.ComponentID.IDGenerator::getID(Ljava/lang/String;)(hrestTag);
        element.setAttribute("id", id);
        // and set the kind of hrest tag into the class attribute
        element.setAttribute("class", hrestTag);
        var parent = element.parentNode;
        var className = parent.className;
        while( parent.nodeName != "BODY" && className != "service" && className != "label" && className != "method" && className != "address" 
        		&& className != "operation" && className != "input" && className != "output" ) {
        	parent = parent.parentNode;
        	className = parent.className;
        }
        if( parent.nodeName == "BODY" ) {
        	return "hrest";
        } else {
	        return parent.id;
        }
	}-*/;
	
	/**
	 * 
	 * @param element
	 */
	private static native void checkService(Object element) /*-{
		var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "service" || className == "label" || className == "method" || className == "address" ||
            className == "operation" ||
            className == "input" ||
            className == "output") {
                return false;
            }
            parent = parent.parentNode;
        }
        return true;
	}-*/;
	
	private static native void checkLabel(Object element) /*-{
		var result = false;
        var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "label" || className == "method" || className == "address" || className == "input" || className == "output") {
                return false;
            }
            if (className == "service" || className == "operation") {
                result = true;
            }
            parent = parent.parentNode;
        }
        return result;
	}-*/;
	
	private static native void checkMethod(Object element) /*-{
		var result = false;
	    var parent = element.parentNode;
	    while (parent.nodeName != "BODY") {
	        var className = parent.className;
	        if (className == "label" || className == "method" || className == "address" || className == "input" || className == "output") {
	            return false;
	        }
	        if (className == "service" || className == "operation") {
	            result = true;
	        }
	        parent = parent.parentNode;
	    }
	    return result;
	}-*/;
	
	private static native void checkAddress(Object element) /*-{
		var result = false;
        var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "label" || className == "method" || className == "address" || className == "input" || className == "output") {
                return false;
            }
            if (className == "service" || className == "operation") {
                result = true;
            }
            parent = parent.parentNode;
        }
        return result;
	}-*/;
	
	private static native void checkOperation(Object element) /*-{
		var result = false;
        var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "label" || className == "method" || className == "address" || className == "operation" || className == "input" || className == "output") {
                return false;
            }
            if (className == "service") {
                result = true;
            }
            parent = parent.parentNode;
        }
        return result;
	}-*/;
	
	private static native void checkInput(Object element) /*-{
		var result = false;
        var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "label" || className == "method" || className == "address" || className == "input" || className == "output") {
                return false;
            }
            if (className == "operation") {
                result = true;
            }
            parent = parent.parentNode;
        }
        return result;
	}-*/;
	
	private static native void checkOutput(Object element) /*-{
		var result = false;
        var parent = element.parentNode;
        while (parent.nodeName != "BODY") {
            var className = parent.className;
            if (className == "label" || className == "method" || className == "address" || className == "input" || className == "output") {
                return false;
            }
            if (className == "operation") {
                result = true;
            }
            parent = parent.parentNode;
        }
        return result;
	}-*/;
	
	private static void updateServiceStructureTree(String parentID, String childID) {
		ServiceStructureTree tree = MicroWSMOeditor.getServiceStructureTree();
		BaseTreeItem parent = tree.getItemById(parentID);
		tree.addItem(parent, new BaseTreeItem(childID, CSSIconImage.FOLDER_CLOSED));
	}
	
	private static void updateHrestTagsTree(String hrestTag) {
		if( hrestTag.startsWith("service") ) {
			if( ComponentID.IDGenerator.isTheFirstServiceTag() ) {
				HRESTController.setFirstHrestLevel(true);
			} else if( ComponentID.IDGenerator.wasTheLastServiceTag() ) {
				HRESTController.setFirstHrestLevel(false);
			}
		} else if( hrestTag.startsWith("operation") ) {
			if( ComponentID.IDGenerator.isTheFirstOperationTag() ) {
				HRESTController.setSecondHrestLevel(true);
			} else if( ComponentID.IDGenerator.wasTheLastOperationTag() ) {
				HRESTController.setSecondHrestLevel(false);
			}
		}
	}
	
	/**
	 * 
	 * @param hrestID
	 */
	public static void deleteAnnotation(String hrestID) {
		ControllerToolkit.deleteElementByID(hrestID);
		ComponentID.IDGenerator.deleteID(hrestID);
		updateHrestTagsTree(hrestID);
	}
	
	/**
	 * Enable/disable the element which can stay in the first
	 * level of the tree, in the hREST microformat. They are:
	 * 		<ul>
	 * 			<li>label</li>
	 * 			<li>method</li>
	 * 			<li>address</li>
	 * 			<li>operation</li>
	 * 		</ul>
	 * @param flag true for enable it, false otherwise.
	 */
	private static void setFirstHrestLevel(boolean flag) {
		HrestTagsTree tree = MicroWSMOeditor.getHrestTagsTree();
		tree.setFirstLevel(flag);
	}
	
	/**
	 * Enable/disable the element which can stay in the second
	 * level of the tree, in the hREST microformat. They are:
	 * 		<ul>
	 * 			<li>input</li>
	 * 			<li>output</li>
	 * 		</ul>
	 * @param flag true for enable it, false otherwise.
	 */
	private static void setSecondHrestLevel(boolean flag) {
		HrestTagsTree tree = MicroWSMOeditor.getHrestTagsTree();
		tree.setSecondLevel(flag);
	}
	
}
