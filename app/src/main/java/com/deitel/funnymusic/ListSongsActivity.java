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


        listSongs.addAll(Util.getListSongs(this));
        ListView lvListSongs = (ListView)findViewById(R.id.lvListSongs);
        ListSongsAdapter adapter = new ListSongsAdapter(this,R.layout.list_songs_items_layout, listSongs);
        lvListSongs.setAdapter(adapter);

        lvListSongs.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(ListSongsActivity.this, PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
            }
        });
    }


    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    } */
}
