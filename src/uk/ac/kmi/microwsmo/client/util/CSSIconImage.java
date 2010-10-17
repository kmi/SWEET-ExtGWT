package uk.ac.kmi.microwsmo.client.util;

/**
 * 
 * @author KMi, The Open University
 */
public abstract class CSSIconImage {
	
	public static final String RDF = "rdf-icon";
	public static final String MICRO_WSMO = "microWSMO-icon";
	public static final String ONTO_CLASS = "onto-class";
	public static final String ONTO_PROPERTY = "onto-property";
	public static final String ONTO_INDIVIDUAL = "onto-individual";
	// Service Properties Term
	public static final String TERM = "sp-term";
	public static final String FOLDER_CLOSED = "folder-closed";
	public static final String FOLDER_OPENED = "folder-open";
	public static final String EXPAND = "expand";
	public static final String COLLAPS = "collaps";
	public static final String LOADING = "loading";
	public static final String SERVICE = "gears";
	public static final String ROOT = "member";
	public static final String FOLDER = "folder";
	public static final String MREF = "mref";
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static String getEntityIcon(String entity) {
		if( entity.equals("Class") ) {
			return ONTO_CLASS;
		} else if( entity.equals("Property") ) {
			return ONTO_PROPERTY;
		} else if( entity.equals("Individual") ) {
			return ONTO_INDIVIDUAL;
		} else {
			return "";
		}
	}
	
}
