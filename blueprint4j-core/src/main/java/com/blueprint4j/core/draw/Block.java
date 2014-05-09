package com.blueprint4j.core.draw;

import com.blueprint4j.core.app.ApplicationItem;

public class Block {

	private ApplicationItem applicationItem;
	
	public Block(ApplicationItem applicationItem) {
		this.applicationItem = applicationItem;
	}
	
	public String getName(){
		return applicationItem.getName();
	}

	public ApplicationItem getApplicationItem() {
		return applicationItem;
	}


}
