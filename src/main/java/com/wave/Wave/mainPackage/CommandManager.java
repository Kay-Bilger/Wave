package com.wave.Wave.mainPackage;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.command.commands.*;
import com.wave.Wave.config.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {
	private final List<ICommand> commands = new ArrayList<>();
	
	public CommandManager() {
		addCommand(new GayCommand());
		addCommand(new HelpCommand(this));
		addCommand(new JoinCommand());
		addCommand(new LeaveCommand());
		addCommand(new LogoCommand());
		addCommand(new PauseCommand());
		addCommand(new PlayCommand());
		addCommand(new QueueCommand());
		addCommand(new ResumeCommand());
		addCommand(new SkipCommand());
		addCommand(new StopCommand());
		addCommand(new ClearCommand());
		addCommand(new TestCommand());
	}
	
	private void addCommand(ICommand cmd) {
		boolean nameFound = this.commands.stream().anyMatch(c -> c.getName().equalsIgnoreCase(cmd.getName()));
		
		if(nameFound) {
			throw new IllegalArgumentException("Command with that name already exists");
		}
		
		commands.add(cmd);
	}
	
	@Nullable
	public ICommand getCommand(String search) {
		String searchLower = search.toLowerCase();
		
		for (ICommand cmd : this.commands) {
			if (cmd.getName().equals(searchLower)) {
				return cmd;
			}
		}
		return null;
	}
	
	void handle(GuildMessageReceivedEvent event) {
		String[] split = event.getMessage().getContentRaw()
				.replaceFirst("(?i)" + Pattern.quote(Config.getInstance().getString("prefix")), "")
				.split("\\s+");
		
		String invoke = split[0].toLowerCase();
		ICommand cmd = this.getCommand(invoke);
		
		if(cmd != null) {
			List<String> args = Arrays.asList(split).subList(1, split.length);
			CommandContext ctx = new CommandContext(event, args);
			cmd.handle(ctx);
		}
	}

	public List<ICommand> getCommands() {
		return commands;
	}
	
	
}
