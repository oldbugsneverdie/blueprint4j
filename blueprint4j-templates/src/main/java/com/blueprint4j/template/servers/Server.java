package com.blueprint4j.template.servers;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.draw.Image;

import java.util.ArrayList;
import java.util.List;

public class Server extends ApplicationItem {

    List<SoftwareModule> softwareModules = new ArrayList<SoftwareModule>();

    public Server(String name) {
        super(name);
    }

    public void addSoftwareModule(SoftwareModule softwareModule) {
        softwareModules.add(softwareModule);
    }

    public List<SoftwareModule> getSoftwareModules() {
        return softwareModules;
    }


}
