package com.deitel.funnymusic;

import java.util.ArrayList;

/**
 * Created by NgocLoi on 12/6/2016.
 */

public class Album {

    private String id;
    private String title;
    private int numberOfSongs;
    private ArrayList<Song> listSongsOfAlbum;
    public Album(String id, String title, int numberOfSongs){
        this.id = id;
        this.title = title;
        this.numberOfSongs = numberOfSongs;
        this.listSongsOfAlbum = new ArrayList<Song>();
    }

    public String getId(){return id;}

    public void setId(String id){ this.id = id; }

    public int getNumberOfSongs(){ return  numberOfSongs; }

    public void setNumberOfSongs(int numberOfSongs){ this.numberOfSongs = numberOfSongs; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Song> getListSongsOfAlbum(){ return this.listSongsOfAlbum; }

    public void setListSongsOfAlbum(ArrayList<Song> listSongsOfAlbum){this.listSongsOfAlbum = listSongsOfAlbum;}

    public void addSongIntoList(Song song){listSongsOfAlbum.add(song);}
}
