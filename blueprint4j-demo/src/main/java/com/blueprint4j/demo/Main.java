package com.blueprint4j.demo;

import com.blueprint4j.core.doc.ProjectDocumentation;

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


    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

		/* Read output directory from properties, otherwise use current dir*/
		File outputDir = new File(System.getProperty("user.dir"));
		if (outputDirectory == null) {
			System.out.println("No 'outputDirectory' set, using default output directory: " + outputDir);
		} else {
			outputDir = new File(outputDirectory);
		}
		System.out.println("Output directory: " + outputDir);

        /* Define the project documentation */
        ProjectDocumentation projectDocumentation = new ProjectDocumentation(outputDir);
        projectDocumentation.generate("com.blueprint4j.demo", "NL,FR");

	}
}
