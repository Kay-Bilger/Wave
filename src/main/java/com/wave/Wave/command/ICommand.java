package com.wave.Wave.command;

import java.util.Arrays;
import java.util.List;

public interface ICommand {

	void handle(CommandContext ctx);
	
	String getName();
	
	String getHelp();
	
	
	
	
	
	
}
