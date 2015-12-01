package com.blueprint4j.core.usecase;

import com.blueprint4j.core.app.ApplicationItem;

import java.util.ArrayList;
import java.util.List;

public class UseCase extends ApplicationItem{

    private List<Step> steps = new ArrayList<Step>();

    public UseCase(String name) {
        super(name);
    }

    public void addStep(Class fromClass, String name, Class toClass){
        Step step = new Step(fromClass, name, toClass);
        steps.add(step);
    }

    public void addStep(Step step){
        steps.add(step);
    }

    public List<Step> getSteps(){
        return steps;
    }

}
