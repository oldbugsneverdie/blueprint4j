package com.blueprint4j.core.doc.html;

import com.blueprint4j.core.doc.ITableRow;


public class HTMLTableRow implements ITableRow {

	private StringBuffer content = new StringBuffer();
	private boolean isHeaderRow;

	public HTMLTableRow() {
		super();
		this.isHeaderRow = false;
	}

	public HTMLTableRow(boolean isHeaderRow) {
		super();
		this.isHeaderRow = isHeaderRow;
	}

	@Override
	public void addTableData(String text) {
		if (this.isHeaderRow) {
			content.append("<th>" + text + "</th>");
		} else {
			content.append("<td>" + text + "</td>");
		}
	}

	@Override
	public String getContent() {
		return "<tr>" + content.toString() + "</tr>";
	}

	@Override
	public void startTableData() {
		if (this.isHeaderRow) {
			content.append("<th>");
		} else {
			content.append("<td>");
		}
	}

	@Override
	public void endTableData() {
		if (this.isHeaderRow) {
			content.append("</th>");
		} else {
			content.append("</td>");
		}
	}

	public boolean getHeaderRow() {
		return this.isHeaderRow;
	}

}
