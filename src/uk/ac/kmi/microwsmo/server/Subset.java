package uk.ac.kmi.microwsmo.server;

public class Subset {

	private String keyword;
	private int startPosition;
	private int size;
	private boolean firstSearch;
	
	public Subset() {
		this("");
	}
	
	public Subset(String key) {
		keyword = key;
		startPosition = 0;
		size = 0;
		firstSearch = true;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getStartPosition() {
		return startPosition;
	}
	
	public boolean isFirstSearch() {
		return firstSearch;
	}
	
	public void setFirstSearch(boolean firstSearch) {
		this.firstSearch = firstSearch;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public void setSize(String size) {
		this.size = new Integer(size);
	}
	
	public void incrementStartPositionOf(int delta) {
		startPosition += delta;
	}
	
}
