package com.blueprint4j.template.diagram;

/**
 * Arrow between two Concepts.
 */
public class Arrow {

    private String from;
    private String to;
    private String text;

    public Arrow(String from, String text, String to) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
