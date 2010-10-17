/**
 Copyright (c) 2009, Ontotext AD
 
 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 */

package uk.ac.kmi.microwsmo.server;

import java.io.ByteArrayInputStream;
import java.util.*;

import org.omwg.logicalexpression.LogicalExpression;
import org.omwg.ontology.*;

import uk.ac.kmi.microwsmo.client.view.model.AttributeNode;
import uk.ac.kmi.microwsmo.client.view.model.AttributeValueNode;
import uk.ac.kmi.microwsmo.client.view.model.AxiomNode;
import uk.ac.kmi.microwsmo.client.view.model.ConceptNode;
import uk.ac.kmi.microwsmo.client.view.model.InstanceNode;
import uk.ac.kmi.microwsmo.client.view.model.ModelNode;
import uk.ac.kmi.microwsmo.client.view.model.OntologyNode;
import uk.ac.kmi.microwsmo.client.view.model.RelationInstanceNode;
import uk.ac.kmi.microwsmo.client.view.model.RelationNode;
import uk.ac.kmi.microwsmo.server.model.*;
import org.wsmo.common.*;
import org.wsmo.factory.Factory;
import org.wsmo.wsml.Parser;


import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.ProfileRegistry;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

public class GuiModelBuilder {

    public static OntologyNode buildFromWSML(String data) throws Exception {
        Parser wsmoParser = Factory.createParser(null);
        TopEntity[] te = wsmoParser.parse(new StringBuffer(data));
        if (te == null 
                || te.length == 0) {
            throw new Exception("No content found");
        }
        if (false == te[0] instanceof Ontology) {
            throw new Exception("No ontology content found");
        }
        return buildModel((Ontology)te[0]);
    }
    public static OntologyNode buildFromRDF(String data) throws Exception {

    	OntModel ontoModel = ModelFactory.createOntologyModel();
        String ontoID = null;
        try {
        	ontoModel.read(new ByteArrayInputStream(data.getBytes("UTF-8")), "http://eu.soa4all.ns#");
        }
        catch(Throwable ex) {
        	throw new Exception(ex);
        }
       
        if (false == ontoModel.listClasses().hasNext()) {
        	ontoModel = ModelFactory.createOntologyModel(ProfileRegistry.RDFS_LANG);
            try {
            	ontoModel.read(new ByteArrayInputStream(data.getBytes("UTF-8")), "http://eu.soa4all.ns#");
            }
            catch(Throwable ex) {
            	throw new Exception(ex);
            }
        }
        else {
        	for(ExtendedIterator<com.hp.hpl.jena.ontology.Ontology> ontoIt = ontoModel.listOntologies(); ontoIt.hasNext();) {
        		String ontoUri = ontoIt.next().getURI();
        		if (ontoUri != null 
        				&& ontoUri.trim().length() > 0) {
        			ontoID = ontoUri;
        			break;
        		}
        	}
        }

        if (ontoID == null) {
        	ontoID = "http://eu..soa4all.ns#anonymous_" + System.currentTimeMillis();
        }

        OntologyNode ontoRoot = new OntologyNode(getLabel(ontoID), ontoID);
        for(OntClass rootCls : collectRootConcepts(ontoModel)) {
            ModelNode subModel = processOntResource(rootCls);
            if (subModel != null) {
                ontoRoot.add(subModel);
            }
        }
        return ontoRoot;
    }
    
