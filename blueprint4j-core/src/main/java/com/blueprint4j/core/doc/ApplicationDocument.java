package com.blueprint4j.core.doc;

import java.io.File;
import java.io.IOException;

import com.blueprint4j.core.translate.Translator;


/**
 * Base class for all application documents.
 * 
 */
public abstract class ApplicationDocument {

	private String fileSeparator = System.getProperty("file.separator");

	/**
	 * The base directory under which all documents should be generated
	 */
	private File baseDirectory;

	/**
	 * The name of the subdirectory to be created under the base directory. The
	 * idea here is that you can change the base directory of an application
	 * document, but keep the subdirectory name. This enables generators to
	 * generate a French demo in <basedirectory>/FR/Demo and a Dutch demo in
	 * <basedirectory>/NL/Demo.
	 */
	private String subDirectoryName;

	/**
	 * The directory in which the application document can be found / will be
	 * generated. This directory will be equal to the
	 * <basedirectory>/<subdirectory name>
	 */
	private File outputDirectory;

	public ApplicationDocument(File baseDirectory, String subDirectoryName) {
		this.baseDirectory = baseDirectory;
		this.subDirectoryName = subDirectoryName;
		validateDirectory();
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setBaseDirectory(File baseDirectory) {
		this.baseDirectory = baseDirectory;
		validateDirectory();
	}

	private void validateDirectory() {
		if (baseDirectory == null) {
			throw new RuntimeException("Base directory can not be null");
		}
		if (subDirectoryName == null) {
			throw new RuntimeException("Subdirectory name can not be null");
		}
		if (subDirectoryName.isEmpty()) {
			throw new RuntimeException("Subdirectory name can not be empty");
		}
		if (!baseDirectory.isDirectory()) {
			throw new RuntimeException("Base directory is not a directory: " + baseDirectory);
		}
		try {
			this.outputDirectory = createSubDirectory(baseDirectory, subDirectoryName);
		} catch (IOException e) {
			throw new RuntimeException("Could not create sub directory with name: " + subDirectoryName
					+ ", in directory: " + baseDirectory.getAbsolutePath(), e);
		}
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

	public abstract String generate() throws IOException;

	public abstract String generate(Translator translator) throws IOException;

}
