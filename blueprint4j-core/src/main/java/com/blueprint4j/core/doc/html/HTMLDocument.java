package com.blueprint4j.core.doc.html;

import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.doc.IDocument;
import com.blueprint4j.core.doc.IList;
import com.blueprint4j.core.doc.ITable;
import com.blueprint4j.core.draw.Drawing;


/**
 * IDocument that represents an HTML document.
 */
public class HTMLDocument implements IDocument {

	private StringBuffer doc = new StringBuffer();
	private StringBuffer toc = new StringBuffer();

	private List<String> styleSheets = new ArrayList<String>();
	int level1Number = 0;
	int level2Number = 0;
	int level3Number = 0;
	int level4Number = 0;
    private List<Drawing> drawings = new ArrayList<Drawing>();

	public HTMLDocument() {
		super();
		doc = new StringBuffer();
		toc = new StringBuffer();
		toc.append("<H2>Content</H2>");
	}

	public HTMLDocument(String contentTitle, String fileName) {
		super();
		doc = new StringBuffer();
		toc = new StringBuffer();
		toc.append("<H2>" + contentTitle + "</H2>");
	}

	@Override
	public void addHeading(int level, String text) {
		String levelNumber = getLevelNumber(level);

		// DOC
		doc.append("<H" + level + ">" + levelNumber + text + "</H" + level + ">");

		// TOC
		if (level <= 2) {
			if (level == 1) {
				toc.append("<b>" + levelNumber + text + "</b><br/>");
			}
			if (level == 2) {
				toc.append("<span>" + levelNumber + text + "</span><br/>");
			}
		}
	}

	private String getLevelNumber(int level) {
		if (level == 1) {
			level1Number++;
			level2Number = 0;
			level3Number = 0;
			level4Number = 0;
			return String.valueOf(level1Number) + " ";
		}
		if (level == 2) {
			level2Number++;
			level3Number = 0;
			level4Number = 0;
			return String.valueOf(level1Number) + "." + String.valueOf(level2Number) + " ";
		}
		if (level == 3) {
			level3Number++;
			level4Number = 0;
			return String.valueOf(level1Number) + "." + String.valueOf(level2Number) + "."
					+ String.valueOf(level3Number) + " ";
		}
		if (level == 4) {
			level4Number++;
			return String.valueOf(level1Number) + "." + String.valueOf(level2Number) + "."
					+ String.valueOf(level3Number) + "." + String.valueOf(level4Number) + " ";
		}
		return "";
	}

	@Override
	public void addParagraph(String paragraph) {

		doc.append("<p>" + paragraph + "</p>");

	}

	@Override
	public void addText(String text) {

		doc.append(text);

	}

	public String getPage() {
		StringBuffer result = new StringBuffer();
		result.append("<html>");
		result.append("<head>");
		result.append(getInternalStyle());
		for (String styleSheetName : styleSheets) {
			result.append("<link rel='stylesheet' type='text/css' href='" + styleSheetName + "' />");
		}
		result.append("</head>");
		result.append("<body>");
		result.append(doc.toString());
		result.append("</body>");
		result.append("<html>");
		return result.toString();
	}

