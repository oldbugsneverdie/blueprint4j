package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.ApplicationItem;

public class ClassLine {

	private Class fromClass;
	private Class toClass;
	private String name;

	public ClassLine(Class fromClass, String name, Class toClass) {
		this.fromClass = fromClass;
		this.toClass = toClass;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}

	public Class getFromClass() {
		return fromClass;
	}

	public Class getToClass() {
		return toClass;
	}


}
