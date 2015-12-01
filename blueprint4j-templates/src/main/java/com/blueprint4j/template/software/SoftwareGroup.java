package com.blueprint4j.template.software;

import com.blueprint4j.core.app.ApplicationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of SoftwareModules
 */
public class SoftwareGroup extends ApplicationItem {

    private List<SoftwareModule> softwareModules = new ArrayList<SoftwareModule>();

    public SoftwareGroup(String name) {
        super(name);
    }

    public void addSoftwareModule(SoftwareModule softwareModule){
        softwareModules.add(softwareModule);
    }

    public List<SoftwareModule> getSoftwareModules() {
        return softwareModules;
    }

    public SoftwareModule addSoftwareModule(String softwareModuleName) {
        SoftwareModule softwareModule = new SoftwareModule(softwareModuleName);
        softwareModules.add(softwareModule);
        return softwareModule;
    }
}
