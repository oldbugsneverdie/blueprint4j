package com.blueprint4j.core.doc;


import com.blueprint4j.core.draw.Drawing;

import java.util.List;

public interface IDocument {

	public void addHeading(int level, String text);

	public void addParagraph(String paragraph);

	public void addText(String text);

	public String getPage();

	public ITable startTable();

	public void endTable(ITable table);

	public IList startList(String cssClass);

	public void endList(IList list);

	public void addExternalImage(String href);

    public void addDrawing(Drawing drawing);

    public List<Drawing> getDrawings();

	public void addStyleSheet(String styleSheetName);

	public String getToc();

}
