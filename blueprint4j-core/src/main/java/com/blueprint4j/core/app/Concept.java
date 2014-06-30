package com.blueprint4j.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.draw.Image;
import com.blueprint4j.core.translate.Translator;

public class Concept extends ApplicationItem {

    private Image image;
    private List<Concept> subConcepts = new ArrayList<Concept>();

	public Concept(String name) {
		super(name);
        this.image = new Image();
	}

    public Concept(String name, Image image) {
        super(name);
        this.image= image;
    }

    public Concept(Concept concept) {
        super(concept.getName());
        this.image = new Image(concept.getImage());
        this.setDescription(concept.getDescription());
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

    public List<Concept> getSubConcepts() {
        return subConcepts;
    }


    public boolean hasSubConcepts() {
        return subConcepts.size()>0;
    }
}
