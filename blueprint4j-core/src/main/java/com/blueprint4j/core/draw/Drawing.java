package com.blueprint4j.core.draw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.translate.Translator;

public class Drawing extends ApplicationItem{

	private List<Concept> concepts = new ArrayList<Concept>();
	private List<Line> lines = new ArrayList<Line>();
	
	public Drawing(String name) {
		super(name);
	}

    public void addConcept(Concept concept) {
        Concept copyConcept = new Concept(concept);
        concepts.add(copyConcept);
    }

    public List<Concept> getConcepts() {
		return concepts;
	}

	public Line drawLine(Concept fromConcept, String name, Concept toConcept) {
		Line line = new Line(fromConcept, name, toConcept);
		lines.add(line);
		return line;
	}

	public List<Line> getLines() {
		return lines;
	}

	@Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub
		
	}



}
