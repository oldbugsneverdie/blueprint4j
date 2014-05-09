package com.blueprint4j.example.servers;

import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.draw.Image;

import java.util.List;

public class SoftwareModule extends Concept {

    public SoftwareModule(String name, Blueprint blueprint) {
        super(name, blueprint);
    }

    @Override
    protected List<Concept> getSubConcepts() {
        return null;
    }

}
