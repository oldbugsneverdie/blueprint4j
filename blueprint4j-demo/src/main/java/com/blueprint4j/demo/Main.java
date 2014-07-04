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
    public static String outputDirectory = configFile.getProperty("outputDirectory");


    public static void main(String[] args) throws IOException {

		/* Read output directory from properties, otherwise use current dir*/
        File outputDir = new File(outputDirectory);
        if (outputDir ==null && !outputDir.exists()){
            outputDir = new File(System.getProperty("user.dir"));
        }

        /* Define the project documentation */
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
