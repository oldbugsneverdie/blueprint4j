package com.blueprint4j.core.doc;

import java.util.ArrayList;
import java.util.List;

public class ApplicationDocumentList {

    private List<ApplicationDocument> applicationDocuments = new ArrayList<ApplicationDocument>();

    public ApplicationDocument add(ApplicationDocument applicationDocument){
        applicationDocuments.add(applicationDocument);
        return applicationDocument;
    }

    public List<ApplicationDocument> getApplicationDocuments(){
        return applicationDocuments;
    }
}
