package com.blueprint4j.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.translate.NoTranslationTranslator;
import com.blueprint4j.core.translate.Translator;

/**
 * A {@link DocumentationSet} is a set of {@link ApplicationDocument}'s and a
 * set of {@link Translator}'s. By generating the documentation set, every
 * {@link ApplicationDocument} will be translated by each of the given
 * {@link Translator}'s.
 *
 */
public abstract class DocumentationSet {

	private String name;
	private File outputDirectory;
	private List<Translator> translators = new ArrayList<Translator>();
	private List<ApplicationDocument> applicationDocuments = new ArrayList<ApplicationDocument>();;
	private String fileSeparator = System.getProperty("file.separator");
	private static Logger log = Logger.getLogger(DocumentationSet.class);
	private NoTranslationTranslator noTranslationTranslator = new NoTranslationTranslator();

	public DocumentationSet(String name, File outputDirectory) throws IOException {
		this.name = name;
		this.outputDirectory = outputDirectory;
		addTranslators();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected void addTranslator(BasicTranslator translator) {
		translators.add(translator);
	}

	public List<Translator> getTranslators() {
		return translators;
	}

	protected void addApplicationDocument(ApplicationDocument applicationDocument) {
		log.info("Add application document: " + applicationDocument.getClass().toString());
		applicationDocuments.add(applicationDocument);
	}

	public File createSubDirectory(File outputDirectory, String subDirectoryName) throws IOException {
		String currentPath = outputDirectory.getCanonicalPath();
		String subDirectoryPath = currentPath + fileSeparator + subDirectoryName;
		File subDirectory = new File(subDirectoryPath);
		if (!subDirectory.exists()) {
			if (!subDirectory.mkdir()) {
				throw new IOException("Could not create directory '" + subDirectoryPath);
			}
		}
		return subDirectory;
	}

	public void generate() throws IOException {

		// Generate the application documents in the default language (no
		// translation).
		recreateDocuments();
		for (ApplicationDocument applicationDocument : applicationDocuments) {
			log.info("Generate application document (no translation) in: " + applicationDocument.getOutputDirectory().getAbsolutePath());
			applicationDocument.generate(noTranslationTranslator);
		}

		// For each additional language, recreate the application documents,
		// then translate.
		for (Translator translator : translators) {
			recreateDocuments();
			File translationDirectory = createSubDirectory(outputDirectory, translator.getLanguage());
			for (ApplicationDocument applicationDocument : applicationDocuments) {
				log.info("Generate application document (translated) in: " + translationDirectory.getAbsolutePath());
				applicationDocument.setBaseDirectory(translationDirectory);
				applicationDocument.generate(translator);
			}
			translator.saveTranslationFile();
		}
	}

	private void recreateDocuments() {
		log.info("recreate application documents");
		log.info("clear existing application documents");
		applicationDocuments.clear();
		log.info("add application documents");
		addDocuments();
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public File getOutputDirectory(String subDirectoryName) {
		try {
			return createSubDirectory(outputDirectory, subDirectoryName);
		} catch (IOException e) {
			throw new RuntimeException("Could not create sub directory with name: " + subDirectoryName
					+ ", in directory: " + outputDirectory.getAbsolutePath(), e);
		}
	}

	public abstract void addTranslators() throws IOException;

	public abstract void addDocuments();

}
