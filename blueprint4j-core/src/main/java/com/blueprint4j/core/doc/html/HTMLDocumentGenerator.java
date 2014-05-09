package com.blueprint4j.core.doc.html;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;


public class HTMLDocumentGenerator implements IDocumentGenerator {

	@Override
	public HTMLDocument createDocument(String contentTitle, String fileName) {
		return new HTMLDocument(contentTitle, fileName);
	}

	@Override
	public String save(IDocument document, File outputDirectory, String fileName) throws IOException {
		File outputFile = new File(outputDirectory + File.separator + fileName);
		FileWriter writer = new FileWriter(outputFile);
		try {
			writer.write(document.getPage());
		} finally {
			writer.close();
		}
		return outputFile.toString();
	}

	@Override
	public String save(String tableOfContents, IDocument document, File outputDirectory, String fileName)
			throws IOException {
		File outputFile = new File(outputDirectory + File.separator + fileName + ".html");
		FileWriter writer = new FileWriter(outputFile);
		try {
			writer.write(tableOfContents);
			writer.write(document.getPage());
		} finally {
			writer.close();
		}
		return outputFile.toString();
	}

}
