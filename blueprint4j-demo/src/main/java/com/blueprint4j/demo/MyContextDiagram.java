package com.blueprint4j.demo;

import com.blueprint4j.template.diagram.ContextDiagram;
import com.blueprint4j.template.software.SoftwareGroup;
import com.blueprint4j.template.software.SoftwareItem;
import com.blueprint4j.template.software.SoftwareModule;

public class MyContextDiagram extends ContextDiagram {

    /* Software Groups */
    private SoftwareGroup external;
    private SoftwareGroup internal;

    /* Software modules */
    private SoftwareModule email;
    private SoftwareModule frontend;
    private SoftwareModule backend;

    /* Software items */
    private SoftwareItem itemA;
    private SoftwareItem itemB;
    private SoftwareItem itemC;

    public MyContextDiagram() {
        super("MyContextDiagram");
        createSoftwareGroups();
        createSoftwareModules();
        createSoftwareItems();
        createArrows();
    }

    private void createSoftwareGroups() {
        external = addSoftwareGroup("External");
        internal = addSoftwareGroup("Internal");
    }

    private void createSoftwareModules() {
        email = external.addSoftwareModule("Email");

        frontend = internal.addSoftwareModule("Frontend");
        backend = internal.addSoftwareModule("Backend");
    }

    private void createSoftwareItems() {
    }

    private void createArrows() {
    }



}
