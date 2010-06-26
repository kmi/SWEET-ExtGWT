package uk.ac.kmi.microwsmo.server.logger;

import java.util.Date;
import uk.ac.kmi.microwsmo.server.logger.URI;

public interface LogManager {

	public void log(URI agentUri, String processId, URI actionUri, String documentUri, String object, Date time, String method);// throws LogException;

}
