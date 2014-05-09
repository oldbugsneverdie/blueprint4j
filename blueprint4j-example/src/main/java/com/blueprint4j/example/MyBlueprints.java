package com.blueprint4j.example;

import java.io.File;
import java.io.IOException;

import com.blueprint4j.core.app.DocumentPrinter;
import com.blueprint4j.core.app.Drawer;
import org.apache.log4j.Logger;

import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.doc.html.HTMLDocumentGenerator;

public class MyBlueprints extends Drawer {

	private Logger log = Logger.getLogger("MyBlueprints");

	public MyBlueprints(String name) {
		super(name);

	}

	@Override
	protected void onGenerate(Blueprint blueprint) {

		String subDirectoryName = "blueprint4j";
		HTMLDocumentGenerator docGenerator = new HTMLDocumentGenerator();
		DocumentPrinter documentPrinter = new DocumentPrinter(blueprint,
				new File(outputDirectory), subDirectoryName, docGenerator);
		try {
			documentPrinter.generate();
		} catch (IOException e) {
			log.error("Error printing blueprint",e);
		}

	}

}
