package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.Concept;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private static int counter = 0;
    private Concept concept;
    private List<Node> nodeList = new ArrayList<Node>();
    private String imageName =null;

    public Node(Concept concept) {
        counter++;
        this.concept = concept;
    }

    public Node(Concept concept, String imageName) {
        counter++;
        this.concept = concept;
        this.imageName = imageName;
    }

    public String getName() {
        return concept.getName();
    }
    public Concept getConcept() {
        return concept;
    }

    public void add(Node node) {
        nodeList.add(node);
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean hasImageName(){
        return (imageName!=null);
    }

    public boolean hasSubNodes(){
        return !nodeList.isEmpty();
    }

    public int getCounter(){
        return counter;
    }

}
