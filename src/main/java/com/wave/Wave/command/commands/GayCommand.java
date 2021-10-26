package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;

public class GayCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		
		if (ctx.getArgs().isEmpty()) {
		ctx.getChannel().sendMessageFormat(ctx.getAuthor().getName() + " is fucking gay").queue();
		}
		else {
		ctx.getChannel().sendMessageFormat(ctx.getArgs().get(0) + " is fucking gay").queue();
		}
	}

	@Override
	public String getName() {
		return "gay";
	}

	@Override
	public String getHelp() {
		return "Calls you or one of your friends gay";
	}
}
