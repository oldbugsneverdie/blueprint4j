package com.blueprint4j.core.translate;

import java.io.IOException;

public interface Translator {

	public void translate(com.blueprint4j.core.app.ApplicationItem applicationItem) throws IOException;

	public String getLanguage();

	void saveTranslationFile() throws IOException;

	public String translate(String text) throws IOException;


}

