package com.blueprint4j.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.draw.Image;
import com.blueprint4j.core.translate.Translator;

public class Concept extends ApplicationItem {

    private Image image;
    private Blueprint blueprint;

	public Concept(String name, Blueprint blueprint) {
		super(name);
        this.blueprint = blueprint;
        this.image = new Image();
        blueprint.addConcept(this);
	}

    public Concept(String name, Image image, Blueprint blueprint) {
        super(name);
        this.image= image;
        this.blueprint = blueprint;
        blueprint.addConcept(this);
    }

    public Concept(Concept concept) {
        super(concept.getName());
        this.image = new Image(concept.getImage());
        this.blueprint = concept.getBlueprint();
        this.setDescription(concept.getDescription());
        blueprint.addConcept(this);
    }

    @Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub

	}

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Blueprint getBlueprint() {
        return blueprint;
    }

    protected List<? extends Concept> getSubConcepts(){
        return new ArrayList<Concept>();
    };
}
