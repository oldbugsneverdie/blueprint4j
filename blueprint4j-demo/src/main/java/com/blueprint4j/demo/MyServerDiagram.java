package com.blueprint4j.demo;

import com.blueprint4j.template.servers.Environment;
import com.blueprint4j.template.servers.Server;
import com.blueprint4j.template.diagram.ServerDiagram;
import com.blueprint4j.template.software.SoftwareModule;

public class MyServerDiagram extends ServerDiagram {

    /* Environments */
    private Environment buildEnvironment;
    private Environment integrationTestEnvironment;
    private Environment acceptanceTestEnvironment;
    private Environment testEnvironment;
    private Environment productionEnvironment;

    /* Servers */
    private Server buildServer;
    private Server integrationTestServer;
    private Server acceptanceTestServer;
    private Server testServer;
    private Server productionServer;

    /* Software */
    private SoftwareModule jenkins;

    public MyServerDiagram() {
        super("MyServerDiagram");
        createEnvironments();
        createServers();
        createSoftwareModules();
    }

    private void createEnvironments() {
        buildEnvironment = addEnvironment("build environment");
        integrationTestEnvironment = addEnvironment("integration test environment");
        acceptanceTestEnvironment = addEnvironment("acceptance environment");
        testEnvironment = addEnvironment("test environment");
        productionEnvironment = addEnvironment("production environment");
    }

    private void createServers() {

        buildServer = addServer("Build server");
        buildServer.setDescription("Creates the hourly and daily builds");
        buildEnvironment.addServer(buildServer);

        integrationTestServer = addServer("Integration test server");
        integrationTestEnvironment.addServer(integrationTestServer);

        acceptanceTestServer = addServer("Acceptance test server");
        acceptanceTestEnvironment.addServer(acceptanceTestServer);

        testServer = addServer("Test server");
        testEnvironment.addServer(testServer);

        productionServer = addServer("Production server");
        productionEnvironment.addServer(productionServer);

    }

    private void createSoftwareModules() {
        jenkins = addSoftwareModule("Jenkins");
        buildServer.addSoftwareModule(jenkins);
    }



}
