package com.blueprint4j.template.diagram;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;
import com.blueprint4j.template.software.SoftwareGroup;
import com.blueprint4j.template.software.SoftwareItem;
import com.blueprint4j.template.software.SoftwareModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Everything you always wanted to know about the context of your software
 */
public class ContextDiagram extends ApplicationDocument {

    private List<SoftwareGroup> softwareGroups = new ArrayList<SoftwareGroup>();
    private List<Arrow> arrows = new ArrayList<Arrow>();
    private String drawingTitle = "Context diagram";
    private String documentTitle = "Context diagram";

	public ContextDiagram(String name) {
		super(name);
	}

    public SoftwareGroup addSoftwareGroup(String softwareGroupName){
        SoftwareGroup softwareGroup = new SoftwareGroup(softwareGroupName);
        softwareGroups.add(softwareGroup);
        return softwareGroup;
    }

    @Override
    public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {
        IDocument doc = documentGenerator.createDocument(documentTitle, documentTitle);
        doc.addHeading(1, documentTitle);
        addDrawing(doc);
        return doc;
    }

    private void addDrawing(IDocument doc) {

//        Drawing drawing = new Drawing(drawingTitle);
//        for (SoftwareGroup softwareGroup: softwareGroups){
//            Concept softwareGroupConcept = new Concept(softwareGroup.getName());
//            for (SoftwareModule softwareModule: softwareGroup.getSoftwareModules()){
//                Concept softwareModuleConcept = new Concept(softwareModule.getName());
//                for (SoftwareItem softwareItem:softwareModule.getSoftwareItems()){
//                    Concept softwareItemConcept = new Concept(softwareItem.getName());
//                    softwareModuleConcept.addConcept(softwareItemConcept);
//                }
//                softwareGroupConcept.addConcept(softwareModuleConcept);
//            }
//            drawing.addConcept(softwareGroupConcept);
//        }
//        for (Arrow arrow : arrows){
//            drawing.addLine(new Concept(arrow.getFrom()), arrow.getText(), new Concept(arrow.getTo()));
//        }
//        doc.addDrawing(drawing);
    }

    @Override
    public void translate(Translator translator) {
        documentTitle = translator.translate(documentTitle);
        drawingTitle = translator.translate(drawingTitle);
        for(SoftwareGroup softwareGroup: softwareGroups){
            translator.translateNameAndDescription(softwareGroup);
        }
    }

    public Arrow addArrow(String from, String text, String to){
        Arrow arrow = new Arrow(from, text, to);
        arrows.add(arrow);
        return arrow;
    }

    public void addArrow(ApplicationItem fromApplicationItem, String text, ApplicationItem toApplicationItem) {
        addArrow(fromApplicationItem.getName(), text, toApplicationItem.getName());
    }


}
