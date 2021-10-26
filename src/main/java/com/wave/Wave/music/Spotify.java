package com.wave.Wave.music;

import com.wave.Wave.config.Config;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

public class Spotify {
	
	private static SpotifyApi instance;
	
	public Spotify() throws ParseException, SpotifyWebApiException, IOException {
		instance = new SpotifyApi.Builder()
				  .setClientId(Config.getInstance().getString("spotify_client_id"))
				  .setClientSecret(Config.getInstance().getString("spotify_client_secret"))
				  .build();
		
		ClientCredentialsRequest.Builder request = new ClientCredentialsRequest.Builder(instance.getClientId(), instance.getClientSecret());
		ClientCredentials creds = request.grant_type("client_credentials").build().execute();
		instance.setAccessToken(creds.getAccessToken());
	}
	
	public static SpotifyApi getInstance() {
		return instance;
	}
}
