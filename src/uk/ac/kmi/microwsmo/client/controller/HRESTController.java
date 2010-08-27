package uk.ac.kmi.microwsmo.client.controller;

import uk.ac.kmi.microwsmo.client.MicroWSMOeditor;
import uk.ac.kmi.microwsmo.client.util.CSSIconImage;
import uk.ac.kmi.microwsmo.client.util.ComponentID;
import uk.ac.kmi.microwsmo.client.util.ComponentID.AnnotationClass;
import uk.ac.kmi.microwsmo.client.view.BaseTreeItem;
import uk.ac.kmi.microwsmo.client.view.HrestTagsTree;
import uk.ac.kmi.microwsmo.client.view.ServiceStructureTree;

/**
 * 
 * 
 * @author KMi, The Open University
 */
public final class HRESTController {
	
	/*var rangeObject = getRangeObject(userSelection);

	function getRangeObject(selectionObject) {
		if (selectionObject.getRangeAt)
			return selectionObject.getRangeAt(0);
		else { // Safari!
			var range = document.createRange();
			range.setStart(selectionObject.anchorNode,selectionObject.anchorOffset);
			range.setEnd(selectionObject.focusNode,selectionObject.focusOffset);
			return range;
		}
	}*/
	
	public static native Object getSelectionRange()/*-{
		var selection = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getStoredSelection()();
    	//ranges = [];
    	
    	//console.log(selection);
    	
		if (selection.getRangeAt){
			//for(var i = 0; i < selection.rangeCount; i++) {
			//console.log("Number: " + i + " " + selection.getRangeAt(i));
				
			//console.log("calling getRangeAt(0)");
			return selection.getRangeAt(0);
			//}
		}
		else { // Safari!
			//console.log("createRange");
			
			var range = document.createRange();
			range.setStart(selection.anchorNode,selection.anchorOffset);
			range.setEnd(selection.focusNode,selection.focusOffset);
			return range;
		}
	}-*/;
	
	public static native void logEvents(String HRESTAnn, String element) /*-{
	var httpRequest = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::getXMLHttpRequest()();
	if( httpRequest != null ) {
		var parameters = "method=HRESTAnn&item=ItemHTMLAnnotation&element=" + element;
		httpRequest.open("POST", "../logger", true);
		// set the header
		httpRequest.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		httpRequest.setRequestHeader("content-length", parameters.length);
		httpRequest.setRequestHeader("connection", "close");
		httpRequest.send(parameters);
	}
	}-*/;
	
