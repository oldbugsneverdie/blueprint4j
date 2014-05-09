package com.blueprint4j.core.draw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

public class DotRunner {

	private Logger log = Logger.getLogger("DotRunner");
	private String dotScript;
	private String directoryName;
	private String outputFileName;

	public DotRunner(String dotScript, String directoryName, String outputFileName) {
		this.dotScript = dotScript;
		this.directoryName = directoryName;
		this.outputFileName = outputFileName;
	}

	public void run_old() {
		try {

			// Create the .dot script
			String dotScriptName = directoryName + File.separator + outputFileName + ".dot";
			String dotOutputName = directoryName + File.separator + outputFileName;
			BufferedWriter out = new BufferedWriter(new FileWriter(dotScriptName));
			out.write(dotScript);
			out.close();

			// Run the dot to create the png file
			Runtime rt = Runtime.getRuntime();
			// Process pr = rt.exec("cmd /c dir");
			// dot hello.dot -Tpng -o hello.png
			
			//String command = "/usr/bin/dot \"" + dotScriptName + "\" -Tpng -o " + "\"" + dotOutputName + "\" -v";
			String[] command = {"/usr/bin/dot", "-Tpng",  "-o" + "\"" + dotOutputName + "\"", "\""+ dotScriptName + "\""};

			System.out.println("Executing command: " + command.toString());
			Process pr = rt.exec(command);
			int exitVal = pr.waitFor();
			if (exitVal==0){
				System.out.println("Created image " + dotOutputName);
			} else {
				System.out.println("Error creating image " + dotOutputName +". Exited with error code " + exitVal);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
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

		//TODO through property
		 ProcessBuilder pb = new ProcessBuilder("/usr/bin/dot", "-Tpng",  "-o" + dotOutputName, dotScriptName);
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
