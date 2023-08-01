package com.kenzie.queues.playlist.models;

public final class Song {
    private final String songName;
    private final String artist;

    /**
     * Song POJO class to hold song name and artist.
     * @param songName name of song
     * @param artist name of artist
     */
    public Song(String songName, String artist) {
        this.songName = songName;
        this.artist = artist;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    /**
     * creates a string to represent the state of the object.
     * @return string representing the song
     */
    public String toString() {
        return this.songName + " by " + this.artist;
    }

}
