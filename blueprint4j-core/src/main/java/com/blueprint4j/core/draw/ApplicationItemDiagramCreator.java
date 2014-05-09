package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.ApplicationItem;


public class ApplicationItemDiagramCreator extends BasicDiagramCreator {


	public ApplicationItemDiagramCreator(String diagramName) {
		super(diagramName);
	}

	public SubGraph createSubGraph(ApplicationItem applicationItem) {
		return super.createSubGraph(applicationItem.getName(), applicationItem.getId());
	}
	
	protected void createSequenceDiagramArrows(NewSequenceDiagram sequenceDiagram) {
		int number = 1;
		for (NewSDMessage sdMessage : sequenceDiagram.getMessages()) {
			
			ApplicationItem fromApplicationItem = sdMessage.getFromApplicationItem();
			ApplicationItem toApplicationItem = sdMessage.getToApplicationItem();
			
			createArrow(fromApplicationItem, toApplicationItem, number, sdMessage.getName());
			number++;
						
		}
	}

	private void createArrow(ApplicationItem fromApplicationItem,ApplicationItem toApplicationItem, int number, String name) {

		if (fromApplicationItem==null){
			throw new RuntimeException("Can not draw sequence diagram arrow. FromApplicationItem is null");
		}

		if (toApplicationItem==null){
			throw new RuntimeException("Can not draw sequence diagram arrow. ToApplicationItem is null");
		}

		boolean fromApplicationItemIsCluster = isApplicationItemCluster(fromApplicationItem);
		boolean toApplicationItemIsCluster = isApplicationItemCluster(toApplicationItem);

		if (fromApplicationItemIsCluster && toApplicationItemIsCluster){
			createClusterClusterArrow(fromApplicationItem, toApplicationItem, number, name);
			return;
		}
		if (fromApplicationItemIsCluster && !toApplicationItemIsCluster){
			createClusterItemArrow(fromApplicationItem, toApplicationItem, number, name);
			return;
		}
		if (!fromApplicationItemIsCluster && toApplicationItemIsCluster){
			createItemClusterArrow(fromApplicationItem, toApplicationItem, number, name);
			return;
		}
		if (!fromApplicationItemIsCluster && !toApplicationItemIsCluster){
			createItemItemArrow(fromApplicationItem, toApplicationItem, number, name);
			return;
		}
		
		throw new RuntimeException("Could not create arrow from " + fromApplicationItem.getName() + " to " + toApplicationItem.getName());
		
	}

	/**
	 * Returns true if this application item is a cluster
	 */
	private boolean isApplicationItemCluster(ApplicationItem fromApplicationItem) {
		return fromApplicationItem.hasSubApplicationItems();
	}
	private void createItemItemArrow(ApplicationItem fromApplicationItem,
			ApplicationItem toApplicationItem, int number, String name) {
		createArrow(fromApplicationItem.getName(), toApplicationItem.getName(), number, name);
	}

	private void createItemClusterArrow(ApplicationItem fromApplicationItem,
			ApplicationItem toApplicationItem, int number, String name) {
		createArrowBetweenClusters(fromApplicationItem.getName(), toApplicationItem.getName(), number, name, null,toApplicationItem.getId());
	}

	private void createClusterItemArrow(ApplicationItem fromApplicationItem,
			ApplicationItem toApplicationItem, int number, String name) {
		createArrowBetweenClusters(fromApplicationItem.getName(), toApplicationItem.getName(), number, name, fromApplicationItem.getId(), null);
	}

	private void createClusterClusterArrow(ApplicationItem fromApplicationItem,
			ApplicationItem toApplicationItem, int number, String name) {
		createArrowBetweenClusters(fromApplicationItem.getName(), toApplicationItem.getName(), number, name, fromApplicationItem.getId(), toApplicationItem.getId());
	}

}
