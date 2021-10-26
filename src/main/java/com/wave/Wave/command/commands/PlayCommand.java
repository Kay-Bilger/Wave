package com.wave.Wave.command.commands;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import com.wave.Wave.command.CommandContext;
import com.wave.Wave.command.ICommand;
import com.wave.Wave.config.Config;
import com.wave.Wave.music.GuildMusicManager;
import com.wave.Wave.music.LinkConverter;
import com.wave.Wave.music.PlayerManager;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PlayCommand implements ICommand {
	private final YouTube youtube;
	
	public PlayCommand() {
		YouTube temp = null;
		
		try {
			temp = new YouTube.Builder(
					GoogleNetHttpTransport.newTrustedTransport(),
					JacksonFactory.getDefaultInstance(),
					null)
					.setApplicationName("BitchesBangerBot")
					.build();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		youtube = temp;
	}
	
	@Nullable
	private String searchYoutube (String search) {
		try {
			List<SearchResult> results = youtube.search()
					.list("id,snippet")
					.setQ(search)
					.setMaxResults(4L)
					.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
					.setKey(Config.getInstance().getString("youtube_api_key"))
					.execute()
					.getItems();
			
			if(!results.isEmpty()) {
				String videoId = results.get(0).getId().getVideoId();
				
				
				return "https://www.youtube.com/watch?v=" + videoId;
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void handle(CommandContext ctx) {
		PlayerManager manager = PlayerManager.getInstance();
		GuildMusicManager musicManager = manager.getGuildMusicManager(ctx.getGuild());
		
		if (ctx.getArgs().isEmpty()) {
			if(musicManager.player.isPaused() && musicManager.player.getPlayingTrack() != null) {
				musicManager.player.setPaused(false);
				ctx.getChannel().sendMessage("Resuming").queue();
				return;
			}
		ctx.getChannel().sendMessage("No valid Url Entered").queue();
		return;

		}
	
		String link = "";
		
		if(!isUrl(ctx.getArgs().get(0))) {
			
			for (String i : ctx.getArgs()) {
				link += i + " ";
			}
			
			String ytSearched = searchYoutube(link);
			
			if(ytSearched == null) {
				ctx.getChannel().sendMessage("Youtube returned no results").queue();
			}
			
			link = ytSearched;
			manager.loadAndPlay(ctx.getChannel(), link);
		}

		if(ctx.getArgs().get(0).contains("spotify")){
			LinkConverter linkConverter = LinkConverter.getInstance();
			try {
				ArrayList<String> tracks = linkConverter.convert(ctx.getArgs().get(0));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (SpotifyWebApiException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


		}
		
		else {
			manager.loadAndPlay(ctx.getChannel(), ctx.getArgs().get(0));
		}
		
	}
	
	private boolean isUrl(String input) {
		try {
			new URL(input);
			return true;
		}
		catch (MalformedURLException e){
			return false;
		}
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "play";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "plays a track with a given url or adds it to the queue";
	}

}
