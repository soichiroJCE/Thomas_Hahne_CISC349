package com.bignerdranch.android.dynamiclistview;

import org.json.JSONException;
import org.json.JSONObject;

public class HolidaySongs {
    private String image;
    private String name;
    private String artist;
    private double danceability;
    private int durationMs;
    private String playlistImage;

    public HolidaySongs(JSONObject jsonData) throws JSONException {
        this.setImage(jsonData.getString("album_img"));
        this.setName(jsonData.getString("album_name"));
        this.setArtist(jsonData.getString("artist_name"));
        this.setDanceability(jsonData.getDouble("danceability"));
        this.setDurationMs(jsonData.getInt("duration_ms"));
        this.setPlaylistImage(jsonData.getString("playlist_img"));
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public double getDanceability() {
        return danceability;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(int durationMs) {
        this.durationMs = durationMs;
    }

    public void setPlaylistImage(String playlistImage) {
        this.playlistImage = playlistImage;
    }
}