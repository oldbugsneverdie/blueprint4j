package com.blueprint4j.example;

import com.blueprint4j.example.servers.ServerBlueprints;

/**
 *
 */
public class Main {
	public static void main(String[] args) {

		/*
		 * Create the example blueprints
		 */
		MyBlueprints myBlueprints = new MyBlueprints("Example blueprints");

		/*
		 * Blueprint that shows information on the available servers
		 */
		ServerBlueprints serverBlueprints = new ServerBlueprints("Current servers that are in use");
		myBlueprints.addBlueprint(serverBlueprints);

		/*
		 * Generate all blueprints
		 */
		myBlueprints.generate();

	}
}
