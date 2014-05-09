package com.blueprint4j.core.app;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.doc.html.HTMLDocument;
import com.blueprint4j.core.draw.DiagramHelper;
import com.blueprint4j.core.draw.DotRunner;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.draw.Line;
import com.blueprint4j.core.draw.DiagramHelper.Node;
import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.translate.Translator;


public class DocumentPrinter extends ApplicationDocument {

	private IDocumentGenerator docGenerator;
	private Logger log = Logger.getLogger("DocumentPrinter");
	private String styleSheetName = "default.css";
	private Blueprint blueprint;
	private Translator translator;

	public DocumentPrinter(Blueprint blueprint, File outputDirectory, String subDirectoryName, IDocumentGenerator docGenerator) {
		super(outputDirectory, subDirectoryName);
		this.blueprint = blueprint;
		this.docGenerator = docGenerator;
		log.info("Create Blueprint in " + outputDirectory);
	}

	public String generate() throws IOException {

		String title = "Blueprint-" + blueprint.getName();
		String fileName = getOutputDirectory() + File.separator + title;
		log.info("Start Blueprint: " + title);
		IDocument doc = docGenerator.createDocument(title, fileName);
		if (doc instanceof HTMLDocument) {
			((HTMLDocument) doc).addStyleSheet(this.styleSheetName);
		}

		addTitle(doc, 1);
		addDrawings(doc,1);
		String tableOfContents = doc.getToc();
		log.info("Finish Blueprint");
		return docGenerator.save(tableOfContents, doc, getOutputDirectory(), title);
	}

	private void addConcept(Concept concept, IDocument doc, int level) {
		doc.addParagraph("");
		doc.addText(concept.getName() +": "+ concept.getDescription());
	}

	public void addDrawings(IDocument doc, int level){
		doc.addHeading(level, "Drawings");
		for (Drawing drawing : blueprint.getDrawings()) {
			addDrawing(drawing, doc, level+1);
		}

	}

	private void addDrawing(Drawing drawing, IDocument doc, int level) {

        doc.addParagraph("");
		doc.addParagraph("Drawing: "+ drawing.getName());
		
		DiagramHelper diagramHelper = new DiagramHelper();
		/**
        Node rootNode = diagramHelper.createRootNode(blueprint.getName());
		Node diagramNode = diagramHelper.createNode(drawing.getName(),
				rootNode);
        */
        Node diagramNode = diagramHelper.createRootNode(drawing.getName());
		for (Concept concept : drawing.getConcepts()) {

            Node blockNode=null;
            if (concept.getImage()==null){
    			blockNode = diagramHelper.createNode(concept.getName(),diagramNode);
            }else {
                blockNode = diagramHelper.createNode(concept.getName(),diagramNode, concept.getImage().getImageName());
            }
		}

		for (Line line : drawing.getLines()) {
			String arrowTail = DiagramHelper.ARROW_HEAD_NONE;
			String arrowHead = DiagramHelper.ARROW_HEAD_NORMAL;

			String fromEntityName = line.getFromApplicationItem().getName();
			String toEntityName = line.getToApplicationItem().getName();
			String label = diagramHelper.wrapLabelOverMultipleLines(line
					.getName());

			diagramHelper.createArrow(fromEntityName, arrowTail,
					toEntityName, arrowHead, label);

		}

		String dotScript = diagramHelper.getDiagramScript();
		String imageName = drawing.getName() + ".png";
		DotRunner dotRunner;
		try {
			dotRunner = new DotRunner(dotScript, getOutputDirectory().getCanonicalPath().toString(), imageName);
			dotRunner.run();
		} catch (IOException e) {
			log.error("Error running DotRunner: " + e.getMessage(),e);
		}
		
		doc.addImage(imageName);

        for (Concept concept : drawing.getConcepts()) {
            addConcept(concept,doc,level);
        }


    }
	
	
	@Override
	public String generate(Translator translator) throws IOException {
		this.translator = translator;
		blueprint.accept(translator);
		return this.generate();
	}

	private void addTitle(IDocument doc, int level) {
		doc.addHeading(level, blueprint.getName());
	}

	public void setStyleSheet(String styleSheetName) {
		this.styleSheetName = styleSheetName;
	}

	private String getTranslation(String text) {
		if (translator == null) {
			return text + BasicTranslator.TODO;
		} else {
			try {
				return translator.translate(text);
			} catch (IOException e) {
				log.error(e);
				throw new RuntimeException("Could not translate: " + text, e);
			}
		}
	}
}
