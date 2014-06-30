package com.blueprint4j.demo;

import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.ApplicationDocumentList;
import com.blueprint4j.core.doc.DocumentGeneratorList;
import com.blueprint4j.core.doc.DocumentationSet;
import com.blueprint4j.core.doc.html.HTMLDocumentGenerator;
import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.translate.TranslatorList;
import com.blueprint4j.template.servers.ServerDiagram;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {

		/* Define output directory*/
        File outputDir = new File(System.getProperty("user.dir"));

        //TODO rename to Project? project = set of translatable documents
        /* Define project */
        DocumentationSet documentationSet = new DocumentationSet("demo", outputDir) {

            @Override
            public ApplicationDocumentList getApplicationDocumentList() {

                ApplicationDocumentList applicationDocumentList = new ApplicationDocumentList();
                applicationDocumentList.add(new MyEntityRelationShipDiagram());
                applicationDocumentList.add(new MyServerDiagram());
                return applicationDocumentList;

            }

            @Override
            public TranslatorList getTranslatorList() throws IOException {
                TranslatorList translatorList = new TranslatorList();
                translatorList.add(new BasicTranslator("NL", "nl.txt"));
                translatorList.add(new BasicTranslator("FR", "fr.txt"));
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


//		MyBlueprintProject myBlueprintProject = new MyBlueprintProject("Demo of Blueprint4j");
//
//		/*
//		 * Blueprint that shows information on the available servers, using custom images
//		 */
////		ServerDiagram serverDiagram = new ServerDiagram("Server overview");
////		myBlueprintProject.addBlueprint(serverDiagram);
//
//
//		/*
//		 * Generate all blueprints
//		 */
//		myBlueprintProject.generate();

	}
}
