package com.blueprint4j.template.diagram;

import com.blueprint4j.core.app.Concept;
import com.blueprint4j.core.doc.ApplicationDocument;
import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IDocumentGenerator;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.translate.Translator;
import com.blueprint4j.template.entities.Entity;
import com.blueprint4j.template.entities.EntityRelationShip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class to create entity relation ship diagrams
 */
public class EntityRelationShipDiagram extends ApplicationDocument{

    private List<Entity> entities = new ArrayList<Entity>();
    private List<EntityRelationShip> entityRelationShips = new ArrayList<EntityRelationShip>();
    private String drawingTitle = "Entity relationship diagram";
    private String documentTitle = "ERD";

	public EntityRelationShipDiagram(String name) {
		super(name);
	}

    public Entity addEntity(String entityName){
        Entity entity = new Entity(entityName);
        entities.add(entity);
        return entity;
    }

    public EntityRelationShip addEntityRelationShip(Entity fromEntity, String relationShipText, Entity toEntity) {
        EntityRelationShip entityRelationShip = new EntityRelationShip(fromEntity, relationShipText, toEntity);
        entityRelationShips.add(entityRelationShip);
        return entityRelationShip;
    }

    @Override
    public IDocument generate(IDocumentGenerator documentGenerator) throws IOException {

        IDocument doc = documentGenerator.createDocument(documentTitle, this.getName());

        doc.addHeading(1,documentTitle);

        for(Entity entity:entities){
            doc.addParagraph(entity.getName() + ": " + entity.getDescription());
        }

        addDrawing(doc);

        return doc;

    }

    private void addDrawing(IDocument doc) {

        Concept concept = new Concept(drawingTitle, Object.class);
        Drawing drawing = new Drawing(concept);
        for (Entity entity :entities){
            drawing.addConcept(new Concept(entity.getName(), entity.getClass()));
        }
        for (EntityRelationShip entityRelationShip :entityRelationShips){
            drawing.addLine(new Concept(entityRelationShip.getFromEntity().getName(), entityRelationShip.getClass()), entityRelationShip.getRelationShipText(), new Concept(entityRelationShip.getToEntity().getName(), entityRelationShip.getClass()));
        }
        doc.addDrawing(drawing);

    }

    @Override
    public void translate(Translator translator) {
        documentTitle = translator.translate(documentTitle);
        drawingTitle = translator.translate(drawingTitle);
        for(Entity entity:entities){
            translator.translateNameAndDescription(entity);
        }
        for(EntityRelationShip entityRelationShip:entityRelationShips){
            String translation = translator.translate(entityRelationShip.getRelationShipText());
            entityRelationShip.setRelationShipText(translation);
        }

    }


    protected List<Entity> getEntities(){
        return entities;
    }

    protected List<EntityRelationShip> getEntityRelationShips(){
        return entityRelationShips;
    }


}