    private static OntologyNode buildModel(Ontology onto) {
        OntologyNode ontoRoot = 
            new OntologyNode(getLabel(onto.getIdentifier().toString()), 
                             onto.getIdentifier().toString());
        
        for (Axiom ax : onto.listAxioms()) {
            ModelNode subModel = processEntity(ax);
            if (subModel != null) {
                ontoRoot.add(subModel);
            }
        }

        Set<Concept> allConcepts = onto.listConcepts();
        Set<Concept> roots = new HashSet<Concept>();
        for(Concept c : allConcepts) {
            boolean isRoot = true;
            for (Concept parent : c.listSuperConcepts()) {
                if (allConcepts.contains(parent)) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                roots.add(c);
            }
        }
        for(Concept root : roots) {
            ModelNode subModel = processEntity(root);
            if (subModel != null) {
                ontoRoot.add(subModel);
            }
        }

        for (Instance inst : onto.listInstances()) {
            boolean hasLocalConcept = false;
            for (Concept c : inst.listConcepts()) {
                if (allConcepts.contains(c)) {
                    hasLocalConcept = true;
                    break;
                }
            }
            if (hasLocalConcept == false) {
                ModelNode subModel = processEntity(inst);
                if (subModel != null) {
                    ontoRoot.add(subModel);
                }
            }
        }

        Set<Relation> allRels = onto.listRelations();
        Set<Relation> rootRels = new HashSet<Relation>();
        for(Relation r : allRels) {
            boolean isRoot = true;
            for (Relation parent : r.listSuperRelations()) {
                if (allRels.contains(parent)) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                rootRels.add(r);
            }
        }
        for(Relation root : rootRels) {
            ModelNode subModel = processEntity(root);
            if (subModel != null) {
                ontoRoot.add(subModel);
            }
        }
        
        for (RelationInstance ri : onto.listRelationInstances()) {
            if (ri.getRelation() == null 
                    || onto != ri.getRelation().getOntology()) {
                ModelNode subModel = processEntity(ri);
                if (subModel != null) {
                    ontoRoot.add(subModel);
                }
            }
        }
        return ontoRoot;
    }
    
    private static ModelNode processOntResource(OntResource resource) {
    	try {
    	if (resource instanceof OntClass) {
    		ConceptNode cNode = new ConceptNode(getLabel(resource.getURI()), resource.getURI());
    		OntClass oClass = (OntClass)resource;
    		for(ExtendedIterator<OntProperty> propIt = oClass.listDeclaredProperties(true); propIt.hasNext();) {
    			ModelNode subModel = processOntResource(propIt.next());
    			if (subModel != null) {
    				cNode.add(subModel);
    			}
    		}
    		for(OntClass subConcept : listNamedSubClasses(oClass)) {
    			ModelNode subModel = processOntResource(subConcept);
    			if (subModel != null) {
    				cNode.add(subModel);
    			}
    		}
    		for(Iterator<? extends OntResource> it = oClass.listInstances(true); it.hasNext();) {
    			ModelNode subModel = processOntResource(it.next());
    			if (subModel != null) {
    				cNode.add(subModel);
    			}
    		}
    		cNode.setTooltip("Concept:\n" + resource.getURI());
    		return cNode;
    	}
    	if (resource instanceof Individual) {
    		Individual instance = (Individual)resource;
            InstanceNode instNode = 
                new InstanceNode(getLabel(instance.getURI()), instance.getURI());
            // TODO: reveal individual property values
           /* for(Identifier attrRef : instance.listAttributeValues().keySet()){
                String attrLab = getLabel(attrRef.toString()) 
                                 + " = " 
                                 + toValueLocalIDList(wsmoInst.listAttributeValues(attrRef));
                AttributeValueNode attrValueNode = new AttributeValueNode(attrLab, attrRef.toString());
                attrValueNode.setTooltip("Attribute : \n" + attrRef.toString() 
                        + "\nValue(s) : " + toValueIDList(wsmoInst.listAttributeValues(attrRef)));
                instNode.add(attrValueNode);
            }*/
            instNode.setTooltip("Instance:\n" + instance.getURI());
            return instNode;
    	}
        if (resource instanceof OntProperty) {
        	OntProperty prop = (OntProperty)resource;
        	String label = getLabel(resource.getURI());
        	if (prop.getRange() != null 
        			&& prop.getRange().getLocalName() != null) {
        		label += " [" + prop.getRange().getLocalName() + "]";
        	}
            AttributeNode attr = 
                new AttributeNode(label, 
                		resource.getURI(),
                		prop.isDatatypeProperty());
            if (prop.getRange() != null) {
            	if (prop.getRange().getURI() != null) {
            	    attr.setRange(prop.getRange().getURI());
            	}
                attr.setTooltip("Attribute range: " + prop.getRange().getURI());
            }
            return attr;
        }
    	}
    	catch(Throwable err) {
    		System.out.println("ERROR:" + err.getMessage());
    		err.printStackTrace();
    	}
        return null;
    }
    
