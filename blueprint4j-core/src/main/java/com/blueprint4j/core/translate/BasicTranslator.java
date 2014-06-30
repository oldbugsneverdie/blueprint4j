package com.blueprint4j.core.translate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.blueprint4j.core.app.ApplicationItem;

public class BasicTranslator implements Translator {

	public static final String TODO = " [TODO]";
	private static final String KEY_VALUE_SEPARATOR = "###";
	private static final Object NEWLINE = System.getProperty("line.separator");
	private Map<String, String> translations = new HashMap<String, String>();
	private Map<String, String> toBeTranslated = new HashMap<String, String>();
	private String language;
	private static Logger LOG = Logger.getLogger(BasicTranslator.class);
	private String translationFileName;


	public BasicTranslator(String language, Map<String, String> translations) throws IOException {
		this.language = language;
		this.translations = translations;

		if (this.language.isEmpty()) {
			throw new RuntimeException("Language can not be empty when creating a Translator");
		}
		if (this.translations.isEmpty()) {
			throw new RuntimeException("Translations file name can not be empty when creating a Translator");
		}
	}

	public BasicTranslator(String language, String translationFileName) throws IOException {

		if (language.isEmpty()) {
			throw new RuntimeException("Language can not be empty when creating a Translator");
		}
		this.language = language;
		this.translationFileName = translationFileName;

		validate(translationFileName);
		Map<String, String> translated = loadTranslationFile(translationFileName);
		translations.putAll(translated);

	}

	@Override
	public String getLanguage() {
		return language;
	}

	/**
	 * @param text
	 *            the text to translateNameAndDescription (key in the translations map)
	 * @return the translated text
	 */
	public String getTranslation(String text) {
		if (text == null) {
			return null;
		}
		if (text.isEmpty()) {
			return text;
		}
		if (text.contains(TODO)) {
			return text;
		}
		// Check if we have a translation for this text
		if (translations.containsKey(text)) {
			String translation = translations.get(text);
			if (translation.contains(TODO)) {
				// The translation value is not yet tranlated as it contains a
				// todo.
				return text;
			} else {
				return translation;
			}
		} else {
			// If this text is already a translated text, return the text
			if (translations.containsValue(text)) {
				return text;
			} else {
				toBeTranslated.put(text, text + TODO);
				return text + TODO;
			}
		}
	}

	private Map<String, String> loadTranslationFile(String fileName) throws IOException {
		FileInputStream fstream = new FileInputStream(fileName);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		Map<String, String> result = new HashMap<String, String>();
		try {
			String strLine;
			while ((strLine = br.readLine()) != null) {
				String[] stringParts = strLine.split(KEY_VALUE_SEPARATOR);
				if (stringParts.length > 1) {
					result.put(stringParts[0], stringParts[1]);
				}
			}
		} finally {
			in.close();
		}
		return result;
	}

	@Override
	public void saveTranslationFile() throws IOException {
		translations.putAll(toBeTranslated);
		saveTranslationFile(translationFileName, translations);

	}

	public void saveTranslationFile(String fileName, Map<String, String> map) throws IOException {
		FileWriter fstream = new FileWriter(fileName);
		BufferedWriter out = new BufferedWriter(fstream);
		try {
			Iterator<Map.Entry<String, String>> it1 = map.entrySet().iterator();
			// First output the strings that are translated (not containing
			// [todo] anymore).
			while (it1.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) it1.next();
				if (!(pairs.getValue().contains(TODO))) {
					out.write(pairs.getKey() + KEY_VALUE_SEPARATOR + pairs.getValue() + NEWLINE);
				}
			}
			// Then output the strings that still need translating.
			Iterator<Map.Entry<String, String>> it2 = map.entrySet().iterator();
			while (it2.hasNext()) {
				Map.Entry<String, String> pairs = (Map.Entry<String, String>) it2.next();
				// First list the strings that are translated (not containing
				// [todo] anymore)
				if (pairs.getValue().contains(TODO)) {
					out.write(pairs.getKey() + KEY_VALUE_SEPARATOR + pairs.getValue() + NEWLINE);
				}
			}
		} finally {
			out.close();
		}
	}

	public int getNumberTranslated() {
		return translations.size();
	}

	public int getNumberUntranslated() {
		return toBeTranslated.size();
	}

	private void validate(String fileName) throws IOException {

		File currentDirectory = new File(".");
		String currentDirectoryName = currentDirectory.getCanonicalPath();

		File file = new File(fileName);
		if (!file.exists()) {
			throw new RuntimeException("File '" + file + "' does not exist in " + currentDirectoryName);
		}
		if (!file.isFile()) {
			throw new RuntimeException("File '" + file + "' has to be a file, not a directory (in "
					+ currentDirectoryName + ")");
		}

	}

    @Override
    public void translateNameAndDescriptions(List<ApplicationItem> applicationItems) {
        for (ApplicationItem applicationItem:applicationItems){
            translateNameAndDescription(applicationItem);
        }
    }

    @Override
    public void translateNameAndDescription(ApplicationItem applicationItem) {
        applicationItem.setName(translate(applicationItem.getName()));
        applicationItem.setDescription(translate(applicationItem.getDescription()));
    }


    @Override
	public String translate(String text) {
		return getTranslation(text);
	}

}
