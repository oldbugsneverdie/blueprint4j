package com.blueprint4j.core.app;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.blueprint4j.core.translate.Translator;

public abstract class RefCardApplication extends ApplicationItem {

	/**
	 * Detects the client's operating system.
	 */
	protected final static String osName = System.getProperty("os.name")
			.replaceAll("\\s", "");

	/**
	 * Load the refcard.properties file.
	 */
	protected final static String cfgProp = "refcard.properties";
	protected final static Properties configFile = new Properties() {
		private final static long serialVersionUID = 1L;
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
	
	
	private List<RefCard> refCards = new ArrayList<RefCard>();
	
	public RefCardApplication(String name) {
		super(name);
	}

	public void addRefCard(RefCard refCard){
		refCards.add(refCard);
	}
	

	/**
	 * Generating the RefCardApplication means calling onCreate on all RefCards, then call onLink on all RefCards.
	 * @param outputDirectory 
	 */
	public void generate() {
		for (RefCard refCard : refCards) {
			refCard.onCreate();
		}
		for (RefCard refCard : refCards) {
			refCard.onLink();
		}
		for (RefCard refCard : refCards) {
			onGenerate(refCard);
		}
	}
	
	protected abstract void onGenerate(RefCard refCard);

	@Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub

	}

}
