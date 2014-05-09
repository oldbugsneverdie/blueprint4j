package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.ApplicationItem;

public class Line {

	private ApplicationItem fromApplicationItem;
	private ApplicationItem toApplicationItem;
	private String name;
	
	public Line(ApplicationItem fromApplicationItem, String name, ApplicationItem toApplicationItem) {
		this.fromApplicationItem = fromApplicationItem;
		this.toApplicationItem = toApplicationItem;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public ApplicationItem getFromApplicationItem() {
		return fromApplicationItem;
	}

	public ApplicationItem getToApplicationItem() {
		return toApplicationItem;
	}


}
