package com.blueprint4j.template.entities;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.translate.Translator;

/**
 * Created by jan on 29-6-14.
 */
public class EntityRelationShip extends ApplicationItem {

    private Entity fromEntity;
    private Entity toEntity;
    private String relationShipText;

    public EntityRelationShip(Entity fromEntity, String relationShipText, Entity toEntity) {
        super(fromEntity.getName() + "_" + relationShipText + "_" + toEntity.getName());
        this.fromEntity = fromEntity;
        this.toEntity = toEntity;
        this.relationShipText = relationShipText;
    }

    public String getRelationShipText() {
        return relationShipText;
    }


    public Entity getToEntity() {
        return toEntity;
    }

    public Entity getFromEntity() {
        return fromEntity;
    }

    public void setRelationShipText(String relationShipText) {
        this.relationShipText = relationShipText;
    }


}
