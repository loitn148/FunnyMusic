package com.deitel.funnymusic;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by NgocLoi on 12/6/2016.
 */

public class ListArtistsActivity extends AppCompatActivity {
    private ArrayList<Artist> listArtists = new ArrayList<Artist>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_artists_layout);

        listArtists.addAll(Util.getListArtists(this));
        ListView lvListArtists = (ListView) findViewById(R.id.lvListArtists);
        ListArtistsAdapter adapterArt = new ListArtistsAdapter(this, R.layout.list_artists_items_layout, listArtists);
        lvListArtists.setAdapter(adapterArt);

       /* lvListSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(ListAlbumsActivity.this, PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
            }
        });
        */
    }
}
