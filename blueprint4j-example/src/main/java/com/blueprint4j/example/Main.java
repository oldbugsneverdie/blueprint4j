package com.blueprint4j.example;

import com.blueprint4j.example.entities.EntityRelationShipsBlueprints;
import com.blueprint4j.example.servers.ServerBlueprints;

/**
 *
 */
public class Main {
	public static void main(String[] args) {

		/*
		 * Create the example blueprints
		 */
		MyBlueprintDrawer myBlueprintDrawer = new MyBlueprintDrawer("Example blueprints");

   		/*
		 * Blueprint that shows a simple Entity Relationship Diagram
		 */
        EntityRelationShipsBlueprints entityRelationShipsBlueprints = new EntityRelationShipsBlueprints("Overview entities");
        myBlueprintDrawer.addBlueprint(entityRelationShipsBlueprints);

		/*
		 * Blueprint that shows information on the available servers, using custom images
		 */
		ServerBlueprints serverBlueprints = new ServerBlueprints("Server overview");
		myBlueprintDrawer.addBlueprint(serverBlueprints);


		/*
		 * Generate all blueprints
		 */
		myBlueprintDrawer.generate();

	}
}
