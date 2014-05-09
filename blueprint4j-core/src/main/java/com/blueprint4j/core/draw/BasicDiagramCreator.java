package com.blueprint4j.core.draw;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a graphical of the layers in an application design.
 */
public abstract class BasicDiagramCreator implements DrawingApplication{


	public static final String ARROW_HEAD_NONE = "none";
	public static final String ARROW_HEAD_CROW = "crow";
	public static final String ARROW_HEAD_NORMAL = "normal";

	protected String indent = "    ";
	public static String newline = System.getProperty("line.separator");
	
	private String nodeColor = "red";
	private String nodeFontName = "Courier";
	private String arrowColor = "blue";
	private String arrowStyle = "dashed";

	private StringBuffer sb = new StringBuffer();
	private List<Arrow> arrows = new ArrayList<Arrow>();

	public BasicDiagramCreator(String diagramName) {

		super();
		
		sb.append("digraph \""+diagramName+"\" {" + newline);
		sb.append("nodesep=1.0 // increases the separation between nodes" + newline);
		sb.append("node [color=" + nodeColor + ",fontname=" + nodeFontName + "]" + newline);
		sb.append("edge [color=" + arrowColor + ", style=" + arrowStyle + "] //setup options" + newline);
		sb.append("rankdir=\"TB\"" + newline);
		sb.append("compound=true;" + newline);

	}

	public String getScript(){
		for (Arrow arrow : arrows) {
			sb.append(arrow.getArrowAsDotScript() + newline);
		}
		sb.append("}" + newline);

		return sb.toString();
	}

	public SubGraph createSubGraph(String name, Long subgraphClusterIndex){
		return new SubGraph(name, subgraphClusterIndex);
	}

	public Elipse createElipse(String name){
		return new Elipse(name);
	}

	public void createArrow(String from, String to, String message){
		String fromNodeName="\""+ from + "\"";
		String arrowTail= ARROW_HEAD_NONE;
		String toNodeName="\""+ to + "\"";
		String arrowHead=ARROW_HEAD_NORMAL;
		String label = message;
		Arrow arrow = new Arrow(fromNodeName, arrowTail, toNodeName, arrowHead, label );
		arrows.add(arrow);
	}

	public void createArrow(String from, String to, int number, String message){
		String fromNodeName="\""+ from + "\"";
		String arrowTail= ARROW_HEAD_NONE;
		String toNodeName="\""+ to + "\"";
		String arrowHead=ARROW_HEAD_NORMAL;
		String label = number + ": " + message;
		Arrow arrow = new Arrow(fromNodeName, arrowTail, toNodeName, arrowHead, label );
		arrows.add(arrow);
	}
	
	public void createArrowBetweenClusters(String from, String to, int number, String message, Long fromClusterIndex, Long toClusterIndex){
		String fromNodeName="\""+ from + "\"";
		String arrowTail= ARROW_HEAD_NONE;
		String toNodeName="\""+ to + "\"";
		String arrowHead=ARROW_HEAD_NORMAL;
		String label = number + ": " + message;
		Arrow arrow = new Arrow(fromNodeName, arrowTail, toNodeName, arrowHead, label, fromClusterIndex, toClusterIndex );
		arrows.add(arrow);
	}

	public List<Arrow> getArrows() {
		return arrows;
	}



	public class SubGraph{
		private String name;
		private Long subGraphClusterIndex;
		private boolean useLabelForName =false;
		
		public SubGraph(String name, Long subGraphClusterIndex) {

			this.name = name;
			this.subGraphClusterIndex = subGraphClusterIndex;
			sb.append(indent + "subgraph cluster" + this.subGraphClusterIndex + " {"+ newline);
			if (useLabelForName ){
				sb.append(indent + "label=\"" + this.name + "\";" + newline);
			} else {
				sb.append(indent + "\"" + this.name + "\" [color=green, shape=none]" + newline);
			}
		}

		public void addScript(String script) {
			sb.append(script);
		}

		public void close(){
			sb.append(indent + "} " + newline);
		}

		public Long getSubGraphclusterIndex() {
			return subGraphClusterIndex;
		}

		public void setSubGraphclusterIndex(Long subGraphClusterIndex) {
			this.subGraphClusterIndex = subGraphClusterIndex;
		}


	}
	
	public class Elipse{
		private String name;

		public Elipse(String name) {

			this.name = name;
			sb.append(indent + "\""+this.name+ "\""+ newline);

		}

		public void addScript(String script) {
			sb.append(script);
		}

		public void close(){
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
		private Long  fromClusterName = null;
		private Long toClusterName = null;
		

		public String getArrowAsDotScript() {
			return this.getFromNode() + " -> " + this.getToNode() + " ["+ getArrowTail() + getArrowHead() + " label= \""
			+ this.getLabel() + "\", fontsize=\"" + this.getFontSize() + "\"" + getClusterTailHead() + "]";
		}

		private String getClusterTailHead() {
			String result = "";
			if (fromClusterName!=null){
				result+= " ltail=cluster"+fromClusterName;
			}
			if (fromClusterName!=null && toClusterName!=null){
				result+= " , lhead=cluster"+toClusterName;
			}
			if (fromClusterName==null && toClusterName!=null){
				result+= " lhead=cluster"+toClusterName;
			}
			
			return result;
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

		public Arrow(String fromNode, String fromArrowHead, String toNode, String toArrowHead, String label, Long fromClusterIndex, Long toClusterIndex) {
			init(fromNode, fromArrowHead, toNode, toArrowHead, label);
			this.fromClusterName = fromClusterIndex;
			this.toClusterName = toClusterIndex;
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
