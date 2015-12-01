package com.blueprint4j.core.app;

import com.blueprint4j.core.translate.Translator;

import java.util.ArrayList;
import java.util.List;

public class ApplicationItemList {

    private List<ApplicationItem> applicationItems = new ArrayList<ApplicationItem>();

    public ApplicationItem add(ApplicationItem applicationItem){
        applicationItems.add(applicationItem);
        return applicationItem;
    }

    public List<ApplicationItem> getApplicationItems(){
        return applicationItems;
    }

    public void translate(Translator translator){
        for (ApplicationItem applicationItem: applicationItems){
            applicationItem.accept(translator);
        }
    }
}
