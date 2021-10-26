package com.wave.Wave.music;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.wave.Wave.config.Config;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class LinkConverter {

	
	private SpotifyApi spotifyApi;
	private YouTube youtube;
	private static LinkConverter instance;
	private String id;
	private String type;
	private String specialtype;
	
	public LinkConverter() {
		try {
			initSpotify();
			initYoutube();
		} catch (ParseException | SpotifyWebApiException | IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		instance = this;
	}
	
	private void initYoutube() throws GeneralSecurityException, IOException {
		this.youtube = new YouTube.Builder(
				GoogleNetHttpTransport.newTrustedTransport(),
				JacksonFactory.getDefaultInstance(),
				null)
				.setApplicationName("BitchesBangerBot")
				.build();
	}
	
	private void initSpotify() throws ParseException, SpotifyWebApiException, IOException {
		this.spotifyApi = new SpotifyApi.Builder()
				  .setClientId(Config.getInstance().getString("spotify_client_id"))
				  .setClientSecret(Config.getInstance().getString("spotify_client_secret"))
				  .build();
		
		ClientCredentialsRequest.Builder request = new ClientCredentialsRequest.Builder(spotifyApi.getClientId(), spotifyApi.getClientSecret());
		ClientCredentials creds = request.grant_type("client_credentials").build().execute();
		spotifyApi.setAccessToken(creds.getAccessToken());
	}
	
	//This method assumes that the link is already a valid URL
	public ArrayList<String> convert(String link) throws ParseException, SpotifyWebApiException, IOException {
		String[] firstSplit = link.split("/");
		String[] secondSplit;
		
		//NORMAL PLAYLISTS DONT WORK ANYMORE
		if(firstSplit.length > 5) {
			secondSplit = firstSplit[6].split("\\?");
			this.type = firstSplit[5];
		}
		else {
			secondSplit = firstSplit[4].split("\\?");
			this.type = firstSplit[3];
		}
		this.id = secondSplit[0];
		ArrayList<String> listOfTracks = new ArrayList<>();
		
		if(type.contentEquals("track")) {
//			return searchYoutube(getArtistAndName());
			listOfTracks.add(getArtistAndName(id));
			return listOfTracks;
		}
		
		if(type.contentEquals("playlist")) {
			GetPlaylistRequest playlistRequest = spotifyApi.getPlaylist(id).build();
			Playlist playlist = playlistRequest.execute();
			Paging<PlaylistTrack> playlistPaging = playlist.getTracks();
			PlaylistTrack[] playlistTracks = playlistPaging.getItems();
			
			for (PlaylistTrack i : playlistTracks) {
				Track track = (Track) i.getTrack();
				String trackID = track.getId();
				listOfTracks.add(getArtistAndName(trackID));
			}
			
			return listOfTracks;
		}
		
		return null;
	}
	
//	@Nullable
//	private String searchYoutube (String search) {
//		try {
//			List<SearchResult> results = youtube.search()
//					.list("id,snippet")
//					.setQ(search)
//					.setMaxResults(2L)
//					.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
//					.setKey(Config.getInstance().getString("youtube_api_key"))
//					.execute()
//					.getItems();
//			
//			if(!results.isEmpty()) {
//				String videoId = results.get(0).getId().getVideoId();
//				
//				return "https://www.youtube.com/watch?v=" + videoId;
//			}
//			
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return null;
//	}
	
	private String getArtistAndName(String trackID) throws ParseException, SpotifyWebApiException, IOException {
		String artistNameAndTrackName = "";
		GetTrackRequest trackRequest = spotifyApi.getTrack(trackID).build();
		
		Track track = trackRequest.execute();
		artistNameAndTrackName = track.getName() + " - ";
		
		ArtistSimplified[] artists = track.getArtists();
		for(ArtistSimplified i : artists) {
			artistNameAndTrackName += i.getName() + " ";
		}
	
		return artistNameAndTrackName;
	}
	
	
	public static LinkConverter getInstance() {
		return instance;
	}
}
