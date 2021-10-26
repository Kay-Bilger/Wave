package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;

public class ResumeCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		
		musicManager.player.setPaused(false);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "resume";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "resumes the musicbot";
	}

}
