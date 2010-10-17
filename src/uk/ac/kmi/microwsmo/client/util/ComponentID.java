package uk.ac.kmi.microwsmo.client.util;

import com.extjs.gxt.ui.client.widget.menu.MenuItem;


public abstract class ComponentID {
	
	//was entity
	public static String ENTITY = "paramm";

	public static String ANNOTATIONS_CONTEXT_MENU = "annotationsContextMenu";
	public static String ANNOTATIONS_PANEL = "annotationsPanel";
	public static String OWNONTOLOGY_PANEL = "ownOntologyPlanel";
	public static String ANNOTATIONS_TREE = "annotationsTree";
	public static String OWNONTOLOGY_TREE = "ownOntologyTree";
	public static String OWNONTOLOGY_TREE_CONTEXT_MENU = "ownOntologyContextMenu";
	public static String BUTTON_PANEL = "buttonPanel";
	public static String CENTER_SIDE_PANEL = "centerSidePanel";
	public static String EDITOR_MENU_BAR = "editorMenuBar";
	public static String EDITOR_VIEWPORT = "editorViewport";
	public static String EXPORT_BUTTON = "exportButton";
	public static String EXPORT_LOCAL_MENU = "exportLocalMenu";
	public static String EXPORT_REPOSITORY_MENU = "exportRepositoryMenu";
	public static String SERVICE_STRUCTURE_CONTEXT_MENU = "serviceStructureContextMenu";
	public static String SERVICE_STRUCTURE_CONTEXT_MENU_DELETE = "deleteServiceProperty";
	public static String SERVICE_STRUCTURE_CONTEXT_MENU_RENAME = "renameServiceProperty";
	public static String SERVICE_STRUCTURE_CONTEXT_MENU_ADDMREF = "addModelReference";
	public static String SERVICE_STRUCTURE_PANEL = "serviceStructurePanel";
	public static String SERVICE_STRUCTURE_TREE = "serviceStructureTree";
	public static String HREST_PANEL = "hrestPanel";
	public static String HREST_TAGS_PANEL = "hrestTagsPanel";
	public static String HREST_TAGS_TREE = "hrestTagsTree";
	public static String INFO_TAB_ITEM = "infoTabItem";
	public static String LEFT_SIDE_PANEL = "leftSidePanel";
	public static String LOWER_SIDE_PANEL = "lowerSidePanel";
	public static String LOWER_SIDE_TABBED_PANEL = "lowerSideTabbedPanel";
	public static String DOMAIN_ONTOLOGIES_CONTEXT_MENU = "domainOntologiesContextMenu";
	public static String DOMAIN_ONTOLOGIES_PANEL = "domainOntologiesPanel";
	public static String DOMAIN_ONTOLOGIES_TREE = "domainOntologiesTree";
	public static String QUERY_WATSON_BUTTON = "queryWatsonButton";
	public static String LOAD_ONTOLOGY_BUTTON = "loadOntologyButton";
	public static String RIGHT_SIDE_PANEL = "rightSidePanel";
	public static String SAVE_BUTTON = "saveButton";
	public static String SAVE_LOCAL_MENU = "saveLocalMenu";
	public static String SAVE_REPOSITORY_MENU = "saveRepositoryMenu";
	public static String SEMANTIC_ANNOTATION_CONTEXT_MENU = "semanticAnnotationContextMenu::";
	public static String SEMANTIC_ANNOTATION_TREE = "semanticAnnotationTree::";
	public static String SEMANTIC_PANEL = "semanticPanel";
	public static String SERVICE_PROPERTIES_CONTEXT_MENU = "servicePropertiesContextMenu";
	public static String SERVICE_PROPERTIES_PANEL = "servicePropertiesPanel";
	public static String SERVICE_PROPERTIES_TREE = "servicePropertiesTree";
	public static String WEB_PAGE_DISPLAY = "webPageDisplay";
	public static String NAVIGATOR_PANEL = "navigatorPanel";
	public static String NAVIGATOR_TEXT_FIELD = "navigatorTextField";
	
	public abstract static class HREST {
		
		/* the hREST constants */
		
		public static final String SERVICE = "service";
		public static final String LABEL = "label";
		public static final String METHOD = "method";
		public static final String ADDRESS = "address";
		public static final String OPERATION = "operation";
		public static final String INPUT = "input";
		public static final String OUTPUT = "output";
		public static final String PARAM = "paramm";
		

		/* The html element to use with the hREST tag */
		
		private static final String DIV = "div";
		private static final String SPAN = "span";
		
