package com.blueprint4j.core.draw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.doc.AnnotationDelegate;

/**
 * A drawing is a set of Concepts, linked by Lines
 */
public class Drawing extends ApplicationItem{

	private List<Concept> concepts = new ArrayList<Concept>();
	private List<Line> lines = new ArrayList<Line>();
    private AnnotationDelegate annotationDelegate = new AnnotationDelegate();
    private Concept mainConcept;

	public Drawing(Concept mainConcept) {
		super(mainConcept.getName());
        this.mainConcept = mainConcept;
        addConcept(mainConcept);
	}

    public void addConcept(Concept concept) {
        concepts.add(concept);
    }

    public List<Concept> getConcepts() {
		return concepts;
	}

	public Line addLine(Concept fromConcept, String name, Concept toConcept) {
		Line line = new Line(fromConcept, name, toConcept);
		lines.add(line);
		return line;
	}

	public List<Line> getLines() {
		return lines;
	}

    public String getFileName() {
        return getName()+"png";
    }

    public void save(File outputDirectory){
        DiagramHelper diagramHelper = new DiagramHelper();
        /**
         Node rootNode = diagramHelper.createRootNode(blueprint.getName());
         Node diagramNode = diagramHelper.createNode(drawing.getName(),
         rootNode);
         */
        Drawing drawing = this;
        Node diagramNode = diagramHelper.createRootNode(mainConcept);
        //for (Concept concept : drawing.getConcepts()) {

            //DiagramHelper.Node blockNode=null;
            //if (concept.getImage()==null){
            //    blockNode = diagramHelper.createNode(concept.getName(),diagramNode);
            //}else {
            //    blockNode = diagramHelper.createNode(concept.getName(),diagramNode, concept.getImage().getImageName());
            //}
            createNode(mainConcept, diagramNode, diagramHelper, true);

       //}

        for (Line line : drawing.getLines()) {
            String arrowTail = DiagramHelper.ARROW_HEAD_NORMAL;
            String arrowHead = DiagramHelper.ARROW_HEAD_NORMAL;

            String label = diagramHelper.wrapLabelOverMultipleLines(line
                    .getName());

            diagramHelper.createArrow(line.getFromConcept(), arrowTail,
                    line.getToConcept(), arrowHead, label);

        }


        String dotScript = diagramHelper.getDiagramScript();
        String imageName = drawing.getName() + ".png";
        DotRunner dotRunner;
        try {
            dotRunner = new DotRunner(dotScript, outputDirectory.getCanonicalPath().toString(), imageName);
            dotRunner.run();
        } catch (IOException e) {
            throw new RuntimeException("Error running DotRunner: " + e.getMessage(),e);
        }

    }

    private void createNode(Concept concept, Node parentNode, DiagramHelper diagramHelper, boolean isTopNode) {
        Node newNode;
        if (isTopNode){
            newNode = parentNode;
        } else {
            newNode = diagramHelper.createNode(concept, parentNode);
        }
        if (concept.hasSubConcepts()){
            for(Concept subConcept:concept.getSubConcepts()){
                createNode(subConcept,newNode, diagramHelper, false);
            }
        }
    }

}
