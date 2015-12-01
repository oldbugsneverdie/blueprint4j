package com.blueprint4j.core.doc;

import java.io.IOException;

import com.blueprint4j.core.translate.Translator;


/**
 * Base class for all application documents.
 * Application documents support the ability to translateNameAndDescription themselves and generate their content using an IDocumentGenerator.
 * 
 */
public abstract class ApplicationDocument{

    private String name;

    public ApplicationDocument(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract IDocument generate(IDocumentGenerator documentGenerator) throws IOException;

    public abstract void translate(Translator translator);
}
