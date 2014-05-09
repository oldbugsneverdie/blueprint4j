package com.blueprint4j.core.draw;

import java.util.ArrayList;
import java.util.List;

public class DiagramHelper {

	public static String newline = System.getProperty("line.separator");
	private static final int MAX_DESCRIPTION = 30;
	private static final int LABEL_FONT_SIZE = 8;
	public static final String ARROW_HEAD_NONE = "none";
	public static final String ARROW_HEAD_CROW = "crow";
	public static final String ARROW_HEAD_NORMAL = "normal";

	private Node rootNode = new Node("");
	private int clusterIndex = 0;
	
	private List<Arrow> arrowsList = new ArrayList<Arrow>();

	private StringBuffer dotScript;
	private boolean showLabels = true;
	private String nodeColor = "red";
	private String nodeFontName = "Courier";

	private String arrowColor = "black";
	private String arrowStyle = "dashed";

	public DiagramHelper() {
		super();
		dotScript = new StringBuffer();
	}

	public DiagramHelper(boolean showLabels) {
		super();
		dotScript = new StringBuffer();
		this.showLabels = showLabels;
	}

	public Node createRootNode(String name) {
		rootNode.setName(name);
		return rootNode;
	}

	public Node createNode(String name, Node parentNode) {
		Node node = new Node(name);
		parentNode.add(node);
		return node;
	}

	public void createArrow(String fromNodeName, String arrowTail, String toNodeName, String arrowHead, String label) {
		Arrow arrow = new Arrow(fromNodeName, arrowTail, toNodeName, arrowHead, label);
		arrowsList.add(arrow);
	}

	public String getDiagramScript() {
		dotScript.append("digraph \""+rootNode.getName()+"\" {" + newline);
		dotScript.append("nodesep=1.0 // increases the separation between nodes" + newline);
        dotScript.append("node [shape=box,style=filled,fillcolor=\"#C0D0C0\"]" + newline);
		dotScript.append("edge [color=" + arrowColor + ", style=" + arrowStyle + "] //setup options" + newline);
		
		dotScript.append(getScriptForNode(rootNode));

		//Add all nodes, to make sure they are displayed.
		//dotScript.append(getAllNodes(rootNode));
		
		for (Arrow arrow : arrowsList) {
			dotScript.append(arrow.getArrowAsDotScript() + newline);
		}

		//End of script
		dotScript.append("}" + newline);

		return dotScript.toString();
	}

	private StringBuffer getAllNodes(Node parentNode) {
		StringBuffer sb = new StringBuffer();
		sb.append(parentNode.getName() + ";" + newline);
		for (Node node : parentNode.getNodeList()) {
			sb.append(getAllNodes(node));
		}
		return sb;
	}

	private StringBuffer getScriptForNode(Node parentNode) {
		String indent = getIndent(clusterIndex);
		StringBuffer sb = new StringBuffer();
		sb.append(newline);
		if (parentNode.getNodeList().isEmpty()){
			sb.append("\"" + parentNode.getName()+"\";" + newline);
		} else {
			clusterIndex++;
			sb.append(indent + "subgraph cluster" + clusterIndex + " {"+ newline);
			sb.append(indent + "label=\"" + parentNode.getName() + "\";" + newline);
			for (Node node : parentNode.getNodeList()) {
				sb.append(getScriptForNode(node));
			}
			sb.append(indent + "} " + newline);
		}
		sb.append(newline);
		return sb;
	}

	private String getIndent(int nrOfIndents) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nrOfIndents; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public String wrapLabelOverMultipleLines(String label) {

		return wrapLabelOverMultipleLines(label, MAX_DESCRIPTION);

	}

	public String wrapLabelOverMultipleLines(String label, int maxNumberOfCharacters) {
		String result = "";
		if (!showLabels) {
			return result;
		}
		if (label.isEmpty()) {
			return result;
		}
		// wrap label over multiple lines
		int len = label.length();
		for (int i = 0; i < len; i += maxNumberOfCharacters) {
			String part = label.substring(i, Math.min(len, i + maxNumberOfCharacters));
			result += part + "\\n";
		}
		return result;
	}

