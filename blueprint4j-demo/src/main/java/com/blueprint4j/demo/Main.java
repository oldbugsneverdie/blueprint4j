package com.blueprint4j.demo;

import com.blueprint4j.core.doc.ApplicationDocumentList;
import com.blueprint4j.core.doc.DocumentGeneratorList;
import com.blueprint4j.core.doc.DocumentationSet;
import com.blueprint4j.core.doc.html.HTMLDocumentGenerator;
import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.translate.TranslatorList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 */
public class Main {

    /**
     * Detects the client's operating system.
     */
    protected final static String osName = System.getProperty("os.name")
            .replaceAll("\\s", "");

    /**
     * Load the properties file.
     */
    protected final static String cfgProp = "blueprint4j.properties";
    protected final static Properties configFile = new Properties() {
        {
            try {
                load(new FileInputStream(cfgProp));
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    };

    /**
     * The directory where temporary files will be created.
     */
    public static String outputDirectory = configFile
            .getProperty("outputDirFor" + osName);

    /**
     * Where is your dot program located? It will be called externally.
     */
    public static String DOT = configFile.getProperty("dotFor" + osName);


    public static void main(String[] args) throws IOException {

		/* Define output directory*/
        File outputDir = new File(System.getProperty("user.dir"));
        if (outputDirectory!=null && !outputDirectory.isEmpty()){
            outputDir = new File(outputDirectory);
        }

        //TODO rename to Project? project = set of translatable documents
        /* Define project */
        DocumentationSet documentationSet = new DocumentationSet(outputDir) {

            @Override
            public ApplicationDocumentList getApplicationDocumentList() {

                ApplicationDocumentList applicationDocumentList = new ApplicationDocumentList();
                applicationDocumentList.add(new MyEntityRelationShipDiagram());
                applicationDocumentList.add(new MyServerDiagram());
                applicationDocumentList.add(new MyMockup());
                return applicationDocumentList;

            }

            @Override
            public TranslatorList getTranslatorList() throws IOException {
                TranslatorList translatorList = new TranslatorList();
                translatorList.add(new BasicTranslator("NL"));
                translatorList.add(new BasicTranslator("FR"));
                return translatorList;
            }

            @Override
            public DocumentGeneratorList getDocumentGeneratorList() {

                DocumentGeneratorList documentGeneratorList = new DocumentGeneratorList();
                documentGeneratorList.add(new HTMLDocumentGenerator());
                return documentGeneratorList;

            }
        };

        /* Generate documents */
        documentationSet.generate();

	}
}
