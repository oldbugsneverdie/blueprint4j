package com.blueprint4j.core.app;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.doc.html.HTMLDocument;
import com.blueprint4j.core.draw.Block;
import com.blueprint4j.core.draw.DiagramHelper;
import com.blueprint4j.core.draw.DotRunner;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.draw.Line;
import com.blueprint4j.core.draw.DiagramHelper.Node;
import com.blueprint4j.core.translate.BasicTranslator;
import com.blueprint4j.core.translate.Translator;


public class RefCardDocument extends ApplicationDocument {

	private IDocumentGenerator docGenerator;
	private Logger log = Logger.getLogger("RefCardDocument");
	private String styleSheetName = "default.css";
	private RefCard refCard;
	private Translator translator;

	public RefCardDocument(RefCard refCard, File outputDirectory, String subDirectoryName, IDocumentGenerator docGenerator) {
		super(outputDirectory, subDirectoryName);
		this.refCard = refCard;
		this.docGenerator = docGenerator;
		log.info("Create RefCard in " + outputDirectory);
	}

	public String generate() throws IOException {

		String title = "RefCard-" + refCard.getName();
		String fileName = getOutputDirectory() + File.separator + title;
		log.info("Start RefCard: " + title);
		IDocument doc = docGenerator.createDocument(title, fileName);
		if (doc instanceof HTMLDocument) {
			((HTMLDocument) doc).addStyleSheet(this.styleSheetName);
		}

		addTitle(doc, 0);
		addConcepts(doc,1);
		addDrawings(doc,1);
		String tableOfContents = doc.getToc();
		log.info("Finish RefCard");
		return docGenerator.save(tableOfContents, doc, getOutputDirectory(), title);
	}

	private void addConcepts(IDocument doc, int level) {
		doc.addHeading(level, "Concepts");
		for (Concept concept: refCard.getConcepts()) {
			addConcept(concept, doc, level+1);
		}		
	}

	private void addConcept(Concept concept, IDocument doc, int level) {
		doc.addParagraph("");
		doc.addText(concept.getName() +": "+ concept.getDescription());
	}

	public void addDrawings(IDocument doc, int level){
		doc.addHeading(level, "Drawings");
		for (Drawing drawing : refCard.getDrawings()) {
			addDrawing(drawing, doc, level+1);
		}

	}

	private void addDrawing(Drawing drawing, IDocument doc, int level) {
		
		doc.addParagraph("Drawing: "+ drawing.getName());
		
		DiagramHelper diagramHelper = new DiagramHelper();
		Node rootNode = diagramHelper.createRootNode(refCard.getName());
		Node diagramNode = diagramHelper.createNode(drawing.getName(),
				rootNode);
		for (Block block : drawing.getBlocks()) {
			Node blockNode = diagramHelper.createNode(block.getName(),
					diagramNode);
		}

		for (Line line : drawing.getLines()) {
			String arrowTail = DiagramHelper.ARROW_HEAD_NONE;
			String arrowHead = DiagramHelper.ARROW_HEAD_NONE;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		doc.addImage(imageName);
	}
	
	
	@Override
	public String generate(Translator translator) throws IOException {
		this.translator = translator;
		refCard.accept(translator);
		return this.generate();
	}

	private void addTitle(IDocument doc, int level) {
		doc.addHeading(level, refCard.getName());
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
