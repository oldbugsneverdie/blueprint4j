package com.blueprint4j.example.servers;

import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.draw.Image;
import com.blueprint4j.core.draw.ImageType;
import com.blueprint4j.core.draw.Line;

/**
 * Everything you always wanted to know about Carriers
 */
public class ServerBlueprints extends Blueprint {

    /* Our servers */
	private Server buildServer;
	private Server integrationTestServer;
	private Server acceptanceTestServer;
	private Server testServer;
	private Server productionServer;

    private SoftwareModule jenkins;

	public ServerBlueprints(String name) {
		super(name);
	}

	@Override
	protected void onCreate() {
		super.onCreate();

		createConcepts();
        createSoftwareModules();
		addDrawing(createServerOverviewDrawing());
        addDrawing(createBuildServerDetailsDrawing());

	}

    private void createConcepts() {

        buildServer = new Server("Build server", this);
        buildServer.setDescription("Creates the hourly and daily builds");

		integrationTestServer = new Server("Integration test server", this);
		acceptanceTestServer = new Server("Acceptance test server", this);
		testServer = new Server("Test server", this);
		productionServer = new Server("Production server", this);
	}

    private void createSoftwareModules() {
        jenkins = new SoftwareModule("Jenkins", this);
    }


    private Drawing createServerOverviewDrawing() {

		Drawing drawing = new Drawing("Servers, currently in use");
        drawing.addConcept(buildServer);
        drawing.addConcept(testServer);
        drawing.addConcept(integrationTestServer);
        drawing.addConcept(acceptanceTestServer);
        drawing.addConcept(productionServer);
        Line line = drawing.drawLine(buildServer, "depends on", integrationTestServer);
        return drawing;
	}

    private Drawing createBuildServerDetailsDrawing() {
        Drawing drawing = new Drawing("Build server details");
        buildServer.setImage(new Image(ImageType.RECTANGLE));
        buildServer.addSoftwareModule(jenkins);
        drawing.addConcept(buildServer);

        return drawing;
    }



}
