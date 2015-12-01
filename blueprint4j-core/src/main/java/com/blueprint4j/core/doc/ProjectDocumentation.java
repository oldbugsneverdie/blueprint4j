package com.blueprint4j.core.doc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import com.blueprint4j.core.annotation.Document;
import com.blueprint4j.core.doc.html.HTMLDocumentGenerator;
import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.util.FileUtils;

import com.blueprint4j.core.translate.NoTranslationTranslator;
import com.blueprint4j.core.translate.Translator;
import org.reflections.Reflections;

/**
 * A {@link ProjectDocumentation} is a set of {@link ApplicationDocument}'s and a
 * set of {@link Translator}'s. By generating the documentation set, every
 * {@link ApplicationDocument} will be translated by each of the given
 * {@link Translator}'s.
 *
 */
public class ProjectDocumentation {

    private static final Logger log = Logger.getLogger(ProjectDocumentation.class.getName());
    private static final String GENERATED_SUB_DIR = "generated";
    private String name;
	private File outputDirectory;
    private File generateDirectory;
    private List<IDocumentGenerator> documentGenerators = new ArrayList<IDocumentGenerator>();
	private List<Translator> translators = new ArrayList<Translator>();
	private List<ApplicationDocument> applicationDocuments = new ArrayList<ApplicationDocument>();;
	private String fileSeparator = System.getProperty("file.separator");
	private NoTranslationTranslator noTranslationTranslator = new NoTranslationTranslator();

    public ProjectDocumentation(File outputDirectory) throws IOException {
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

    public void generate(String packageName, String languages) throws IOException, IllegalAccessException, InstantiationException {
        recreateGenerators();
        recreateTranslators(languages);
        for (Translator translator : translators) {
            recreateDocuments(packageName);
            File translationDirectory = FileUtils.createSubDirectory(generateDirectory, translator.getLanguage());
            for (ApplicationDocument applicationDocument : applicationDocuments) {
                applicationDocument.translate(translator);
                for(IDocumentGenerator documentGenerator: documentGenerators){
                    IDocument content = applicationDocument.generate(documentGenerator);
                    File documentDirectory = FileUtils.createSubDirectory(translationDirectory, applicationDocument.getName());
                    documentGenerator.save(content, documentDirectory, applicationDocument.getName());
                }
            }
            translator.saveTranslationFile();
        }
    }

    private void recreateGenerators() throws IOException {
        log.info("recreate generators");
        log.info("clear existing generators");
        documentGenerators.clear();
        log.info("add generators");
        documentGenerators.add(new HTMLDocumentGenerator());
    }

    private void recreateTranslators(String requestedLanguages) throws IOException {
        log.info("recreate translators");
        log.info("clear existing translators");
        translators.clear();
        log.info("add translators");
        String[] langaugaes = requestedLanguages.split(",");
        for(String language : langaugaes){
            Translator translator = new BasicTranslator(language);
            translators.add(translator);
        }
        translators.add(noTranslationTranslator);
        loadTranslations(translators);
    }

    private void loadTranslations(List<Translator> translators) {
        for (Translator translator:translators){
            translator.loadTranslations(outputDirectory);
        }
    }

    private void recreateDocuments(String packageName) throws InstantiationException, IllegalAccessException {
        log.info("recreate application documents");
        log.info("clear existing application documents");
        applicationDocuments.clear();
        log.info("add application documents");
        findDocuments(packageName);
    }

    private void findDocuments(String packageName) throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Document.class);
        for (Class clazz : annotated){
            BasicDocument basicDocument = new BasicDocument(clazz, clazz.getSimpleName());
            applicationDocuments.add(basicDocument);
        }
    }


}