    private static ModelNode processEntity(Entity entity) {
        if (entity instanceof Axiom) {
            AxiomNode axNode = new AxiomNode(
                    getLabel(entity.getIdentifier().toString()), entity.getIdentifier().toString());
            String tooltip = "Axiom: " + axNode.getURI() + "\n";
            for(LogicalExpression le : ((Axiom)entity).listDefinitions()) {
                tooltip += "\n" + le.toString(((Axiom)entity).getOntology());
            }
            axNode.setTooltip(tooltip);
            return axNode;
        }
        if (entity instanceof Instance) {
            Instance wsmoInst = (Instance)entity;
            InstanceNode instNode = 
                new InstanceNode(getLabel(entity.getIdentifier().toString()), entity.getIdentifier().toString());
            for(Identifier attrRef : wsmoInst.listAttributeValues().keySet()){
                String attrLab = getLabel(attrRef.toString()) 
                                 + " = " 
                                 + toValueLocalIDList(wsmoInst.listAttributeValues(attrRef));
                AttributeValueNode attrValueNode = new AttributeValueNode(attrLab, attrRef.toString());
                attrValueNode.setTooltip("Attribute : \n" + attrRef.toString() 
                        + "\nValue(s) : " + toValueIDList(wsmoInst.listAttributeValues(attrRef)));
                instNode.add(attrValueNode);
            }
            instNode.setTooltip("Instance:\n" + entity.getIdentifier().toString());
            return instNode;
        }
        if (entity instanceof Attribute) {
            AttributeNode attr = 
                new AttributeNode(getLabel(entity.getIdentifier().toString()), 
                		entity.getIdentifier().toString(),
                		false);
            attr.setTooltip("Attribute range: " + toIDList(((Attribute)entity).listTypes()));
            return attr;
        }
        if (entity instanceof Concept) {
            ConceptNode cNode = new ConceptNode(getLabel(entity.getIdentifier().toString()), entity.getIdentifier().toString());
            for(Attribute attr : ((Concept)entity).listAttributes()) {
                ModelNode subModel = processEntity(attr);
                if (subModel != null) {
                    cNode.add(subModel);
                }
            }
            for(Concept subConcept : ((Concept)entity).listSubConcepts()) {
                ModelNode subModel = processEntity(subConcept);
                if (subModel != null) {
                    cNode.add(subModel);
                }
            }
            for(Instance inst : ((Concept) entity).getOntology().listInstances()) {
                if (inst.listConcepts().contains(entity)) {
                    ModelNode subModel = processEntity(inst);
                    if (subModel != null) {
                        cNode.add(subModel);
                    }
                }
            }
            cNode.setTooltip("Concept:\n" + entity.getIdentifier().toString());
            return cNode;
        }
        if (entity instanceof Relation) {
            RelationNode rNode = new RelationNode(getLabel(entity.getIdentifier().toString()), entity.getIdentifier().toString());
            String tooltip = "Relation:\n" + entity.getIdentifier().toString() + " / " + ((Relation)entity).listParameters().size();
            int i = 0;
            for(Parameter param : ((Relation)entity).listParameters()) {
                tooltip += "\nparam " + (i++) + " type:" + toIDList(param.listTypes());
            }
            for(Relation subRel : ((Relation)entity).listSubRelations()) {
                ModelNode subModel = processEntity(subRel);
                if (subModel != null) {
                    rNode.add(subModel);
                }
            }
            for(RelationInstance rInst : ((Relation) entity).getOntology().listRelationInstances()) {
                if (rInst.getRelation().equals(entity)) {
                    ModelNode subModel = processEntity(rInst);
                    if (subModel != null) {
                        rNode.add(subModel);
                    }
                }
            }
            rNode.setTooltip(tooltip);
            return rNode;
        }
        if (entity instanceof RelationInstance) {
            RelationInstanceNode riNode = 
                new RelationInstanceNode(
                        getLabel(entity.getIdentifier().toString())
                        + "(" + toValueLocalIDList(((RelationInstance)entity).listParameterValues()) + ")", 
                        entity.getIdentifier().toString());
            riNode.setTooltip("Relation instance: " + entity.getIdentifier().toString());
            return riNode;
        }
        return null;
    }
    
