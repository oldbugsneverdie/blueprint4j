package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.Concept;

public class Line {

	private Concept fromConcept;
	private Concept toConcept;
	private String name;
	
	public Line(Concept fromConcept, String name, Concept toConcept) {
		this.fromConcept = fromConcept;
		this.toConcept = toConcept;
		this.name = name;
        checkNotNull(fromConcept, "fromConcept can not be nuil: " + name);
        checkNotNull(toConcept, "toConcept can not be nuil: " + name);
	}

    private void checkNotNull(Concept concept, String errorMessage) {
        if (concept == null){
            throw new RuntimeException(errorMessage);
        }
    }

    public String getName(){
		return name;
	}

	public Concept getFromConcept() {
		return fromConcept;
	}

	public Concept getToConcept() {
		return toConcept;
	}


}
