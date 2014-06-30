package com.blueprint4j.core.draw;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.translate.Translator;

/**
 * A drawing is a set of Concepts, linked by Lines
 */
public class Drawing extends ApplicationItem{

	private List<Concept> concepts = new ArrayList<Concept>();
	private List<Line> lines = new ArrayList<Line>();
	
	public Drawing(String name) {
		super(name);
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
        DiagramHelper.Node diagramNode = diagramHelper.createRootNode(drawing.getName());
        for (Concept concept : drawing.getConcepts()) {

            //DiagramHelper.Node blockNode=null;
            //if (concept.getImage()==null){
            //    blockNode = diagramHelper.createNode(concept.getName(),diagramNode);
            //}else {
            //    blockNode = diagramHelper.createNode(concept.getName(),diagramNode, concept.getImage().getImageName());
            //}
            createNode(concept, diagramNode, diagramHelper);


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
            dotRunner = new DotRunner(dotScript, outputDirectory.getCanonicalPath().toString(), imageName);
            dotRunner.run();
        } catch (IOException e) {
            throw new RuntimeException("Error running DotRunner: " + e.getMessage(),e);
        }

    }

    private void createNode(Concept concept, DiagramHelper.Node parentNode, DiagramHelper diagramHelper) {
        DiagramHelper.Node newNode = diagramHelper.createNode(concept.getName(),parentNode);
        if (concept.hasSubConcepts()){
            for(Concept subConcept:concept.getSubConcepts()){
                createNode(subConcept,newNode, diagramHelper);
            }
        }
    }

}
