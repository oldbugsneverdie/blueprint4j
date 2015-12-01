package com.blueprint4j.demo;

import com.blueprint4j.template.entities.Entity;
import com.blueprint4j.template.entities.EntityRelationShip;
import com.blueprint4j.template.diagram.EntityRelationShipDiagram;

public class MyEntityRelationShipDiagram extends EntityRelationShipDiagram{

    public MyEntityRelationShipDiagram() {
        super("MyEntityRelationShipDiagram");

        Entity relation = addEntity("Relation");
        relation.setDescription("The person that orders something");
        Entity order = addEntity("Order");
        order.setDescription("A set of products that is ordered together in one transaction");
        Entity product =addEntity("Product");

        EntityRelationShip entityRelationShip1 = addEntityRelationShip(relation, "orders", product);
        EntityRelationShip entityRelationShip2 = addEntityRelationShip(order, "contains one or more", product);
        EntityRelationShip entityRelationShip3 = addEntityRelationShip(order, "is payed by", relation);
        EntityRelationShip entityRelationShip4 = addEntityRelationShip(order, "is send to", relation);

    }


}