	public void setNodeColor(String nodeColor) {
		this.nodeColor = nodeColor;
	}

	public String getNodeColor() {
		return nodeColor;
	}

	public void setNodeFontName(String nodeFontName) {
		this.nodeFontName = nodeFontName;
	}

	public String getNodeFontName() {
		return nodeFontName;
	}

	public void setArrowColor(String arrowColor) {
		this.arrowColor = arrowColor;
	}

	public String getArrowColor() {
		return arrowColor;
	}

	public void setArrowStyle(String arrowStyle) {
		this.arrowStyle = arrowStyle;
	}

	public String getArrowStyle() {
		return arrowStyle;
	}

	public class Node {

		private String name;
		private List<Node> nodeList = new ArrayList<Node>();
		
		public Node(String name) {
			this.setName(name);
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
		
		public void add(Node node) {
			nodeList.add(node);
		}
		
		public List<Node> getNodeList() {
			return nodeList;
		}
		
		public Node createSubNode(String name) {
			Node node = new Node(name);
			nodeList.add(node);
			return node;
		}

	}
	
	public class Box extends Node {

		public Box(String name) {
			super(name);
		}

	}

	public class Arrow {
		private String color = "Blue";
		private String style = "dashed";
		private String fontSize = "10";
		private String fromNode;
		private String toNode;
		private String fromArrowHead = ARROW_HEAD_NONE;
		private String toArrowHead = ARROW_HEAD_NONE;
		private String label = "";

		public String getArrowAsDotScript() {
			return "\"" + this.getFromNode() + "\"" +" -> " + "\"" +this.getToNode() + "\"" +" ["+ getArrowTail() + getArrowHead() + " label= \""
			+ this.getLabel() + "\", fontsize=\"" + this.getFontSize() + "\"]";
		}

		private String getArrowTail() {
			if (getFromArrowHead()==null){
				return "";
			} else {
				return "arrowtail=\""+ this.getFromArrowHead() + "\",";
			}
		}
		private String getArrowHead() {
			if (getToArrowHead()==null){
				return "";
			} else {
				return "arrowhead=\""+ this.getToArrowHead() + "\",";
			}
		}

		public Arrow(String fromNode, String toNode) {
			init(fromNode, fromArrowHead, toNode, toArrowHead, label);
		}

		public Arrow(String fromNodeName, String toNodeName, String label) {
			init(fromNodeName, fromArrowHead, toNodeName, toArrowHead, label);
		}

		public Arrow(String fromNode, String fromArrowHead, String toNode, String toArrowHead) {
			init(fromNode, fromArrowHead, toNode, toArrowHead, label);
		}

		public Arrow(String fromNode, String fromArrowHead, String toNode, String toArrowHead, String label) {
			init(fromNode, fromArrowHead, toNode, toArrowHead, label);
		}


		private void init(String fromNode, String fromArrowHead, String toNode, String toArrowHead, String label) {
			this.fromNode = fromNode;
			this.fromArrowHead = fromArrowHead;
			this.toNode = toNode;
			this.toArrowHead = toArrowHead;
			this.label = label;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getStyle() {
			return style;
		}

		public void setStyle(String style) {
			this.style = style;
		}

		public String getFromNode() {
			return fromNode;
		}

		public void setFromNode(String fromNode) {
			this.fromNode = fromNode;
		}

		public String getToNode() {
			return toNode;
		}

		public void setToNode(String toNode) {
			this.toNode = toNode;
		}

		public String getFromArrowHead() {
			return fromArrowHead;
		}

		public void setFromArrowHead(String fromArrowHead) {
			this.fromArrowHead = fromArrowHead;
		}

		public String getToArrowHead() {
			return toArrowHead;
		}

		public void setToArrowHead(String toArrowHead) {
			this.toArrowHead = toArrowHead;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public void setFontSize(String fontSize) {
			this.fontSize = fontSize;
		}

		public String getFontSize() {
			return fontSize;
		}

	}


}
