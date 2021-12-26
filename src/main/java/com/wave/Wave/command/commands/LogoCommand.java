package com.wave.Wave.command.commands;

import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import java.util.List;

public class LogoCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		EmbedBuilder builder = new EmbedBuilder();
		builder.setImage("https://i.imgur.com/aUvnYQ3.png");
		ctx.getChannel().sendMessage(builder.build());
	}

	@Override
	public String getName() {
		return "logo";
	}

	@Override
	public String getHelp() {
		return "Displays the Gras im Glas Logo";
	}

}
