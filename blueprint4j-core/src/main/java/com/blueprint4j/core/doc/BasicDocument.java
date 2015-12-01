package com.blueprint4j.core.doc;

import com.blueprint4j.core.annotation.Diagram;
import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;
import com.blueprint4j.core.usecase.Step;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class BasicDocument extends ApplicationDocument{

    private final AnnotationDelegate annotationDelegate = new AnnotationDelegate();
    private Class documentClazz;
    private List<Drawing> allDrawings = new ArrayList<Drawing>();
    private Map<Class,Concept> classConceptMap = new HashMap<Class, Concept>();

    public BasicDocument(Class documentClazz, String name) {
        super(name);
        this.documentClazz = documentClazz;
        createDrawings();
    }

    private void createDrawings() {
        Field[] fields = documentClazz.getDeclaredFields();
        for (Field field : fields){
            if (field.isAnnotationPresent(Diagram.class));{
                // Create the main concept
                Class mainClazz = field.getType();
                String mainConceptName = annotationDelegate.getNameForDiagramAnnotatedField(field);
                Concept mainConcept = new Concept(mainConceptName, mainClazz);
                // Create the drawing
                Drawing drawing = new Drawing(mainConcept);
                drawing.setDescription(annotationDelegate.getDescriptionForField(field));
                List<Concept> concepts = getConcepts(field);
                for (Concept concept : concepts) {
                    // Add the sub concepts to the main concept
                    mainConcept.addConcept(concept);
                }
                // Add the drawing to the list of drawings in this document
                allDrawings.add(drawing);
                // Fill a map with all class - concept combinations
                fillClassConceptMap(drawing);
                // Get the use case
                Class useCaseClass = annotationDelegate.getUseCaseClassForDiagramAnnotatedField(field);
                if (useCaseClass != null){
                    List<Step> steps = annotationDelegate.getStepsFromUseCaseClass(useCaseClass);
                    for (Step step : steps){
                        Concept fromConcept = getConceptForClass(step.getFromClass());
                        if (fromConcept == null){
                            throw new RuntimeException("Could not find fromConcept for class: " + step.getFromClass());
                        }
                        Concept toConcept = getConceptForClass(step.getToClass());
                        if (toConcept == null){
                            throw new RuntimeException("Could not find toConcept for class: " + step.getFromClass());
                        }
                        drawing.addLine(fromConcept, step.getName(), toConcept);
                    }
                }
            }
        }
    }

    private void fillClassConceptMap(Drawing drawing) {
        classConceptMap.clear();
        for (Concept concept : drawing.getConcepts()){
            addClassConceptMapping(concept);
        }
    }

    private void addClassConceptMapping(Concept concept) {
        classConceptMap.put(concept.getConceptClass(), concept);
        for (Concept subConcept: concept.getSubConcepts()){
            addClassConceptMapping(subConcept);
        }
    }

    private Concept getConceptForClass(Class clazz) {
        return classConceptMap.get(clazz);
    }

    @Override
    public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {
        int level = 1;
        IDocument doc = documentGenerator.createDocument(getName(), getName() + ".html");
        for (Drawing drawing: allDrawings){
            doc.addHeading(level, drawing.getName());
            doc.addParagraph(drawing.getDescription());
            doc.addDrawing(drawing);
            describeConcepts(doc, drawing);
        }
        return doc;
    }

    private void describeConcepts(IDocument doc, Drawing drawing) {
        IList list = doc.startList("");
        List<Concept> allConcepts = getAllConceptsFromDrawing(drawing);
        sortConceptsByName(allConcepts);
        for (Concept concept: allConcepts){
            list.addItem(concept.getName() + ": " + concept.getDescription());
        }
        doc.endList(list);
    }

    private void sortConceptsByName(List<Concept> allConcepts) {
        allConcepts.sort(new Comparator<Concept>() {
            @Override
            public int compare(Concept concept1, Concept concept2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(concept1.getName(), concept2.getName());
                if (res == 0) {
                    res = concept1.getName().compareTo(concept2.getName());
                }
                return res;            }
        });
    }

    private List<Concept> getAllConceptsFromDrawing(Drawing drawing) {
        List<Concept> allConcepts = new ArrayList<Concept>();
        for (Concept concept : drawing.getConcepts()){
            allConcepts.add(concept);
            addSubConceptsToList(allConcepts, concept);
        }
        return allConcepts;
    }

    private void addSubConceptsToList(List<Concept> allConcepts, Concept concept) {
        for (Concept subConcept: concept.getSubConcepts()){
            allConcepts.add(subConcept);
            addSubConceptsToList(allConcepts, subConcept);
        }
    }

    private List<Concept> getConcepts(Field field) {
        List<Concept> concepts = new ArrayList<Concept>();
        Class clazz  = field.getType();
        Field[] subFields = clazz.getDeclaredFields();
        for (Field subField : subFields) {
            Concept subConcept = new Concept(annotationDelegate.getNameForDocAnnotatedClass(subField.getType()), subField.getType());
            subConcept.setDescription(annotationDelegate.getDescriptionForDocAnnotatedClass(subField.getType()));
            concepts.add(subConcept);
            List<Concept> subConcepts = getConcepts(subField);
            for (Concept subSubConcept: subConcepts){
                subConcept.addConcept(subSubConcept);
            }
        }
        return concepts;
    }

    @Override
    public void translate(Translator translator) {
        for (Drawing drawing: allDrawings){
            translator.translateNameAndDescription(drawing);
            translateConcepts(translator, drawing.getConcepts());
        }
    }

    private void translateConcepts(Translator translator, List<Concept> concepts) {
        for (Concept concept : concepts){
            translator.translateNameAndDescription(concept);
            translateConcepts(translator, concept.getSubConcepts());
        }

    }

    private String getNameForDocAnnotatedClass(Class mainClazz) {
        return annotationDelegate.getNameForDocAnnotatedClass(mainClazz);
    }


    private String getNameForDiagramAnnotatedField(Field field) {
        return annotationDelegate.getNameForDiagramAnnotatedField(field);
    }

    private String getNameForDiagramAnnotatedClass(Class mainClazz) {
        return annotationDelegate.getNameForDiagramAnnotatedClass(mainClazz);
    }

    private String getDescriptionForField(Field field) {
        return annotationDelegate.getDescriptionForField(field);
    }


}