	/**
	 * Annotate the web page with the hrest tag.
	 */
	public static native void annotate(String hrestTag) /*-{
	    try {
	    	var range = @uk.ac.kmi.microwsmo.client.controller.HRESTController::getSelectionRange()();
	    	var parentElementStart = null;
	    	var parentElementEnd = null;
	    	
	    	//console.log("range");
	    	//console.log(range);
	    	
	        try {
	            //if (typeof range.surroundContents != "undefined") {
	                var elementName = @uk.ac.kmi.microwsmo.client.util.ComponentID.HREST::getElement(Ljava/lang/String;)(hrestTag);
	                if (elementName != null) {
	                	var oldCode = @uk.ac.kmi.microwsmo.client.controller.ControllerToolkit::retrieveIframeHTML()();
	                    var element = $doc.createElement(elementName);

						 try {
						 	//Not working properly
	                    	range.surroundContents(element);
	                    	
	                    	//Logger
	                    	//console.log(element.id);
	            			@uk.ac.kmi.microwsmo.client.controller.HRESTController::logEvents(Ljava/lang/String;Ljava/lang/String;)("HRESTAnn",hrestTag);
	                     
	                     } catch (Error) {
	                     	//LoggingConsole
							//console.log(Error);
	                     	
	                     	while(range.startContainer.nodeType!=1){
					    		 parentElementStart=range.startContainer.parentNode;
					    		 range.setStart(parentElementStart, 0);
					    		 
					    		 //console.log("parentElementStart extended selection");
					    		 //console.log(parentElementStart);
					    	}
					    	
					    	while(range.endContainer.nodeType!=1){
					    		 parentElementEnd=range.endContainer.parentNode;  
					    		 range.setEnd(parentElementEnd, parentElementEnd.length);
					    		 
					    		 //console.log("parentElementEnd extended selection");
					    		 //console.log(parentElementEnd);
					    	}
	    	
	                     	var nodes = [], candidates=[], nodesAfterSelection=[], children, el, parent, touch;

							parent= range.parentElement ? range.parentElement() : range.commonAncestorContainer;
							     
						     //Logg
						     //console.log("parent of selection");
						     //console.log(parent);
							     
							     if(parent) {
							       // adjust from text node to element, if needed
							       while(parent.nodeType!=1) parent=parent.parentNode;
					
							       // obtain all candidates down to all children
							       //children=parent.all||parent.getElementsByTagName("*");
							       children = parent.childNodes;
							       
							       for(var j=0; j<children.length; j++){
							         candidates[candidates.length]=children[j];
							       }
					
									//console.log("candidates");
									//console.log(candidates);
							       //proceed - keep element when range touches it
							       nodes=[parent];
							       
							       var foundSelection = false;
		       
							       for(var ii=0, r2; ii<candidates.length; ii++) {
							         r2 = @uk.ac.kmi.microwsmo.client.controller.HRESTController::createRangeFromElement(Ljava/lang/Object;)(candidates[ii]);
							         touch = @uk.ac.kmi.microwsmo.client.controller.HRESTController::rangeContact(Ljava/lang/Object;Ljava/lang/Object;)(range, r2)
							   
							         if(r2 && touch){
							           	 
							           	 //console.log("Selected Node");
							         	 //console.log(candidates[ii]);
							         	 
							         	 foundSelection = true;
							             nodes[nodes.length]=candidates[ii];
							         }
							         else if(r2 && foundSelection){
							         	//console.log("After Selection candidates[ii]");
							           	//console.log(candidates[ii]);
							           	 
							         	nodesAfterSelection[nodesAfterSelection.length]=candidates[ii];
							         }
							       }
							     }
	      						
	      						//console.log("selectNodes");
	      						//console.log(nodes);
	      						//console.log("nodesAfterSelect");
	      						//console.log(nodesAfterSelection);
	      						
	      						for(var j=0; j<nodes.length; j++){
	      							//Log
	      							//console.log("ElementAppendNodes");
	      							//console.log(nodes[j]);
	      							
	      							//Exclude the root/parent node
	      							if(j!=0){
		         						element.appendChild(nodes[j]);
	      							}
	      						}
		         
	      						//console.log("element");
	      						//console.log(element);
	      						
	      						parent.appendChild(element);
	      						//range.selectNode(element);
	      						
	      						for(var g=0; g<nodesAfterSelection.length; g++){
		         					parent.appendChild(nodesAfterSelection[g]);
	      						}
	      						
	      					//Logger
	      					//console.log(element.id);
	                    	@uk.ac.kmi.microwsmo.client.controller.HRESTController::logEvents(Ljava/lang/String;Ljava/lang/String;)("HRESTAnn",hrestTag);
	                     
				            //@uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
							//(@uk.ac.kmi.microwsmo.client.util.Message::DIV_ELEMENT);
				        }
	                    
	                    //console.log("surroundContents");
	                    
	                    //console.log(element);
	                    //console.log(hrestTag);
	                    
	                    var isOK = @uk.ac.kmi.microwsmo.client.controller.HRESTController::parse(Ljava/lang/Object;Ljava/lang/String;)(element,hrestTag);
	                    //console.log(isOK);
	                    
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
	           // }
	        } catch (Error) {
	            @uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
				(@uk.ac.kmi.microwsmo.client.util.Message::ANNOTATION_SELECTION);
				
				//console.log(Error);
	        }
	    } catch (Error) {
	        @uk.ac.kmi.microwsmo.client.util.Message::show(Ljava/lang/String;)
				(@uk.ac.kmi.microwsmo.client.util.Message::ANNOTATION_SELECTION);
	    }
	}-*/;

	private static native boolean rangeContact(Object r1, Object r2) /*-{
	     var p=null;
	     if(r1.compareEndPoints) {
	       p={
	         method:"compareEndPoints",
	         StartToStart:"StartToStart",
	         StartToEnd:"StartToEnd",
	         EndToEnd:"EndToEnd",
	         EndToStart:"EndToStart"
	       }
	     } else if(r1.compareBoundaryPoints) {
	       p={
	         method:"compareBoundaryPoints",
	         StartToStart:0,
	         StartToEnd:1,
	         EndToEnd:2,
	         EndToStart:3
	       }
	     }
	     return p && !(
	           r2[p.method](p.StartToStart, r1)==1 &&
	           r2[p.method](p.EndToEnd, r1)==1 &&
	           r2[p.method](p.StartToEnd, r1)==1 &&
	           r2[p.method](p.EndToStart, r1)==1
	           ||
	           r2[p.method](p.StartToStart, r1)==-1 &&
	           r2[p.method](p.EndToEnd, r1)==-1 &&
	           r2[p.method](p.StartToEnd, r1)==-1 &&
	           r2[p.method](p.EndToStart, r1)==-1
	         );
	}-*/;

	private static native Object createRangeFromElement(Object el) /*-{
	     var rng=null;
	     
	     //console.log("el");
		 //console.log(el);
		         
	     if(document.body.createTextRange) {
	       rng=document.body.createTextRange();
	      
	      
	       rng.moveToElementText(el);
	       //console.log(rng);
	       
	     } else if(document.createRange) {
	       rng=document.createRange();
	       rng.selectNodeContents(el);
	       //console.log(rng);
	     }
	     return rng;
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
		
		if (childID.equals(AnnotationClass.SERVICE)){
			tree.addItem(parent, new BaseTreeItem(childID, CSSIconImage.SERVICE));
		} else{
			tree.addItem(parent, new BaseTreeItem(childID, CSSIconImage.FOLDER_CLOSED));
		}
	}
	
	public static void updateHrestTagsTree(String hrestTag) {
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
