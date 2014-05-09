package com.blueprint4j.example.entities;

import com.blueprint4j.core.app.Blueprint;
import com.blueprint4j.core.draw.Drawing;
import com.blueprint4j.core.draw.Line;

/**
 * Everything you always wanted to know about Carriers
 */
public class EntityRelationShipsBlueprints extends Blueprint {

	private Entity customer;
	private Entity order;
	private Entity orderLine;

	public EntityRelationShipsBlueprints(String name) {
		super(name);
	}

	@Override
	protected void onCreate() {
		super.onCreate();

		createConcepts();
		addDrawing(createEntityRelationshipDiagram());

	}

	private void createConcepts() {

        customer = new Entity("Customer", this);
        customer.setDescription("A customer can place an Order");

		order = new Entity("Order", this);
		orderLine = new Entity("OrderLine",this);
	}

	private Drawing createEntityRelationshipDiagram() {

		Drawing drawing = new Drawing("Entity Relationship Diagram");
        drawing.addConcept(customer);
        drawing.addConcept(orderLine);
        drawing.addConcept(order);
        Line line1 = drawing.drawLine(customer, "orders", order);
        Line line2 = drawing.drawLine(order, "contains", orderLine);
        Line line3 = drawing.drawLine(order, "is payed by", customer);
        Line line4 = drawing.drawLine(order, "is send to", customer);
        return drawing;
	}


}
