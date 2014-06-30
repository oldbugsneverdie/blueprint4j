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
import com.blueprint4j.core.translate.Translator;


public class DocumentPrinter extends ApplicationDocument {

	private IDocumentGenerator docGenerator;
	private Logger log = Logger.getLogger("DocumentPrinter");
	private String styleSheetName = "default.css";
	private Translator translator;
    private File outputDirectory;

	public DocumentPrinter(File outputDirectory, String subDirectoryName, IDocumentGenerator docGenerator) {
		super(subDirectoryName);
		this.docGenerator = docGenerator;
        this.outputDirectory =outputDirectory;
		log.info("Create Blueprint in " + outputDirectory);
	}

	public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {

		String title = "Blueprint-TODO"; //blueprint.getName();
		String fileName = outputDirectory.getCanonicalPath() + File.separator + title;
		log.info("Start Blueprint: " + title);
		IDocument doc = docGenerator.createDocument(title, fileName);
		if (doc instanceof HTMLDocument) {
			((HTMLDocument) doc).addStyleSheet(this.styleSheetName);
		}

		//addTitle(doc, 1);
		addDrawings(doc,1);
		String tableOfContents = doc.getToc();
		log.info("Finish Blueprint");
		return doc;
	}

    @Override
    public void translate(Translator translator) {

    }

    private void addConcept(Concept concept, IDocument doc, int level) {
		doc.addParagraph("");
		doc.addText(concept.getName() +": "+ concept.getDescription());
	}

	public void addDrawings(IDocument doc, int level){
		doc.addHeading(level, "Drawings");
//		for (Drawing drawing : blueprint.getDrawings()) {
//			addDrawing(drawing, doc, level+1);
//		}

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

            //FIXME
			dotRunner = new DotRunner(dotScript, outputDirectory.getCanonicalPath().toString(), imageName);
			dotRunner.run();
		} catch (IOException e) {
			log.error("Error running DotRunner: " + e.getMessage(),e);
		}
		
		doc.addExternalImage(imageName);

        for (Concept concept : drawing.getConcepts()) {
            addConcept(concept,doc,level);
        }


    }
	
	

	public void setStyleSheet(String styleSheetName) {
		this.styleSheetName = styleSheetName;
	}

}
