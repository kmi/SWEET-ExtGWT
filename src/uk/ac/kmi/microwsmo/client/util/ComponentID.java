package uk.ac.kmi.microwsmo.client.util;


public abstract class ComponentID {
	
	public static String ENTITY = "entity";

	public static String ANNOTATIONS_CONTEXT_MENU = "annotationsContextMenu";
	public static String ANNOTATIONS_PANEL = "annotationsPanel";
	public static String ANNOTATIONS_TREE = "annotationsTree";
	public static String BUTTON_PANEL = "buttonPanel";
	public static String CENTER_SIDE_PANEL = "centerSidePanel";
	public static String EDITOR_MENU_BAR = "editorMenuBar";
	public static String EDITOR_VIEWPORT = "editorViewport";
	public static String EXPORT_BUTTON = "exportButton";
	public static String SERVICE_STRUCTURE_CONTEXT_MENU = "serviceStructureContextMenu";
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
	public static String RIGHT_SIDE_PANEL = "rightSidePanel";
	public static String SAVE_BUTTON = "saveButton";
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
			} else {
				return "";
			}
		}
		
	}
	
	public static class IDGenerator {
			
			private static long serviceCount;
			private static long labelCount;
			private static long methodCount;
			private static long addressCount;
			private static long operationCount;
			private static long inputCount;
			private static long outputCount;
			private static long entityCount;
			private static long unknownCount;
			
			public static void init() {
				serviceCount = 0;
				labelCount = 0;
				methodCount = 0;
				addressCount = 0;
				operationCount = 0;
				inputCount = 0;
				outputCount = 0;
				entityCount = 0;
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
					entityCount++;
					return "entity" + entityCount;
				} else {
					unknownCount++;
					return "unknown" + unknownCount;
				}		
			}
			
			/**
			 * 
			 * @param item
			 */
			public static void deleteID(String item) {
				if( item.startsWith(HREST.SERVICE) ) {
					serviceCount--;
				} else if( item.equals(HREST.LABEL) ) {
					labelCount--;
				} else if( item.startsWith(HREST.METHOD) ) {
					methodCount--;
				} else if( item.startsWith(HREST.ADDRESS) ) {
					addressCount--;
				} else if( item.startsWith(HREST.OPERATION) ) {
					operationCount--;
				} else if( item.startsWith(HREST.INPUT) ) {
					inputCount--;
				} else if( item.startsWith(HREST.OUTPUT) ) {
					outputCount--;
				} else if( item.startsWith(ComponentID.ENTITY) ) {
					entityCount--;
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