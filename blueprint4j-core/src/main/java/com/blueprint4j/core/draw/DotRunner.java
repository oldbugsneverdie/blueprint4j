package com.blueprint4j.core.draw;

import java.io.*;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

public class DotRunner {

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
    public static String pathToDot = configFile.getProperty("pathToDot");

    private Logger log = Logger.getLogger("DotRunner");
	private String dotScript;
	private String directoryName;
	private String outputFileName;

    public DotRunner(String dotScript, String directoryName, String outputFileName) {
		this.dotScript = dotScript;
		this.directoryName = directoryName;
		this.outputFileName = outputFileName;
	}

	public void run(){
		
		// Create the .dot script
		String dotScriptName = directoryName + File.separator + outputFileName + ".dot";
		String dotOutputName = directoryName + File.separator + outputFileName;
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(dotScriptName));
			out.write(dotScript);
		} catch (Exception e) {
			log.error(e.toString(),e);
		} finally{
			try {
				out.close();
			} catch (IOException e) {
				log.error(e.toString(),e);
			}
		}
	
		//Check it does exist
		File dotFile = new File(dotScriptName);
		if (!dotFile.exists()){
			throw new RuntimeException("Can not create image. Dot file does not exist: "+ dotScriptName);
		}
		log.info("Create image for: " + dotScriptName);

//		 ProcessBuilder pb = new ProcessBuilder(pathToDot, "-Tpng", "-Kfdp",  "-o" + dotOutputName, dotScriptName);
         ProcessBuilder pb = new ProcessBuilder(pathToDot, "-Tpng",  "-o" + dotOutputName, dotScriptName);
		 Map<String, String> env = pb.environment();
		 env.put("VAR1", "myValue");
		 try {
			Process p = pb.start();
			System.out.println("Created image " + dotOutputName);
		} catch (IOException e) {
			System.out.println("Error creating image " + dotOutputName +". Exited with error: " + e.getMessage());
		}
		
	}


}
