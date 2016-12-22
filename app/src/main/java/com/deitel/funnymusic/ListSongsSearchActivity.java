package com.deitel.funnymusic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NgocLoi on 12/8/2016.
 */

public class ListSongsSearchActivity extends AppCompatActivity {

    private ArrayList<Song> listSongsSearch = new ArrayList<Song>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_songs_search_layout);

        listSongsSearch.addAll(Util.LIST_SONGS_SEARCH);
        TextView tvListSongsSearch = (TextView) findViewById(R.id.tvListSongsSearch);
        tvListSongsSearch.setText(Util.VOICE_RECOGNIZE);

        ListView lvListSongsSearch = (ListView)findViewById(R.id.lvListSongsSearch);
        ListSongsSearchAdapter adapter = new ListSongsSearchAdapter(this, R.layout.list_songs_search_items_layout, listSongsSearch);
        lvListSongsSearch.setAdapter(adapter);

        lvListSongsSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(ListSongsSearchActivity.this, PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
                Util.LIST_SONGS_PLAY = listSongsSearch;
            }
        });
    }

    public void backAction(View v){
        Intent i = new Intent(ListSongsSearchActivity.this, PlaySongActivity.class);
        startActivityForResult(i, Util.OPEN_ACTIVITY_PLAY_SONG);
    }
}
