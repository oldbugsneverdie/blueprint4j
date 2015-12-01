package com.blueprint4j.core.app;

import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.draw.Image;

public class Concept extends ApplicationItem {

    private static int counter = 0;

    private Image image;
    private List<Concept> subConcepts = new ArrayList<Concept>();
    private List<ConceptProperty> conceptProperties = new ArrayList<ConceptProperty>();
    private Class clazz;
    private int id;

	public Concept(String name, Class clazz) {
		super(name);
        this.image = new Image();
        this.clazz = clazz;
        counter++;
        id = counter;
	}

    public String getTechnicalName(){
        if (hasSubConcepts()){
            return "cluster" + id;
        } else {
            return getName();
        }
    }


    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void addConcept(Concept concept) {
        subConcepts.add(concept);
    }

    public Class getConceptClass(){
        return clazz;
    }

    public List<Concept> getSubConcepts() {
        return subConcepts;
    }

    public boolean hasSubConcepts() {
        return subConcepts.size()>0;
    }

    public boolean hasConceptProperties() {
        return conceptProperties.size()>0;
    }

    public void addConceptProperty(ConceptProperty conceptProperty) {
        conceptProperties.add(conceptProperty);
    }

    public List<ConceptProperty> getConceptProperties() {
        return conceptProperties;
    }

}
