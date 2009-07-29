package uk.ac.kmi.microwsmo.server;

public class Entity {

	private String url;
	private String type;
	
	public Entity(String url) {
		this.url = url;
	}
	
	public String getURL() {
		return url;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return getURL();
	}
	
}
