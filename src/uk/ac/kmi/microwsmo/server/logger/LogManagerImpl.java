package uk.ac.kmi.microwsmo.server.logger;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.ontoware.aifbcommons.collection.ClosableIterable;
import org.ontoware.rdf2go.RDF2Go;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.QueryResultTable;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.Syntax;
import org.ontoware.rdf2go.vocabulary.XSD;
import org.openrdf.rdf2go.RepositoryModel;

//import uk.ac.open.kmi.iserve.api.exceptions.LogException;
import uk.ac.kmi.microwsmo.server.logger.RDFRepositoryException;
import uk.ac.kmi.microwsmo.server.logger.RDFRepositoryConnector;
import uk.ac.kmi.microwsmo.server.logger.LogManager;
//import uk.ac.open.kmi.iserve.api.manager.QueryExecutor;
//import uk.ac.open.kmi.iserve.api.vocabulary.COMMON;
import uk.ac.kmi.microwsmo.server.logger.LOG;
//import uk.ac.open.kmi.iserve.api.vocabulary.SERVICE;
import uk.ac.kmi.microwsmo.server.logger.URI;


public class LogManagerImpl implements LogManager {

	protected RDFRepositoryConnector rdfRepositoryConnector;

	private org.ontoware.rdf2go.model.node.URI directTypeUri;

	private org.ontoware.rdf2go.model.node.URI hasAgentUri;

	private org.ontoware.rdf2go.model.node.URI hasActionUri;
	
	private org.ontoware.rdf2go.model.node.URI hasProcessUri;
	
	private org.ontoware.rdf2go.model.node.URI hasHTMLDocumentUri;

	private org.ontoware.rdf2go.model.node.URI hasDateTimeUri;

	private org.ontoware.rdf2go.model.node.URI exportedItemUri;
	private org.ontoware.rdf2go.model.node.URI HTMLAnnotationItemUri;
	private org.ontoware.rdf2go.model.node.URI savedItemUri;
	private org.ontoware.rdf2go.model.node.URI savedToRepositoryItemUri;
	private org.ontoware.rdf2go.model.node.URI searchedInWatsonItemUri;
	private org.ontoware.rdf2go.model.node.URI semanticallyAnnotatedItemUri;
	private org.ontoware.rdf2go.model.node.URI calledProxyItemUri;
	private org.ontoware.rdf2go.model.node.URI deletedHTMLAnnotationItemUri;
	private org.ontoware.rdf2go.model.node.URI deletedSemAnnotationItemUri;

	private org.ontoware.rdf2go.model.node.URI logEntryUri;
	
	private org.ontoware.rdf2go.model.node.URI timeInstantUri;

	private org.ontoware.rdf2go.model.node.URI inXSDDateTimeUri;

