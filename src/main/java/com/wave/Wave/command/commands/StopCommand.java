package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;

public class StopCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		
		if(musicManager.player.isPaused()) {
			ctx.getChannel().sendMessage("Allready paused, use [resume] to resume").queue();
			return;
		}
		
		musicManager.player.setPaused(true);
		
		ctx.getChannel().sendMessage("Stopping the player").queue();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "stop";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Stops the musicbot";
	}
	

}
