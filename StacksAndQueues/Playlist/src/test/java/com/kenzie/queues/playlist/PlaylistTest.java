package com.kenzie.queues.playlist;

import com.kenzie.queues.playlist.models.Song;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class PlaylistTest {
    @Test
    public void getNextSong_emptyPlaylist_returnsNull() {
        //GIVEN
        Playlist playlist = new Playlist();

        //WHEN
        Song song = playlist.getNextSong();

        //THEN
        assertNull(song, "Expected getNextSong() to return null for empty Playlist, but returned: " + song);
    }

    @Test
    public void addSong_nonEmptyPlaylist_songAddedToEnd() {
        //GIVEN
        Playlist playlist = new Playlist();
        playlist.addSong(new Song("someSong", "someArtist"));

        //WHEN
        playlist.addSong(new Song("anotherSong", "anotherArtist"));

        //THEN
        Song song = playlist.getNextSong();
        song = playlist.getNextSong();
        assertEquals("anotherSong", song.getSongName(), "The playlist did not return the second song " +
                "that was added correctly.");
    }

    @Test
    public void addSong_emptyPlaylist_songFirstInPlaylist() {
        //GIVEN
        Playlist playlist = new Playlist();

        //WHEN
        playlist.addSong(new Song("someSong", "someArtist"));

        //THEN
        Song firstSong = playlist.getNextSong();
        assertEquals("someSong", firstSong.getSongName(), "The playlist did not return the first song " +
                "that was added correctly.");

    }

    @Test
    public void getNextSong_secondSong_returnsCorrectSong() {
        //GIVEN
        Playlist playlist = new Playlist();

        //WHEN
        playlist.addSong(new Song("someSong", "someArtist"));

        //THEN
        playlist.addSong(new Song("anotherSong", "anotherArtist"));
        Song song = playlist.getNextSong();
        song = playlist.getNextSong();
        assertEquals("anotherSong", song.getSongName(), "The playlist did not return the second " +
                "song that was added correctly.");
    }

    @Test
    public void getNextSong_oneSongPlaylist_returnsCorrectSong() {
        //GIVEN
        String songName = "someSong";
        Playlist playlist = new Playlist();
        playlist.addSong(new Song(songName, "someArtist"));

        //WHEN
        Song firstSong = playlist.getNextSong();

        //THEN
        assertEquals(songName, firstSong.getSongName(), "The playlist did not return the first song " +
                "that was added correctly.");

    }

    @Test
    public void getNextSong_twoSongPlaylist_returnsCorrectSong() {
        //GIVEN
        String firstSongName = "someSong";
        Playlist playlist = new Playlist();
        playlist.addSong(new Song(firstSongName, "someArtist"));
        playlist.addSong(new Song("anotherSong", "anotherArtist"));

        //WHEN
        Song firstSong = playlist.getNextSong();

        //THEN
        assertEquals(firstSongName, firstSong.getSongName(), "The playlist did not return the first song " +
                "that was added from a passed in playlist.");
    }
}
