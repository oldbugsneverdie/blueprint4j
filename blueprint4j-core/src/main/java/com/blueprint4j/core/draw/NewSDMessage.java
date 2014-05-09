package com.blueprint4j.core.draw;

import java.io.IOException;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.translate.Translator;

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

	@Override
	public void accept(Translator translator) throws IOException {
		translator.translate(this);
	}

}
