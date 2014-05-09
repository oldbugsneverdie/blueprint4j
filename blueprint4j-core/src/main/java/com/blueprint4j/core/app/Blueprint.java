package com.blueprint4j.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;

public class Blueprint extends ApplicationItem {

    List<Concept> concepts = new ArrayList<Concept>();
	List<Drawing> drawings = new ArrayList<Drawing>();

	public Blueprint(String name) {
		super(name);
	}

    public void addConcept(Concept concept) {
        concepts.add(concept);
    }

    protected void addDrawing(Drawing drawing) {
		drawings.add(drawing);
	}

	public List<Drawing> getDrawings() {
		return drawings;
	}

	public List<Concept> getConcepts() {
		return concepts;
	}


	@Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub

	}

}
