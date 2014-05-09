package com.blueprint4j.example.servers;

import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.draw.Image;

import java.util.ArrayList;
import java.util.List;

public class Server extends Concept {

    List<SoftwareModule> softwareModules = new ArrayList<SoftwareModule>();

    public Server(String name, Blueprint blueprint) {
        super(name,blueprint);
        setImage(new Image("/home/jan/Documenten/projecten/blueprint4j/blueprint4j-example/src/main/resources/images/server_100px.png"));
    }

    public void addSoftwareModule(SoftwareModule softwareModule) {
        softwareModules.add(softwareModule);
    }

    @Override
    protected List<? extends Concept> getSubConcepts() {
        return softwareModules;
    }



}
