package com.wave.Wave.command.commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();
		
		if(queue.isEmpty()) {
			ctx.getChannel().sendMessage("The queue is empty").queue();
			return;
		}
		
		int trackCount = Math.min(queue.size(), 20);
		List<AudioTrack> tracks = new ArrayList<>(queue);
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Current Queue (Total: " + queue.size() + ")");
		
		for (int i = 0; i < trackCount; i++) {
			AudioTrack track = tracks.get(i);
			AudioTrackInfo info = track.getInfo();
			
			eb.appendDescription(String.format("%s - %s\n", info.title, info.author));
		}
		
		ctx.getChannel().sendMessage(eb.build()).queue();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "queue";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Shows the current queue";
	}

}
