package com.blueprint4j.example.servers;

import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.draw.Block;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.draw.Line;

/**
 * Everything you always wanted to know about Carriers
 */
public class ServerBlueprints extends Blueprint {

	private Concept buildServer;
	private Concept integrationTestServer;
	private Concept acceptanceTestServer;
	private Concept testServer;
	private Concept productionServer;

	public ServerBlueprints(String name) {
		super(name);
	}

	@Override
	protected void onCreate() {
		super.onCreate();

		createConcepts();
		addDrawing(createBasicCarrierDrawing());

	}

	private void createConcepts() {
		buildServer = addConcept("Build server");
		integrationTestServer = addConcept("Integration test server");
		acceptanceTestServer = addConcept("Acceptance test server");
		testServer = addConcept("Test server");
		productionServer = addConcept("Production server");
	}

	private Drawing createBasicCarrierDrawing() {

		Drawing drawing = new Drawing("Current servers");
		Block block1 = drawing.drawBlock(buildServer);
		Block block2 = drawing.drawBlock(integrationTestServer);
        Block block3 = drawing.drawBlock(acceptanceTestServer);
        Block block4 = drawing.drawBlock(testServer);
        Block block5 = drawing.drawBlock(productionServer);
		return drawing;
	}


}
