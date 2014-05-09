package com.blueprint4j.core.doc;

import java.io.File;
import java.io.IOException;

public interface IDocumentGenerator {

	/**
	 * Creates a new {@link IDocument}.
	 * 
	 * @param contentTitle
	 *            title used for the content directory
	 * @return
	 */
	public IDocument createDocument(String contentTitle, String fileName);

	/**
	 * Saves the given {@link IDocument} in the given outputDirectory with the
	 * given fileName.
	 * 
	 * @param document
	 * @param outputDirectory
	 * @param fileName
	 * @return the path and filename of the saved file.
	 * @throws IOException
	 */
	public String save(IDocument document, File outputDirectory, String fileName) throws IOException,
			IllegalArgumentException;

	/**
	 * Saves the given table of contents plus the {@link IDocument} in the given
	 * outputDirectory with the given name.
	 * 
	 * @param tableOfContents
	 * @param document
	 * @param outputDirectory
	 * @param fileName
	 * @return the path and filename of the saved file.
	 * @throws IOException
	 */
	public String save(String tableOfContents, IDocument doc, File outputDirectory, String fileName) throws IOException;

}
