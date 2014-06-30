package com.blueprint4j.core.doc;

import java.util.ArrayList;
import java.util.List;

public class DocumentGeneratorList {

    private List<IDocumentGenerator> documentGenerators = new ArrayList<IDocumentGenerator>();

    public IDocumentGenerator add(IDocumentGenerator documentGenerator){
        documentGenerators.add(documentGenerator);
        return documentGenerator;
    }

    public List<IDocumentGenerator> getDocumentGenerators(){
        return documentGenerators;
    }
}
