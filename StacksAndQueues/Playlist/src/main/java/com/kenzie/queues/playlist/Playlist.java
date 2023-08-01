package com.kenzie.queues.playlist;

import com.kenzie.queues.playlist.models.Song;

public class Playlist {

    /**
     * Constructor.
     */
    public Playlist() {

    }

    /**
     * Provides the next song to be played from this playlist.
     * Once a song is played it will not be available to be played again.
     * @return The next Song in the playlist, or null if the playlist is empty.
     */
    public Song getNextSong() {
        // TODO: implement this
        return new Song("", "");
    }

    /**
     * Adds a Song to the end of the playlist.
     * @param song the song to be added to the playlist.
     */
    public void addSong(Song song) {
        // TODO: implement this
    }

}
