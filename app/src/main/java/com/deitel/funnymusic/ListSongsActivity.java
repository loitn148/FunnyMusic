package com.deitel.funnymusic;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListSongsActivity extends AppCompatActivity {

    private ArrayList<Song> listSongs = new ArrayList<Song>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_songs_layout);

        listSongs.addAll(Util.LIST_SONGS);
        final ListView lvListSongs = (ListView)findViewById(R.id.lvListSongs);
        final ListSongsAdapter adapter = new ListSongsAdapter(this, R.layout.list_songs_items_layout, listSongs);
        lvListSongs.setAdapter(adapter);

        lvListSongs.setLongClickable(true);
        lvListSongs.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                lvListSongs.setAdapter(adapter);
                return true;
            }
        });

        lvListSongs.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(ListSongsActivity.this, PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
                Util.LIST_SONGS_PLAY = listSongs;
            }
        });
    }
}
