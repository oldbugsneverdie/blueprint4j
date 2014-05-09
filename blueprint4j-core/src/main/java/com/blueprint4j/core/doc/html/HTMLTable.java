package com.blueprint4j.core.doc.html;

import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.doc.ITable;



public class HTMLTable implements ITable {

	public static final String DEFAULT_CSS_CLASS = "'noclass'";

	private List<HTMLTableRow> rows = new ArrayList<HTMLTableRow>();
	private StringBuffer content = new StringBuffer();
	private String cssClass = DEFAULT_CSS_CLASS;

	public HTMLTable() {
	}

	public HTMLTable(String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public HTMLTableRow addTableRow() {

		HTMLTableRow row = new HTMLTableRow();
		rows.add(row);
		return row;

	}

	@Override
	public HTMLTableRow addTableRow(boolean isHeaderRow) {

		HTMLTableRow row = new HTMLTableRow(isHeaderRow);
		rows.add(row);
		return row;

	}

	@Override
	public String getContent() {
		content.append("<table class=" + cssClass + ">");
		for (HTMLTableRow row : rows) {
			content.append(row.getContent());
		}
		content.append("</table>");
		return content.toString();
	}
}
