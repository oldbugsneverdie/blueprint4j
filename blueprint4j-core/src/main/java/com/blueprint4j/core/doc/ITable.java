package com.blueprint4j.core.doc;


public interface ITable {

	public ITableRow addTableRow();

	public ITableRow addTableRow(boolean isHeaderRow);

	public String getContent();

}
