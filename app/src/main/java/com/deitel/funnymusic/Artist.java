package com.deitel.funnymusic;

import java.util.ArrayList;

/**
 * Created by NgocLoi on 12/6/2016.
 */

public class Artist {
    private String id;
    private String title;
    private int numberOfTracks;
    private ArrayList<Song> listSongsOfArtist;

    public Artist(String id, String title, int numberOfTracks){

        this.id = id;
        this.title = title;
        this.numberOfTracks = numberOfTracks;
        this.listSongsOfArtist = new ArrayList<Song>();
    }

    public String getId(){return id;}

    public void setId(String id){ this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTile(String title) {
        this.title = title;
    }

    public int getNumberOfTracks(){ return numberOfTracks; }

    public void setNumberOfTracks(int numberOfTracks){ this.numberOfTracks = numberOfTracks; }

    public ArrayList<Song> getListSongsOfArtist(){return listSongsOfArtist;}

    public void setListSongsOfArtist(ArrayList<Song> listSongsOfArtist){this.listSongsOfArtist = listSongsOfArtist;}
}
