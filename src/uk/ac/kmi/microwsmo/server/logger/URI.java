package uk.ac.kmi.microwsmo.server.logger;

import java.io.Serializable;

public interface URI extends Serializable {

	public String getNameSpace();

	public String getLocalName();

	public String toSPARQL();
}

