package com.blueprint4j.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.translate.TranslatorList;
import com.blueprint4j.core.util.FileUtils;
import org.apache.log4j.Logger;

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
    private File baseDirectory;
	private File outputDirectory;
    private List<IDocumentGenerator> documentGenerators = new ArrayList<IDocumentGenerator>();
	private List<Translator> translators = new ArrayList<Translator>();
	private List<ApplicationDocument> applicationDocuments = new ArrayList<ApplicationDocument>();;
	private String fileSeparator = System.getProperty("file.separator");
	private static Logger log = Logger.getLogger(DocumentationSet.class);
	private NoTranslationTranslator noTranslationTranslator = new NoTranslationTranslator();

	public DocumentationSet(String name, File baseDirectory) throws IOException {
		this.name = name;
		this.baseDirectory = baseDirectory;
        FileUtils.validateIsDirectory(baseDirectory);
        this.outputDirectory = FileUtils.createSubDirectory(baseDirectory,name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void generate() throws IOException {

        recreateGenerators();
		/* Generate the application documents in the default language (no translation) */
		recreateDocuments();
		for (ApplicationDocument applicationDocument : applicationDocuments) {
			log.info("Generate application document (no translation) in: " + outputDirectory.getAbsolutePath());
            applicationDocument.translate(noTranslationTranslator);
            for(IDocumentGenerator documentGenerator: documentGenerators){
                IDocument content = applicationDocument.generate(documentGenerator);
                documentGenerator.save(content, outputDirectory, applicationDocument.getName());
            }
		}

		/* For each additional language, recreate the application documents (to erase previous translations), then translateNameAndDescription. */
        recreateTranslators();
		for (Translator translator : translators) {
			recreateDocuments();
			File translationDirectory = FileUtils.createSubDirectory(outputDirectory, translator.getLanguage());
			for (ApplicationDocument applicationDocument : applicationDocuments) {
				log.info("Generate application document (translated) in: " + translationDirectory.getAbsolutePath());
                applicationDocument.translate(translator);
                for(IDocumentGenerator documentGenerator: documentGenerators){
                    IDocument content = applicationDocument.generate(documentGenerator);
                    documentGenerator.save(content, translationDirectory, applicationDocument.getName());
                }
			}
			translator.saveTranslationFile();
		}
	}

	private void recreateDocuments() {
		log.info("recreate application documents");
		log.info("clear existing application documents");
		applicationDocuments.clear();
		log.info("add application documents");
		applicationDocuments = getApplicationDocumentList().getApplicationDocuments();
	}

    private void recreateTranslators() throws IOException {
        log.info("recreate translators");
        log.info("clear existing translators");
        translators.clear();
        log.info("add translators");
        translators = getTranslatorList().getTranslators();
    }

    private void recreateGenerators() throws IOException {
        log.info("recreate generators");
        log.info("clear existing generators");
        documentGenerators.clear();
        log.info("add generators");
        documentGenerators = getDocumentGeneratorList().getDocumentGenerators();
    }

    public abstract ApplicationDocumentList getApplicationDocumentList();
	public abstract TranslatorList getTranslatorList() throws IOException;
    public abstract DocumentGeneratorList getDocumentGeneratorList();


}
