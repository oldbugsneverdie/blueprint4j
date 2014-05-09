package com.blueprint4j.core.doc;


public interface IDocument {

	public void addHeading(int level, String text);

	public void addParagraph(String paragraph);

	public void addText(String text);

	public String getPage();

	public ITable startTable();

	public void endTable(ITable table);

	public IList startList(String cssClass);

	public void endList(IList list);

	public void addImage(String href);

	public void addStyleSheet(String styleSheetName);

	public String getToc();

}
