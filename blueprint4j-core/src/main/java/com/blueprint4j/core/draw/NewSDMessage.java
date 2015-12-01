package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.ApplicationItem;

/**
 * Represents a message send between two {@link ApplicationItem}'s
 *
 */
public class NewSDMessage extends ApplicationItem{

	private ApplicationItem fromApplicationItem;
	private ApplicationItem toApplicationItem;
	
	public NewSDMessage(String name) {
		super(name);
	}

	public ApplicationItem getFromApplicationItem() {
		return fromApplicationItem;
	}

	public void setFromApplicationItem(ApplicationItem fromApplicationItem) {
		this.fromApplicationItem = fromApplicationItem;
	}

	public ApplicationItem getToApplicationItem() {
		return toApplicationItem;
	}

	public void setToApplicationItem(ApplicationItem toApplicationItem) {
		this.toApplicationItem = toApplicationItem;
	}


}
