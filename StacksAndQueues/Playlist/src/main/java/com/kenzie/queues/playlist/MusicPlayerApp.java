package com.kenzie.queues.playlist;

import com.kenzie.queues.playlist.models.Song;

public class MusicPlayerApp {

    /**
     * main method to interact with the Playlist Class.
     * @param args passed in from the command line. Not used.
     */
    public static void main(String[] args) {
        // create an instance of the playlist
        Playlist playlist = new Playlist();

        // add some songs
        playlist.addSong(new Song("Yesterday", "The Beatles"));
        playlist.addSong(new Song("Maybellene", "Chuck Berry"));
        playlist.addSong(new Song("Charango", "Morcheeba"));

        // make sure they come out in the same order that you added them.
        for (int i = 0; i < 3; i++) {
            Song song = playlist.getNextSong();
            System.out.println(song.toString());
        }

        // What happens if the queue is empty?
        Song song = playlist.getNextSong();
        System.out.println(song);
    }
}
