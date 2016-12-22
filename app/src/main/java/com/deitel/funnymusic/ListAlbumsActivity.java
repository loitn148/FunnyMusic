package com.deitel.funnymusic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by NgocLoi on 12/6/2016.
 */

public class ListAlbumsActivity extends AppCompatActivity {

    private ArrayList<Album> listAlbums = new ArrayList<Album>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_albums_layout);

        listAlbums.addAll(Util.getListAlbums(this));
        ListView lvListAlbums = (ListView) findViewById(R.id.lvListAlbums);
        ListAlbumsAdapter adapterAlb = new ListAlbumsAdapter(this, R.layout.list_albums_items_layout, listAlbums);
        lvListAlbums.setAdapter(adapterAlb);

        lvListAlbums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentViewSongs = new Intent(getApplicationContext(), ListSongsOfAlbumActivity.class);
                intentViewSongs.putExtra(Util.KEY_AlBUM_POSITION, position);
                startActivityForResult(intentViewSongs, 100);
            }
        });
    }
}
