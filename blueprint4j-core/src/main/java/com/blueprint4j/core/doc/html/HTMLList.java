package com.blueprint4j.core.doc.html;

import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.doc.IList;


public class HTMLList implements IList {

	public static final String DEFAULT_CSS_CLASS = "'noclass'";

	private List<String> items = new ArrayList<String>();
	private StringBuffer content = new StringBuffer();
	private String cssClass = DEFAULT_CSS_CLASS;

	public HTMLList() {
	}

	public HTMLList(String cssClass) {
		this.cssClass = cssClass;
	}

	@Override
	public String addItem(String item) {

		items.add(item);
		return item;

	}

	@Override
	public String getContent() {
		content.append("<ul class=" + cssClass + ">");
		for (String item : items) {
			content.append("<li>" + item + "</li>");
		}
		content.append("</ul>");
		return content.toString();
	}
}
