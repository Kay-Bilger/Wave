package com.wave.Wave.command.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;
import com.wave.Wave.music.TrackScheduler;


public class SkipCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		TrackScheduler scheduler = musicManager.scheduler;
		AudioPlayer player = musicManager.player;
		
		if(player.getPlayingTrack() == null) {
			ctx.getChannel().sendMessage("Not currently playing anything").queue();
			return;
		}
		
		if(scheduler.getQueue().isEmpty()) {
			player.stopTrack();
			ctx.getChannel().sendMessage("Queue Empty").queue();
			return;
		}
		
		scheduler.nextTrack();
		ctx.getChannel().sendMessage("Playing: " + player.getPlayingTrack().getInfo().title).queue();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "skip";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Skips the current track";
	}

}
