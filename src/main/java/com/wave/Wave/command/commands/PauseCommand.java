package com.wave.Wave.command.commands;


import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.mainPackage.JDAMain;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;
import net.dv8tion.jda.api.entities.Activity;

public class PauseCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		
		if(musicManager.player.isPaused()) {
			ctx.getChannel().sendMessage("Allready paused, use [resume] to resume").queue();
			return;
		}
		
		JDAMain.getJDA().getPresence().setActivity(Activity.playing("Paused"));
		musicManager.player.setPaused(true);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "pause";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "pauses the musicbot";
	}

}	
