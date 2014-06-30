package com.blueprint4j.core.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blueprint4j.core.translate.Translator;

/**
 * The place that holds all the Blueprint's
 */
public abstract class BlueprintProject {

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
	
	
}
