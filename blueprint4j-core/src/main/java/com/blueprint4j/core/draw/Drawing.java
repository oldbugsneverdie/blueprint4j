package com.blueprint4j.core.draw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.blueprint4j.core.app.ApplicationItem;
import com.blueprint4j.core.translate.Translator;

public class Drawing extends ApplicationItem{

	private List<Block> blocks = new ArrayList<Block>();
	private List<Line> lines = new ArrayList<Line>();
	
	public Drawing(String name) {
		super(name);
	}

	public Block drawBlock(ApplicationItem applicationItem) {
		Block block = new Block(applicationItem);
		blocks.add(block);
		return block;
	}

	public List<Block> getBlocks() {
		return blocks;
	}

	public Line drawLine(Block fromBlock, String name, Block toBlock) {
		Line line = new Line(fromBlock.getApplicationItem(), name, toBlock.getApplicationItem());
		lines.add(line);
		return line;
	}

	public List<Line> getLines() {
		return lines;
	}

	@Override
	public void accept(Translator translator) throws IOException {
		// TODO Auto-generated method stub
		
	}



}
