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

public class ListSongsOfAlbumActivity extends AppCompatActivity{

    private ArrayList<Song> listSongsOfAlbum = new ArrayList<Song>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_songs_of_album_layout);

        int albumPosition = getIntent().getIntExtra(Util.KEY_AlBUM_POSITION, 0);

        for(Song song : Util.LIST_SONGS){
            if(song.getAlbum().equals(Util.LIST_ALBUMS.get(albumPosition).getTitle())){
                listSongsOfAlbum.add(song);
            }
        }
        //Util.LIST_ALBUMS.get(albumPosition).getListSongsOfAlbum()

        TextView albumTitle = (TextView) findViewById(R.id.tvAlbum);
        albumTitle.setText(Util.LIST_ALBUMS.get(albumPosition).getTitle());

        ListView lvListSongsOfAlbum = (ListView)findViewById(R.id.lvListSongsOfAlbum);
        ListSongsAdapter adapter = new ListSongsAdapter(this, R.layout.list_songs_items_layout, listSongsOfAlbum);
        lvListSongsOfAlbum.setAdapter(adapter);

        lvListSongsOfAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentPlaySong = new Intent(ListSongsOfAlbumActivity.this, PlaySongActivity.class);
                intentPlaySong.putExtra(Util.KEY_SONG_POSITION, position);
                startActivityForResult(intentPlaySong, Util.OPEN_ACTIVITY_PLAY_SONG);
                Util.LIST_SONGS_PLAY = listSongsOfAlbum;
            }
        });
    }

    public void backAction(View v){
        Intent i = new Intent(ListSongsOfAlbumActivity.this, StartActivity.class);
        startActivityForResult(i, 100);
    }
}
