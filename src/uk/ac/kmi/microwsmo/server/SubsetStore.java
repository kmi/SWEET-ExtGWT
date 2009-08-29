package uk.ac.kmi.microwsmo.server;

import java.util.LinkedList;
import java.util.List;

public class SubsetStore {

	private List<Subset> subsetList;
	
	public SubsetStore() {
		subsetList = new LinkedList<Subset>();
	}
	
	public boolean contains(String keyword) {
		for( Subset s : subsetList ) {
			if( s.getKeyword().equals(keyword) ) {
				return true;
			}
		}
		return false;
	}
	
	public Subset getSubset(String keyword) {
		for( Subset s : subsetList ) {
			if( s.getKeyword().equals(keyword) ) {
				return s;
			}
		}
		Subset subset = new Subset(keyword);
		subsetList.add(subset);
		return subset;
	}
	
}
