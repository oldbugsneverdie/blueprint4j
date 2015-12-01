package com.blueprint4j.core.draw;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.blueprint4j.core.app.ApplicationItem;

/**
 * Abstract base class for defining sequence diagrams.
 */
public abstract class NewSequenceDiagram extends ApplicationItem {


	private List<NewSDMessage> messages = new ArrayList<NewSDMessage>();
	
	public NewSequenceDiagram(String name) {
		super(name);
	}

	public List<NewSDMessage> getMessages() {
		return messages;
	}
	
	public void createMessage(String message, ApplicationItem fromApplicationItem,ApplicationItem toApplicationItem){
		if (message  == null) {
			throw new RuntimeException("Can not create a message. Null not allowed");
		}
		if (fromApplicationItem == null) {
			throw new RuntimeException("Can not create a message. FromApplicationItem can not be null for message: " + message);
		}
		if (toApplicationItem == null) {
			throw new RuntimeException("Can not create a message. ToApplicationItem can not be null for message: " + message);
		}

		NewSDMessage sdMessage = new NewSDMessage(message);
		sdMessage.setFromApplicationItem(fromApplicationItem);
		sdMessage.setToApplicationItem(toApplicationItem);
		messages.add(sdMessage);
	}

	public String getMessagesDescription(){
		String result = "<ul>";
		for (NewSDMessage sdMessage : messages) {
			result += "<li><b>"+ sdMessage.getName() + "</b>(";
			if (sdMessage.getFromApplicationItem() == null) {
				throw new RuntimeException("Invalid sdMessage found. FromApplicationItem is null for: " + sdMessage.getName());
			}
			result += sdMessage.getFromApplicationItem().getName() + " --> ";
			if (sdMessage.getToApplicationItem() == null) {
				throw new RuntimeException("Invalid sdMessage found. ToApplicationItem is null for: " + sdMessage.getName());
			}
			result += sdMessage.getToApplicationItem().getName() + " )";
			result += "</li>";
		}
		result += "</ul>";
		return result;

	}


	public List<ApplicationItem> getAllApplicationItemsInThisDiagram(){
		Set<ApplicationItem> applicationItems = new HashSet<ApplicationItem>();
		for (NewSDMessage sdMessage : messages) {
			applicationItems.add(sdMessage.getFromApplicationItem());
			applicationItems.add(sdMessage.getToApplicationItem());
		}
		return new ArrayList<ApplicationItem>(applicationItems);
		
	}

	public Set<ApplicationItem> getApplicationItemsUsedByArrows() {
		Set<ApplicationItem> applicationItems = new HashSet<ApplicationItem>();
		for (NewSDMessage sdMessage : messages) {
			applicationItems.add(sdMessage.getFromApplicationItem());
			applicationItems.add(sdMessage.getToApplicationItem());
		}
		return new HashSet<ApplicationItem>(applicationItems);
		
	}
	
	public void markAllApplicationItemsInThisSequenceDiagramAsSelectedIncludingAllParents(){
		for (NewSDMessage sdMessage : messages) {
			sdMessage.getFromApplicationItem().markAsSelectedIncludingAllParents();
			sdMessage.getToApplicationItem().markAsSelectedIncludingAllParents();
		}
	}


}
