package com.blueprint4j.core.change;

/**
 * Something you want to know. 
 * The idea here is to be able to add questions to the objects in your specification/design/deployment. This then can later be answered.
 */
public class Question {

	private String question;
	private String answer;

	/**
	 * Constructor.
	 */
	public Question(String text) {
		super();
		this.setQuestion(text);
		this.setAnswer("?");
	}

	/**
	 * Constructor.
	 */
	public Question(String text, String answer) {
		super();
		this.setQuestion(text);
		this.setAnswer(answer);
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestion() {
		return question;
	}


}
