package com.blueprint4j.template.mockup;

import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.translate.Translator;

import java.io.IOException;

public class Mockup extends ApplicationDocument{


    private String documentTitle= "Application demo";

    public Mockup(String name) {
        super(name);
    }

    @Override
    public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {
        IDocument doc = documentGenerator.createDocument(documentTitle, documentTitle);
        return doc;
    }

    @Override
    public void translate(Translator translator) {

    }
}
