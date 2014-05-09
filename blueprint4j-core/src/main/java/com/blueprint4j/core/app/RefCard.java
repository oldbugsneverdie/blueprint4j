package com.blueprint4j.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;

public class RefCard extends ApplicationItem {

	List<Drawing> drawings = new ArrayList<Drawing>();
	List<Concept> concepts = new ArrayList<Concept>();
	
	public RefCard(String name) {
		super(name);
	}
	
	protected void addDrawing(Drawing drawing) {
		drawings.add(drawing);
	}

	public List<Drawing> getDrawings() {
		return drawings;
	}

	protected Concept addConcept(String name) {
		Concept concept = new Concept(name);
		concepts.add(concept);
		return concept;
	}

	public List<Concept> getConcepts() {
		return concepts;
	}


	@Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub

	}

}
