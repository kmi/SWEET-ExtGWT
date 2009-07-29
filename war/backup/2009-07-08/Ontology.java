package uk.ac.kmi.microwsmo.server;

import java.util.ArrayList;


public class Ontology {

	private String url;
	private ArrayList<Entity> entities;
	
	public Ontology(String url) {
		this.url = url;
		entities = new ArrayList<Entity>();
	}
	
	public String getURL() {
		return url;
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	public Entity getEntityAt(int index) {
		return entities.get(index);
	}
	
	public int getNumberOfEntities() {
		return entities.size();
	}
	
	@Override
	public String toString() {
		String message = "Ontology URI: " + getURL() + "\n\n";
		message += "Entities list:";
		for( Entity e : entities ) {
			message += "\n" + e.getType() + " - " + e.getURL();
		}
		return message;
	}
	
}