    private static Set<OntClass> collectRootConcepts(OntModel model) {
        Set<OntClass> roots = new HashSet<OntClass>();
        Set<OntClass> todos = new HashSet<OntClass>();
    	for (ExtendedIterator<OntClass> it = model.listHierarchyRootClasses(); it.hasNext();) {
        	OntClass cls = it.next();
        	if (cls.getURI() != null) {
        	    roots.add(cls);
        	}
        	else {
        		todos.add(cls);
        	}
        }
    	for(OntClass anonm : todos) {
    		for(OntClass child : listNamedSubClasses(anonm)) {
    			boolean isRoot = true;
    			for(Iterator<OntClass> it = child.listSuperClasses(true); it.hasNext();) {
    				OntClass testClass = it.next();
    				if (testClass.getURI() != null 
    						&& false == testClass.getURI().equals("http://www.w3.org/2002/07/owl#Thing")
    						&& false == testClass.getURI().equals("http://www.w3.org/2000/01/rdf-schema#Resource") ) {
    					isRoot = false;
    					break;
    				}
    			}
    			if (isRoot) {
    				roots.add(child);
    			}
    		}
    	}
    	return roots;
    }
    private static List<OntClass> listNamedSubClasses(OntClass parent) {
    	List<OntClass> subClasses = new LinkedList<OntClass>();
    	for(ExtendedIterator<OntClass> it = parent.listSubClasses(true); it.hasNext();) {
    		OntClass subClass = it.next();
    		if (subClass.getURI() != null) {
    			subClasses.add(subClass);
    		}
    		else {
    			subClasses.addAll(listNamedSubClasses(subClass));
    		}
    	}
    	return subClasses;
    }
    
    private static String toIDList(Set<Type> set) {
        if (set.size() == 0) {
            return "";
        }
        String result = "";
        for (Type e : set) {
            if (e instanceof Concept) {
                result += "\n" + ((Concept)e).getIdentifier();
            }
            else {
                result += "\n" + ((WsmlDataType)e).getIRI().toString();
            }
        }
        return result;
    }
    private static String toValueIDList(Set<Value> set) {
        if (set.size() == 0) {
            return "";
        }
        String result = "";
        for (Value e : set) {
            if (e instanceof Instance) {
                result += "\n" + ((Instance)e).getIdentifier();
            }
            else {
                result += "\n" + ((DataValue)e).getValue().toString();
            }
        }
        return result;
    }
    private static String toValueLocalIDList(Collection<Value> set) {
        if (set.size() == 0) {
            return "";
        }
        String result = "";
        for (Iterator<Value> it = set.iterator(); it.hasNext();) {
            Value e = it.next();
            if (result.length() > 0) {
                result += ", ";
            }
            if (e instanceof Instance) {
                result += getLabel(((Instance)e).getIdentifier().toString());
            }
            else {
                result += getLabel(((DataValue)e).getValue().toString());
            }
        }
        return result;
    }
    
    public static String getLabel(String all) {
        int hashPos = all.indexOf("#");
        if (hashPos != -1) {
        	if (hashPos < all.length() - 1) {
                return all.substring(hashPos + 1);
        	}
        }
        else {
        	hashPos = all.lastIndexOf("/");
        	if (hashPos < all.length() - 1) {
                return all.substring(hashPos + 1);
        	}
        }
        return all;
    }
}
