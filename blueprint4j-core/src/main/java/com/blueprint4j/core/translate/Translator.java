package com.blueprint4j.core.translate;

import com.blueprint4j.core.app.ApplicationItem;

import java.io.IOException;
import java.util.List;

public interface Translator {

	public String getLanguage();

	void saveTranslationFile() throws IOException;

	public String translate(String text);

    public void translateNameAndDescription(ApplicationItem applicationItem);

    public void translateNameAndDescriptions(List<ApplicationItem> applicationItems);


}

