package com.blueprint4j.core.translate;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.blueprint4j.core.app.ApplicationItem;


/**
 * Translator that does not do any translations.
 */
public class NoTranslationTranslator implements Translator {

	private static Logger LOG = Logger.getLogger(NoTranslationTranslator.class);

	public NoTranslationTranslator() {
	}

	@Override
	public String getLanguage() {
		return "no translation";
	}

	@Override
	public void saveTranslationFile() throws IOException {
	}

	@Override
	public void translate(ApplicationItem applicationItem) throws IOException {
	}

	@Override
	public String translate(String text) throws IOException {
		return text;
	}

}
