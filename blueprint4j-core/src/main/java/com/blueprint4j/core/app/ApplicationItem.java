package com.blueprint4j.core.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.blueprint4j.core.change.Question;
import com.blueprint4j.core.translate.Translatable;
import com.blueprint4j.core.translate.Translator;

/**
 * Base class for describing items in your specification/design/deployment.
 */
public abstract class ApplicationItem implements Translatable {
	
	/**
	 * Unique identification of this item. 
	 */
	private Long id;

	/**
	 * The name of this item.
	 */
	private String name = "No name available";

	/**
	 * A description of this item.
	 */
	private String description = "No description available";
	
	/**
	 * A list of questions related to this item.
	 */
	private List<Question> questionList = new ArrayList<Question>();
	
	/**
	 * A list of sub application items.
	 */
	private  List<ApplicationItem> subApplicationItems= new ArrayList<ApplicationItem>();
	
	/**
	 * Counter to give each application item a unique id.
	 */
	private static Long objectCounter = 1L;

	/**
	 * If true, this ApplicationItem is selected for some purpose. E.g. this can be used to select certain ApplicationItems to be drawn in a diagram an others not. 
	 */
	private boolean selected = false;
	
	/**
	 * The application item that is the parent of this application item.
	 */
	private ApplicationItem parent = null;
	
	/**
	 * Constructor.
	 * @param name name of the application item
	 */
	public ApplicationItem(String name) {
		if (name == null || name.isEmpty()) {
			throw new RuntimeException("You can not create an ApplicationItem without a name.");
		}
		this.name = name; //.replace(' ', '_');
		this.id = objectCounter++;
	}
	
	protected void onCreate(){
		
	}

	protected void onLink(){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	public Question createQuestion(String text) {
		return null;
		// TODO return new Question(text, this);
	}

	public Question createQuestion(String question, String answer) {
		return null;
		// Question q = new Question(question, this);
		// q.setAnswer(answer);
		// return q;
	}

	public void register(com.blueprint4j.core.change.Question question) {
		questionList.add(question);
	}

	public void removeQuestion(com.blueprint4j.core.change.Question question) {
		questionList.remove(question);
	}

	public List<Question> getQuestionList() {
		return questionList;
	}

	public boolean hasSubApplicationItems() {
		return subApplicationItems.size()>0;
	}

	public List<ApplicationItem> getSubApplicationItems() {
		return subApplicationItems;
	}
	
	public void addSubApplicationItem(ApplicationItem applicationItem){
		if (applicationItem ==null){
			throw new RuntimeException("Can not add a sub application item to '"+this.getName()+"', beacuse the applicationItem supplied is null");
		}
		subApplicationItems.add(applicationItem);
		applicationItem.setParent(this);
	}


	public Long getId() {
		return id;
	}

	/**
	 * Returns true in case this application item (or any of it's sub application items) has a sub application item that equals the applicationItemToFind.
	 */
	public boolean contains(ApplicationItem applicationItemToFind){
		for (ApplicationItem subApplicationItem : subApplicationItems) {
			if (subApplicationItem.equals(applicationItemToFind) || subApplicationItem.contains(applicationItemToFind) ){
				return true;
			}
		}
		return false;
	}

	public boolean containsAnyOf(Set<ApplicationItem> applicationItems) {
		for (Iterator<ApplicationItem> iterator = applicationItems.iterator(); iterator.hasNext();) {
			ApplicationItem applicationItem = (ApplicationItem) iterator.next();
			if (this.equals(applicationItem) || this.contains(applicationItem)){
				return true;
			}	
		}
		return false;
	}
	
	public boolean equalsAnyOf(Set<ApplicationItem> applicationItems) {
		for (Iterator<ApplicationItem> iterator = applicationItems.iterator(); iterator.hasNext();) {
			ApplicationItem designObject = (ApplicationItem) iterator.next();
			if (this.equals(designObject)){
				return true;
			}	
		}
		return false;
	}

	public boolean hasChildren() {
		return subApplicationItems.size()>0;
	}

	public boolean equals(Object obj) {
	    if(obj == null)                return false;
	    if(!(obj instanceof ApplicationItem)) return false;

	    ApplicationItem other = (ApplicationItem) obj;
	    return this.getId()== other.getId();
	}

	  public int hashCode(){
		  return new Long(this.getId()).intValue();
	  }
	  
	@Override
	public String toString() {
		return getName();
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setParent(ApplicationItem parent) {
		this.parent = parent;
	}

	public ApplicationItem getParent() {
		return parent;
	}

	public void markAsSelectedIncludingAllParents(){
		this.setSelected(true);
		if (parent!=null){
			parent.setSelected(true);
			parent.markAllParentsAsSelected();
		}
	}

	public void markAsUnSelectedIncludingAllParents(){
		this.setSelected(false);
		if (parent!=null){
			parent.setSelected(false);
			parent.markAllParentsAsSelected();
		}
	}

	public void markAllParentsAsSelected(){
		if (parent!=null){
			parent.setSelected(true);
			parent.markAllParentsAsSelected();
		}
	}

	public void markAllParentsAsNotSelected(){
		if (parent!=null){
			parent.setSelected(false);
			parent.markAllParentsAsNotSelected();
		}
	}

	public void markAllSubApplicationItemsAsSelected(){
		for (ApplicationItem applicationItem : subApplicationItems) {
			applicationItem.setSelected(true);
			applicationItem.markAllSubApplicationItemsAsSelected();
		}
	}

	public void markAllSubApplicationItemsAsNotSelected(){
		for (ApplicationItem applicationItem : subApplicationItems) {
			applicationItem.setSelected(false);
			applicationItem.markAllSubApplicationItemsAsNotSelected();
		}
	}

    @Override
    public void accept(Translator translator) {
        this.name = translator.translate(this.name);
        this.description = translator.translate(this.description);
    }

}
