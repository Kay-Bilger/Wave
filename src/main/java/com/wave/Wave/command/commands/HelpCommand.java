package com.wave.Wave.command.commands;

import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.mainPackage.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;

public class HelpCommand implements ICommand {

	private final CommandManager manager;
	
	public HelpCommand(CommandManager manager) {
		this.manager = manager;
	}

	@Override
	public void handle(CommandContext ctx) {
		if (ctx.getArgs().isEmpty()) {
			EmbedBuilder bl = new EmbedBuilder();
			bl.setTitle("List of all the Commands");
			
			manager.getCommands().stream().forEach(c -> {
				bl.addField(c.getName(), c.getHelp(), false);
			});
			
			ctx.getChannel().sendMessage(bl.build()).queue();
			return;
		}
		
		String search = ctx.getArgs().get(0);
		ICommand command = manager.getCommand(search);
		
		if (command == null) {
			ctx.getChannel().sendMessage("Nothing found for " + search).queue();
			return;
		}
		
		EmbedBuilder bl = new EmbedBuilder();
		bl.addField(command.getName(), command.getHelp(), false);
		ctx.getChannel().sendMessage(bl.build()).queue();
		return;
	}

	@Override
	public String getName() {
		return "help";
	}

	@Override
	public String getHelp() {
		return "Display the functions of all the commands or of a specific command with the format (help [command])";
	}

}
