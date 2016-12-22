package com.deitel.funnymusic;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by NgocLoi on 12/2/2016.
 */

public class Util {

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
    public static  final  int FORWARD_TIME = 5000;
    public static  final  int BACKWARD_TIME = 5000;
    public static final String KEY_SONG_POSITION = "songPosition";
    public static final int OPEN_ACTIVITY_PLAY_SONG = 1;
    public static  final long DELAY_MILLISECONDS = 200;


    //Chuyen doi thoi gian tu millisecond ra min:sec
    public static String getTextFormat(long timeInMilliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) timeInMilliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds((long) timeInMilliseconds)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeInMilliseconds));
        String min = minutes < 10 ? ("0" + minutes) : String.valueOf(minutes);
        String sec = seconds < 10 ? ("0" + seconds) : String.valueOf(seconds);
        return min + ":" + sec;
    }


    //Lay danh sach nhac tu bo nho
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
}
