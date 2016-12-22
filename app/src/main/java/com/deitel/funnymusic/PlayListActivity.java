package com.deitel.funnymusic;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class PlayListActivity extends AppCompatActivity {

    private ArrayList<Song> listSongs = new ArrayList<Song>();
    Handler handler;
    int songPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playlist_layout);

        handler = new Handler();
        songPosition = Util.SONG_POSITION;

        listSongs = Util.LIST_SONGS_PLAY;
        final ListView lvListSongs = (ListView)findViewById(R.id.lvListSongs);
        final PlayListAdapter adapter = new PlayListAdapter(this,R.layout.playlist_items_layout, listSongs);
        lvListSongs.setAdapter(adapter);

        lvListSongs.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(getApplicationContext(), PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
            }
        });
    }


}
