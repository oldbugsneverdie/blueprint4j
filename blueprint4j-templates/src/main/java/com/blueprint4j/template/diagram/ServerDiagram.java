package com.blueprint4j.template.diagram;

import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;
import com.blueprint4j.template.servers.Environment;
import com.blueprint4j.template.servers.Server;
import com.blueprint4j.template.software.SoftwareModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Everything you always wanted to know about the build environment
 */
public class ServerDiagram extends ApplicationDocument {


    private List<Environment> environments= new ArrayList<Environment>();
    private List<Server> servers = new ArrayList<Server>();
    private List<SoftwareModule> softwareModules= new ArrayList<SoftwareModule>();
    private String drawingTitle = "Current servers per environment";
    private String documentTitle = "Server overview";

	public ServerDiagram(String name) {
		super(name);
	}

    public Environment addEnvironment(String environmentName){
        Environment environment= new Environment(environmentName);
        environments.add(environment);
        return environment;
    }

    public Server addServer(String serverName){
        Server server = new Server(serverName);
        servers.add(server);
        return server;
    }

    public SoftwareModule addSoftwareModule(String softwareModuleName){
        SoftwareModule softwareModule = new SoftwareModule(softwareModuleName);
        softwareModules.add(softwareModule);
        return softwareModule;
    }

    @Override
    public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {
        IDocument doc = documentGenerator.createDocument("Server Document", "Server Document");
        doc.addHeading(1, documentTitle);
        addDrawing(doc);
        return doc;
    }

    private void addDrawing(IDocument doc) {

//        Drawing drawing = new Drawing(drawingTitle);
//        for (Environment environment: environments){
//            Concept environmentConcept = new Concept(environment.getName());
//            for (Server server: environment.getServers()){
//                Concept serverConcept = new Concept(server.getName());
//                for (SoftwareModule softwareModule:server.getSoftwareModules()){
//                    Concept softwareModuleConcept = new Concept(softwareModule.getName());
//                    serverConcept.addConcept(softwareModuleConcept);
//                }
//                environmentConcept.addConcept(serverConcept);
//            }
//            drawing.addConcept(environmentConcept);
//        }
//        doc.addDrawing(drawing);
    }

    @Override
    public void translate(Translator translator) {
        documentTitle = translator.translate(documentTitle);
        drawingTitle = translator.translate(drawingTitle);
        for(Server server:servers){
            translator.translateNameAndDescription(server);
        }

    }

}
