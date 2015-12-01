package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.Concept;

import java.util.ArrayList;
import java.util.List;

public class DiagramHelper {

	public static String newline = System.getProperty("line.separator");
	private static final int MAX_DESCRIPTION = 30;
	private static final int LABEL_FONT_SIZE = 8;
	public static final String ARROW_HEAD_NONE = "none";
	public static final String ARROW_HEAD_CROW = "crow";
	public static final String ARROW_HEAD_NORMAL = "normal";

	private Node rootNode = null;
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

	public Node createRootNode(Concept mainConcept) {
		rootNode = new Node(mainConcept);
		return rootNode;
	}

	public Node createNode(Concept concept, Node parentNode) {
		Node node = new Node(concept);
		parentNode.add(node);
		return node;
	}

	public String getDiagramScript() {
		dotScript.append("digraph \""+rootNode.getName()+"\"{" + newline);
		dotScript.append("nodesep=1.0 // increases the separation between nodes" + newline);
        dotScript.append("node [shape=box,style=filled,fillcolor=\"#C0D0C0\"]" + newline);
		dotScript.append("edge [color=" + arrowColor + ", style=" + arrowStyle + "] //setup options" + newline);
        dotScript.append("graph [overlap=false,splines=true,compound=true, size=16]");

		dotScript.append(getScriptForNode(rootNode));

		//Add all nodes, to make sure they are displayed.
		//dotScript.append(getAllNodes(rootNode));
		
		for (Arrow arrow : arrowsList) {
			dotScript.append(arrow.getArrowAsDotScript() + newline);
		}
        // Add invisible arrows to display components vertically
        dotScript.append("{edge[style=invis]");
        dotScript.append(makeVertical(rootNode));
        dotScript.append("}");
		//End of script
		dotScript.append("}" + newline);

		return dotScript.toString();
	}

    private String makeVertical(Node rootNode) {
         StringBuffer sb = new StringBuffer();
        sb.append("{" + "\"" + rootNode.getName() + "\"");
        if( rootNode.hasSubNodes()){
            sb.append(" -> ");
        }
        sb.append( newline);
        for (Node node : rootNode.getNodeList()) {
            sb.append(makeVertical(node));
        }
        sb.append("}");
        return sb.toString();
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
            if(parentNode.hasImageName()){
                sb.append("\"" + parentNode.getConcept().getTechnicalName()+"\"[shape=\"none\" style=\"\" image=\""+parentNode.getImageName()+"\"];" + newline);
            }else {
			    sb.append("\"" + parentNode.getConcept().getTechnicalName()+"\";" + newline);
            }
		} else {
			clusterIndex++;
			sb.append(indent + "subgraph " + parentNode.getConcept().getTechnicalName() + " {"+ newline);
			//sb.append(indent + "label=\"" + parentNode.getConcept().getName() + "\";" + newline);
            // Instead of a label, create a node with text. This guarantees that we have at leaset one node in every cluster.
            // This then will enable drawing lines directly to a cluster (using "lhead=<name_of_cluster>" on the arrow)
            sb.append(indent + "\"" + parentNode.getConcept().getName() + "\"" + " [style=none,shape=plaintext];" + newline);
            for (Node node : parentNode.getNodeList()) {
				sb.append(getScriptForNode(node));
			}
			sb.append(indent + "} " + newline);
		}

        //TODO implement conceptProperties
//        if (parentNode.getConcept().hasConceptProperties(){
//
//        }

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

    public void createArrow(Concept fromConcept, String arrowTail, Concept toConcept, String arrowHead, String label) {
        Arrow arrow = new Arrow(fromConcept, arrowHead, toConcept, arrowTail, label);
        arrowsList.add(arrow);
    }


    public class Box extends Node {

		public Box(Concept concept) {
			super(concept);
		}

	}

	public class Arrow {
		private String color = "Blue";
		private String style = "dashed";
		private String fontSize = "10";
		private Concept fromConcept;
		private Concept toConcept;
		private String fromArrowHead = ARROW_HEAD_NORMAL;
		private String toArrowHead = ARROW_HEAD_NORMAL;
		private String label = "";

		public String getArrowAsDotScript() {
            // Arrows directed to clusters, are actually directed at the always present invisible node, and then
            // an "lhead" property is added as a trick to make the arrow land on the edge of the cluster.
            if (toConcept.hasSubConcepts()){
                return "\"" + fromConcept.getTechnicalName() + "\"" + " -> " + "\"" + toConcept.getName() + "\"" + " [" + getArrowTail() + getArrowHead() + " label= \""
                        + this.getLabel() + "\", fontsize=\"" + this.getFontSize() + "\", lhead=\"" + toConcept.getTechnicalName() + "\"]";
            } else {
                return "\"" + fromConcept.getTechnicalName() + "\"" + " -> " + "\"" + toConcept.getTechnicalName() + "\"" + " [" + getArrowTail() + getArrowHead() + " label= \""
                        + this.getLabel() + "\", fontsize=\"" + this.getFontSize() + "\"]";
            }
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

        public Arrow(Concept fromConcept, String fromArrowHead, Concept toConcept, String toArrowHead, String label) {
			init(fromConcept, fromArrowHead, toConcept, toArrowHead, label);
		}


		private void init(Concept fromConcept, String fromArrowHead, Concept toConcept, String toArrowHead, String label) {
			this.fromConcept = fromConcept;
			this.fromArrowHead = fromArrowHead;
			this.toConcept = toConcept;
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
