package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/6/2016.
 */

import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.net.Uri;
import android.provider.MediaStore;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Created by NgocLoi on 12/2/2016.
 */

public class Util {

    public static final MediaPlayer mediaPlayer = new MediaPlayer();
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
    public static final  int FORWARD_TIME = 5000;
    public static final  int BACKWARD_TIME = 5000;
    public static final String KEY_SONG_POSITION = "songPosition";
    public static final String KEY_AlBUM_POSITION = "albumPosition";
    public static final String KEY_ARTIST_POSITION = "artistPosition";
    public static int SONG_POSITION;
    public static int IS_REPEAT = 0;
    public static int IS_SHUFFLE = 0;
    public static final int OPEN_ACTIVITY_PLAY_SONG = 1;
    public static final int OPEN_ACTIVITY_LIST_SONGS_SEARCH = 2;
    public static final int OPEN_ACTIVITY_LIST_SONGS_OF_ALBUM = 3;
    public static final long DELAY_MILLISECONDS = 200;
    public static ArrayList<Song> LIST_SONGS_PLAY = new ArrayList<Song>();
    public static ArrayList<Song> LIST_SONGS_SEARCH = new ArrayList<Song>();
    public static ArrayList<Song> LIST_SONGS = new ArrayList<Song>();
    public static ArrayList<Album> LIST_ALBUMS = new ArrayList<Album>();

    public static String VOICE_RECOGNIZE;

    public static String KEY_PLAY = "Play";
    public static String KEY_PAUSE = "Pause";
    public static String KEY_NEXT = "Next";
    public static String KEY_PREVIOUS = "Previous";
    public static String KEY_REPEAT = "Repeat";
    public static String KEY_SHUFFLE = "Shuffle";
    public static String KEY_PLAYLIST = "Playlist";


    //Chuyen doi thoi gian tu millisecond ra min:sec
    public static String getTextFormat(long timeInMilliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) timeInMilliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) timeInMilliseconds)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeInMilliseconds));
        String min = minutes < 10 ? ("0" + minutes) : String.valueOf(minutes);
        String sec = seconds < 10 ? ("0" + seconds) : String.valueOf(seconds);
        return min + ":" + sec;
    }

    //Lay danh sach Songs
    public static ArrayList<Song> getListSongs(Context context) {
        ArrayList<Song> listSongs = new ArrayList<Song>();
        Uri uri;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] data = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        Cursor c = context.getContentResolver().query(uri, data, MediaStore.Audio.Media.IS_MUSIC + "=1", null,
                MediaStore.Audio.Media.TITLE + " ASC");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String id, name, title, album, artist, path;
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            duration = (int) (c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            Song song = new Song(id, name, title, album, artist, path, duration);
            listSongs.add(song);

        }
        return listSongs;
    }

    //Lấy danh sách Albums
    public static ArrayList<Album> getListAlbums(Context context){

        ArrayList<Album> listAlbums = new ArrayList<Album>();
        Uri uri;
        uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] data = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS};

        Cursor c = context.getContentResolver().query(uri, data, null, null,
                MediaStore.Audio.Albums.ALBUM + " ASC");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String id, title;
            int numberOfSongs;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
            numberOfSongs = (int) (c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS)));
            Album album = new Album(id, title, numberOfSongs);
            listAlbums.add(album);
        }
        return listAlbums;
    }

    //Thêm bài hát vào album
    /*public static ArrayList<Album> addSongsToAlbum(ArrayList<Album> listAlbums, ArrayList<Song> listSongs){
        int position = 0;
        for(Album album : listAlbums){
            for(Song song: listSongs){
                if(album.getTitle() == song.getAlbum()){
                    listAlbums.get(position).addSongIntoList(song);
                }
            }
            position += 1;
        }
        return listAlbums;
    }*/

    //Lấy danh sách Artist
    public static ArrayList<Artist> getListArtists(Context context){

        ArrayList<Artist> listArtists = new ArrayList<Artist>();
        Uri uri;
        uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        String[] data = {MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS};

        Cursor c = context.getContentResolver().query(uri, data, MediaStore.Audio.Artists.ARTIST, null,
                MediaStore.Audio.Artists.ARTIST + " ASC");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String id, title;
            int numberOfTracks;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
            numberOfTracks = (int) (c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS)));
            Artist artist = new Artist(id, title, numberOfTracks);
            listArtists.add(artist);
        }
        return listArtists;
    }

    //Chuyển chuỗi có dấu thành không dấu
    public static String removeAccent(String string){
        String temp = Normalizer.normalize(string, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    }
}


