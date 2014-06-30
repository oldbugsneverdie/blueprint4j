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

    private static final String GENERATED_SUB_DIR = "generated";
    private String name;
	private File outputDirectory;
    private File generateDirectory;
    private List<IDocumentGenerator> documentGenerators = new ArrayList<IDocumentGenerator>();
	private List<Translator> translators = new ArrayList<Translator>();
	private List<ApplicationDocument> applicationDocuments = new ArrayList<ApplicationDocument>();;
	private String fileSeparator = System.getProperty("file.separator");
	private static Logger log = Logger.getLogger(DocumentationSet.class);
	private NoTranslationTranslator noTranslationTranslator = new NoTranslationTranslator();

	public DocumentationSet(File outputDirectory) throws IOException {
		this.outputDirectory = outputDirectory;
        FileUtils.validateIsDirectory(outputDirectory);
        generateDirectory = FileUtils.createSubDirectory(outputDirectory, GENERATED_SUB_DIR);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void generate() throws IOException {

        recreateGenerators();
        recreateTranslators();
		for (Translator translator : translators) {
			recreateDocuments();
			File translationDirectory = FileUtils.createSubDirectory(generateDirectory, translator.getLanguage());
			for (ApplicationDocument applicationDocument : applicationDocuments) {
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
        translators.add(noTranslationTranslator);
        loadTranslations(translators);
    }

    private void recreateGenerators() throws IOException {
        log.info("recreate generators");
        log.info("clear existing generators");
        documentGenerators.clear();
        log.info("add generators");
        documentGenerators = getDocumentGeneratorList().getDocumentGenerators();
    }

    private void loadTranslations(List<Translator> translators) {
        for (Translator translator:translators){
            translator.loadTranslations(outputDirectory);
        }
    }


    public abstract ApplicationDocumentList getApplicationDocumentList();
	public abstract TranslatorList getTranslatorList() throws IOException;
    public abstract DocumentGeneratorList getDocumentGeneratorList();


}
