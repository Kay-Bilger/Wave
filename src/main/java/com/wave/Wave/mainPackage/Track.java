package com.wave.Wave.mainPackage;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Tracks")
public class Track {

    @Id
    private String id;

    private String youtubeLink;
    private String youtubeVideoName;
    private String spotifyLink;
    private String name;
    private String artist;



    public Track(String name) {
        this.name = name;
    }

    public Track(String id, String youtubeLink, String youtubeVideoName, String spotifyLink, String name, String artist){
        this.id = id;
        this.youtubeLink = youtubeLink;
        this.youtubeVideoName = youtubeVideoName;
        this.spotifyLink = spotifyLink;
        this.name = name;
        this.artist = artist;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }

    public String getYoutubeVideoName() {
        return youtubeVideoName;
    }

    public void setYoutubeVideoName(String youtubeVideoName) {
        this.youtubeVideoName = youtubeVideoName;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }

    public void setSpotifyLink(String spotifyLink) {
        this.spotifyLink = spotifyLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