	private String getInternalStyle() {
		StringBuffer result = new StringBuffer();
		result.append("<style type='text/css'>");
		result
			.append("body {padding-top: 8px;font: 1em 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Helvetica, Arial, sans-serif;color: #2a2121;background: #FFFFFF url(/css/i/nice-light.gif) 227px 319px no-repeat;}");
		result.append("h2, h3, h4, h5, h6 {margin: 1.5em 0 0.25em;font-weight: normal;line-height: 1em;}");
		result
			.append("h1 {margin-top: 24px;margin-bottom: 0.618em;font-weight: normal;font-size: 2.2em;color: #2a2121;text-shadow: 0 1px 0 #CCC6BA;}");
		result.append("h2 {font-size: 1.9em;}");
		result
			.append("h3 {margin-top: 0;font-weight: normal;font-size: 1.6em;font-family: 'Lucida Grande', 'Lucida Sans Unicode', 'Lucida Sans', Helvetica, Arial, sans-serif;line-height: 1.3em;color: #666054;}");
		result.append("h4 {margin: 0 0 0.25em;font-size: 1.3em;line-height: 1.858em;color: #2a2121;}");
		result
			.append("h5 {margin-bottom: 0.5em;font-size: 1.1em;text-transform: uppercase;letter-spacing: 0.1em;color: #1E2630;}");
		result.append("p {margin: 0 0 1em;font-size: 1.1em;line-height: 1.618em;}");
		result.append("hr {color:sienna;}");
		result.append("ul {list-style-type:none;}");
		result.append("ul.buttonlist li {float:left;margin-right:20px;}");
		result.append("ul.buttonlist li a {text-decoration:none;}");
		result.append("ul.errorlist {background-color:pink;}");
		result.append("img {padding:5px;}");
		result.append("fieldset {border:none;padding:0.5em}");
		result.append(".screen {border:1px solid black;margin-bottom:2em;background-color:#feff8f}");
		result.append(".screen_content {padding:0.5em 0;}");
		result.append(".screen_header {border-bottom:1px solid black;padding:0.5em;}");
		result.append(".screen_footer {border-top:1px solid black;padding:0.5em;}");
		result.append(".grid th td {background-color:green;}");
		/* general table styles */
		result.append("table, td{font:100% Arial, Helvetica, sans-serif; }");
		result.append("table {border-collapse:collapse;margin:1em 0;}");
		result.append("table th, td{text-align:left;padding:.5em;border:1px solid #fff;}");
		result.append("table th{background:#328aa4 url(tr_back.gif) repeat-x;color:#fff;}");
		result.append("table td{background:#e5f1f4;}");
		/* table used for menu's */
		result.append("table.menu{margin:5px;}");
		result.append("table.menu td{}");
		/* table holding buttons's */
		result.append("table.buttonrow {}");
		result.append("table.buttonrow th{}");
		result.append("table.buttonrow td{ background:none;border:none;}");
		/* table with 'no' styling */
		result.append("table.noclass {border:1px solid black;}");
		result.append("table.noclass th{background-color:grey;}");
		result.append("table.noclass td{ background:none;border:1px solid black;}");
		/* specific table styles */
		result.append("tr.even td{background:#e5f1f4;}");
		result.append("tr.odd td{background:#f8fbfc;}");
		result.append("th.over, tr.even th.over, tr.odd th.over{background:#4a98af;}");
		result.append("th.down, tr.even th.down, tr.odd th.down{background:#bce774;}");
		result.append("th.selected, tr.even th.selected, tr.odd th.selected{}");
		result.append("td.over, tr.even td.over, tr.odd td.over{background:#ecfbd4;}");
		result.append("td.down, tr.even td.down, tr.odd td.down{background:#bce774;color:#fff;}");
		result.append("td.selected, tr.even td.selected, tr.odd td.selected{background:#bce774;color:#555;}");
		/* use this if you want to apply different styling to empty table cells */
		result.append("td.empty, tr.odd td.empty, tr.even td.empty{background:#fff;}");
		/* style for vertical panel's */
		result.append("div.vpanel{display:block;padding:0 5px;}");
		/* style for horizontal panel's */
		result.append("div.hpanel{display:inline-block;padding:5px;}");
		/* style for menu panel */
		result
			.append("div.menu{display:inline-block;border:1px solid black;margin-bottom:10px;background-color:#E5F1F4;}");
		/* style for the header of a demo page */
		result
			.append("div.demoheader{background-color:#d8d8d8;padding:5px;margin:0px 5px 30px 5px;border:1px solid black;}");
		/* buttonlist */
		result.append(".buttonlist {display: inline-block;}");
		/* button */
		result
			.append(".button {padding: 5px 10px;display: inline;background: #777 url(button.png) repeat-x bottom;border: none; color: #fff; cursor: pointer;font-weight: bold;border-radius: 5px;-moz-border-radius: 5px;-webkit-border-radius: 5px;text-shadow: 1px 1px #666;}");
		result.append(".button:hover {background-position: 0 -48px;}");
		result.append(".button:active {background-position: 0 top;position: relative;top: 1px;padding: 6px 10px 4px;}");
		result.append(".button.red { background-color: #e50000; }");
		result.append(".button.purple { background-color: #9400bf; }");
		result.append(".button.green { background-color: #58aa00; }");
		result.append(".button.orange { background-color: #ff9c00; }");
		result.append("	.button.blue { background-color: #2c6da0; }");
		result.append("	.button.black { background-color: #333; }");
		result.append("	.button.white { background-color: #fff; color: #000; text-shadow: 1px 1px #fff; }");
		result.append("	.button.small { font-size: 75%; padding: 3px 7px; }");
		result.append("	.button.small:hover { background-position: 0 -50px; }");
		result.append("	.button.small:active { padding: 4px 7px 2px; background-position: 0 top; }");
		result.append("	.button.large { font-size: 125%; padding: 7px 12px; }");
		result.append("	.button.large:hover { background-position: 0 -35px; }");
		result.append("	.button.large:active { padding: 8px 12px 6px; background-position: 0 top; }");
		result.append("</style>");
		return result.toString();
	}

	public String getHtmlBody() {
		StringBuffer result = new StringBuffer();
		result.append(doc.toString());
		return result.toString();
	}

	@Override
	public HTMLTable startTable() {
		return new HTMLTable();
	}

	public HTMLTable startTable(String cssClassName) {
		return new HTMLTable(cssClassName);
	}

	@Override
	public void endTable(ITable table) {
		doc.append(table.getContent());
	}

	@Override
	public void addStyleSheet(String styleSheetName) {
		styleSheets.add(styleSheetName);
	}

	@Override
	public IList startList(String cssClass) {
		return new HTMLList(cssClass);
	}

	@Override
	public void endList(IList list) {
		doc.append(list.getContent());
	}


	private String getHyperLink(String name, String href) {
		return "<a href='" + href + "' class='link'>" + name + "</a>";
	}

	public String getToc() {
		return toc.toString();
	}

	@Override
	public void addExternalImage(String href) {
		doc.append("<img src='" + href + "'/>");
	}

    @Override
    public void addDrawing(Drawing drawing) {
        drawings.add(drawing);
        doc.append("<img src='" + drawing.getName()+ ".png'/>");

    }

    public List<Drawing> getDrawings() {
        return drawings;
    }



}
