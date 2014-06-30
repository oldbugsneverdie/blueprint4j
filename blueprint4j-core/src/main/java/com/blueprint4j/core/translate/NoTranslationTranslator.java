package com.blueprint4j.core.translate;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
		return "Default";
	}

	@Override
	public void saveTranslationFile() throws IOException {
	}

	@Override
	public void translateNameAndDescription(ApplicationItem applicationItem){
	}

    @Override
    public void translateNameAndDescriptions(List<ApplicationItem> applicationItems) {
    }

    @Override
    public void loadTranslations(File outputDirectory) {

    }


    @Override
	public String translate(String text){
		return text;
	}

}
