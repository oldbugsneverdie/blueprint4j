package com.blueprint4j.template.software;

import com.blueprint4j.core.app.ApplicationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of SoftwareItems
 */
public class SoftwareModule extends ApplicationItem {

    private List<SoftwareItem> softwareItems= new ArrayList<SoftwareItem>();

    public SoftwareModule(String name) {
        super(name);
    }

    public List<SoftwareItem> getSoftwareItems() {
        return softwareItems;
    }

    public SoftwareItem addSoftwareItem(String softwareItemName) {
        SoftwareItem softwareItem = new SoftwareItem(softwareItemName);
        softwareItems.add(softwareItem);
        return softwareItem;
    }
}
