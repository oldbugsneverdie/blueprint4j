package com.blueprint4j.core.doc;

public interface ITableRow {

	public void startTableData();

	public void endTableData();

	public void addTableData(String text);

	public String getContent();

}