	public LogManagerImpl(URI serverUri, String repositoryName) throws RDFRepositoryException {
		rdfRepositoryConnector = new RDFRepositoryConnector(serverUri, repositoryName);
		RepositoryModel repoModel = rdfRepositoryConnector.openRepositoryModel();
		// set up the vocabulary
		directTypeUri = repoModel.createURI("http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "type");
		hasAgentUri = repoModel.createURI(LOG.hasAgent);
		hasProcessUri = repoModel.createURI(LOG.hasProcess);
		hasHTMLDocumentUri = repoModel.createURI(LOG.hasDocument);
		hasActionUri = repoModel.createURI(LOG.hasAction);
		hasDateTimeUri = repoModel.createURI(LOG.hasDateTime);
		
		exportedItemUri = repoModel.createURI(LOG.Item_Export);
		HTMLAnnotationItemUri = repoModel.createURI(LOG.Item_HTMLAnnotation);
		savedItemUri = repoModel.createURI(LOG.Item_Save);
		savedToRepositoryItemUri = repoModel.createURI(LOG.Item_SaveToRepository);
		searchedInWatsonItemUri = repoModel.createURI(LOG.Item_SearchInWatson);
		semanticallyAnnotatedItemUri = repoModel.createURI(LOG.Item_SemanticAnnotation);
		calledProxyItemUri = repoModel.createURI(LOG.Item_CallProxy);
		deletedHTMLAnnotationItemUri = repoModel.createURI(LOG.Item_DeleteHTMLAnnotation);
		deletedSemAnnotationItemUri = repoModel.createURI(LOG.Item_DeleteSemAnnotation);
		
		logEntryUri = repoModel.createURI(LOG.Service_Repositoy_Log_Entry);
		timeInstantUri = repoModel.createURI(LOG.Time_Instant);
		inXSDDateTimeUri = repoModel.createURI(LOG.inXSDDateTime);
		rdfRepositoryConnector.closeRepositoryModel(repoModel);
	}

	public void log(URI agentUri, String processId, URI actionUri, String documentUri, String object, Date time, String method) {
		try {
			RepositoryModel repoModel = rdfRepositoryConnector.openRepositoryModel();
			// FIXME: only logging ONCE within ONE millisecond.
			long currentTime = System.currentTimeMillis();
			org.ontoware.rdf2go.model.node.URI logEntryInst = repoModel.createURI(LOG.NS + "logEntry" + currentTime);
			org.ontoware.rdf2go.model.node.URI actionInst = repoModel.createURI(LOG.NS + "action" + currentTime);
			org.ontoware.rdf2go.model.node.URI timeInstantInst = repoModel.createURI(LOG.TIME_NS + "instant" + currentTime);
		
			repoModel.addStatement(timeInstantInst, directTypeUri, timeInstantUri);
			repoModel.addStatement(actionInst, directTypeUri, repoModel.createURI(actionUri.toString()));
			
			org.ontoware.rdf2go.model.node.URI actUri = null;
			if ( actionUri.toString().endsWith(LOG.Item_Export) ) {
				actUri = exportedItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_HTMLAnnotation) ) {
				actUri = HTMLAnnotationItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_Save) ) {
				actUri = savedItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_SaveToRepository) ) {
				actUri = savedToRepositoryItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_SearchInWatson) ) {
				actUri = searchedInWatsonItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_SemanticAnnotation) ) {
				actUri = semanticallyAnnotatedItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_CallProxy) ) {
				actUri = calledProxyItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_DeleteHTMLAnnotation) ) {
				actUri = deletedHTMLAnnotationItemUri;
			} else if ( actionUri.toString().endsWith(LOG.Item_DeleteSemAnnotation) ) {
				actUri = deletedSemAnnotationItemUri;
			}

			if ( null == actUri ) {
				//throw new LogException("Unknown Action: " + actionUri.toString());
			}

			repoModel.addStatement(actionInst, actUri, repoModel.createURI(object));
			repoModel.addStatement(logEntryInst, directTypeUri, logEntryUri);
			//repoModel.addStatement(logEntryInst, hasActionUri, actionInst);
			
			org.ontoware.rdf2go.model.node.URI processInst = repoModel.createURI(processId);
			repoModel.addStatement(logEntryInst, hasProcessUri, processInst);
			
			
			org.ontoware.rdf2go.model.node.URI documentInst = repoModel.createURI(documentUri);
			repoModel.addStatement(processInst, hasHTMLDocumentUri, documentInst);
			repoModel.addStatement(processInst, hasActionUri, actionInst);
			// FIXME 
			org.ontoware.rdf2go.model.node.URI agnetInst = repoModel.createURI(agentUri.toString());
			repoModel.addStatement(logEntryInst, hasAgentUri, agnetInst);
			DatatypeFactory datatypeFactory;
			datatypeFactory = DatatypeFactory.newInstance();
			GregorianCalendar utcCalendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
			utcCalendar.setTime(time);
			XMLGregorianCalendar xmlCalendar = datatypeFactory.newXMLGregorianCalendar(utcCalendar);
			xmlCalendar = xmlCalendar.normalize();
			repoModel.addStatement(timeInstantInst, inXSDDateTimeUri, repoModel.createDatatypeLiteral(xmlCalendar.toXMLFormat(), XSD._dateTime));
			repoModel.addStatement(logEntryInst, hasDateTimeUri, timeInstantInst);

			//if ( actUri.equals(exportedItemUri) ) {
				//repoModel.addStatement(repoModel.createURI(object), repoModel.createURI(SERVICE.creator), agnetInst);
			//}
			rdfRepositoryConnector.closeRepositoryModel(repoModel);
		} catch (RDFRepositoryException e) {
			//throw new LogException(e);
		} catch (DatatypeConfigurationException e) {
			//throw new LogException(e);
		}

	}

	public QueryResultTable executeQuery(String queryString) throws RDFRepositoryException {
		RepositoryModel repoModel = rdfRepositoryConnector.openRepositoryModel();
		QueryResultTable qrt = repoModel.sparqlSelect(queryString);
		rdfRepositoryConnector.closeRepositoryModel(repoModel);
		return qrt;
	}

	public String executeDescribeQuery(String queryString) throws RDFRepositoryException {
		RepositoryModel repoModel = rdfRepositoryConnector.openRepositoryModel();
		ClosableIterable<Statement> stmts = repoModel.sparqlDescribe(queryString);
		Model tempModel = RDF2Go.getModelFactory().createModel();
		tempModel.open();
		tempModel.addAll(stmts.iterator());
		String result = tempModel.serialize(Syntax.RdfXml);
		tempModel.close();
		rdfRepositoryConnector.closeRepositoryModel(repoModel);
		return result;
	}

	public RDFRepositoryConnector getConnector() {
		return rdfRepositoryConnector;
	}
}
