package com.blueprint4j.core.usecase;

import com.blueprint4j.core.app.ApplicationItem;

public class Step extends ApplicationItem{

	private Class fromClass;
	private Class toClass;

	public Step(Class fromClass, String name, Class toClass) {
		super(name);
        this.fromClass = fromClass;
        this.toClass = toClass;
	}

	public Class getFromClass() {
		return fromClass;
	}

	public Class getToClass() {
		return toClass;
	}

}