		/**
		 * Returns the element related to the hREST tag.
		 * 
		 * @param itemID the name of the hREST tag.
		 * @return a string which can be: "div" or "span"
		 */
		public static String getElement(String itemID) {
			if( itemID.equalsIgnoreCase(SERVICE) ) {
				return DIV;
			} else if( itemID.equalsIgnoreCase(LABEL) ) {
				return SPAN;
			} else if( itemID.equalsIgnoreCase(METHOD) ) {
				return SPAN;
			} else if( itemID.equalsIgnoreCase(ADDRESS) ) {
				return SPAN;
			} else if( itemID.equalsIgnoreCase(OPERATION) ) {
				return DIV;
			} else if( itemID.equalsIgnoreCase(INPUT) ) {
				return DIV;
			} else if( itemID.equalsIgnoreCase(OUTPUT) ) {
				return DIV;
			} else if( itemID.equalsIgnoreCase(PARAM) ) {
				return SPAN;
			} else {
				return "";
			}
		}
		
	}
	
	
	public abstract static class AnnotationClass {	
		/* the ServiceClass constants */
		public static final String SERVICE = "service";
		public static final String LABEL = "label";
		public static final String METHOD = "method";
		public static final String ADDRESS = "address";
		public static final String OPERATION = "operation";
		public static final String INPUT = "input";
		public static final String OUTPUT = "output";
		//was entity
		public static final String ENTITY = "paramm";
		public static final String PARAM = "paramm";
		public static final String TREEROOT = "SemanticAnnotation";
		public static final String MODELREFERENCE = "areference";
		public static final String SCHEMAMAPPING = "aschemamapping";
	}
	
	public static class IDGenerator {
			
			private static long serviceCount;
			private static long labelCount;
			private static long methodCount;
			private static long addressCount;
			private static long operationCount;
			private static long inputCount;
			private static long outputCount;
			//private static long entityCount;
			private static long paramCount;
			private static long unknownCount;
			
			public static void init() {
				serviceCount = 0;
				labelCount = 0;
				methodCount = 0;
				addressCount = 0;
				operationCount = 0;
				inputCount = 0;
				outputCount = 0;
				//entityCount = 0;
				paramCount = 0;
				unknownCount = 0;
			}
			
			/**
			 * Return the id related to the particular hREST tag.
			 * 
			 * @param item the hREST item id
			 * @return the new id for the hREST tag
			 */
			public static String getID(String item) {
				if( item.equals(HREST.SERVICE) ) {
					serviceCount++;
					return "service" + serviceCount;
				} else if( item.equals(HREST.LABEL) ) {
					labelCount++;
					return "label" + labelCount;
				} else if( item.equals(HREST.METHOD) ) {
					methodCount++;
					return "method" + methodCount;
				} else if( item.equals(HREST.ADDRESS) ) {
					addressCount++;
					return "address" + addressCount;
				} else if( item.equals(HREST.OPERATION) ) {
					operationCount++;
					return "operation" + operationCount;
				} else if( item.equals(HREST.INPUT) ) {
					inputCount++;
					return "input" + inputCount;
				} else if( item.equals(HREST.OUTPUT) ) {
					outputCount++;
					return "output" + outputCount;
				} else if( item.equals(ComponentID.ENTITY) ) {
					//was entityCount
					paramCount++;
					//was entity
					return "paramm" + paramCount;
				} else if( item.equals(HREST.PARAM) ) {
					paramCount++;
					return "paramm" + paramCount;
				} else {
					unknownCount++;
					return "unknown" + unknownCount;
				}		
			}
			
			/**
			 * 
			 * @param item
			 */
			public static void deleteID(String nodeType) {
				if( nodeType.equals(AnnotationClass.SERVICE) ) {
					serviceCount--;
				} else if( nodeType.equals(AnnotationClass.LABEL) ) {
					labelCount--;
				} else if( nodeType.equals(AnnotationClass.METHOD) ) {
					methodCount--;
				} else if( nodeType.equals(AnnotationClass.ADDRESS) ) {
					addressCount--;
				} else if( nodeType.equals(AnnotationClass.OPERATION) ) {
					operationCount--;
				} else if( nodeType.equals(AnnotationClass.INPUT) ) {
					inputCount--;
				} else if( nodeType.equals(AnnotationClass.OUTPUT) ) {
					outputCount--;
				} else if( nodeType.equals(AnnotationClass.ENTITY) ) {
					//was entityCount
					paramCount--;
				} else if( nodeType.equals(AnnotationClass.PARAM) ) {
					paramCount--;
				} else {
					unknownCount--;
				}		
			}
			
			public static boolean wasTheLastServiceTag() {
				return serviceCount == 0;
			}
			
			public static boolean wasTheLastOperationTag() {
				return operationCount == 0;
			}
			
			public static boolean isTheFirstServiceTag() {
				return serviceCount == 1;
			}
			
			public static boolean isTheFirstOperationTag() {
				return operationCount == 1;
			}
			
		}
	
}