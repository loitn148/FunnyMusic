package com.deitel.funnymusic;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;


import android.os.Bundle;


import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.io.IOException;

public class StartActivity extends TabActivity{

    TextView tvSongTitle, tvArtist;
    ImageButton btnPlay, btnNext, btnPrevious;
    int songPosition;
    LinearLayout statusPlay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        tvSongTitle = (TextView) findViewById(R.id.tvSongTitle);
        tvArtist = (TextView) findViewById(R.id.tvArtist);

        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnPrevious = (ImageButton) findViewById(R.id.btnPrevious);

        TabHost tabHost = getTabHost();
        // Tab for Songs
        TabHost.TabSpec songsSpec = tabHost.newTabSpec("Songs");
        songsSpec.setIndicator("Songs", ResourcesCompat.getDrawable(getResources(), R.drawable.icon_songs, null));
        Intent songsIntent = new Intent(this, ListSongsActivity.class);
        songsSpec.setContent(songsIntent);

        // Tab for Albums
        TabHost.TabSpec albumsSpec = tabHost.newTabSpec("Albums");
        albumsSpec.setIndicator("Albums", ResourcesCompat.getDrawable(getResources(), R.drawable.icon_albums, null));
        Intent albumsIntent = new Intent(this, ListAlbumsActivity.class);
        albumsSpec.setContent(albumsIntent);

        // Tab for Artists
        TabHost.TabSpec artistsSpec = tabHost.newTabSpec("Artists");
        artistsSpec.setIndicator("Artists", ResourcesCompat.getDrawable(getResources(), R.drawable.icon_artists, null));
        Intent artistsIntent = new Intent(this, ListArtistsActivity.class);
        artistsSpec.setContent(artistsIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(songsSpec);
        tabHost.addTab(albumsSpec);
        tabHost.addTab(artistsSpec);

        loadStatusApp();
    }


    public void savingStatusApp(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("Status App", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("isRepeat", Util.IS_REPEAT);
        editor.putInt("isShuffle", Util.IS_SHUFFLE);
        editor.putInt("songPosition", Util.SONG_POSITION);
        //editor.putInt("songProgress", Util.mediaPlayer.getCurrentPosition());
    }

    public void loadStatusApp(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("Status App", MODE_PRIVATE);
        if(sharedPreferences != null){
            Util.IS_REPEAT = sharedPreferences.getInt("isRepeat", 0);
            Util.IS_SHUFFLE = sharedPreferences.getInt("isShuffle", 0);
            Util.SONG_POSITION = sharedPreferences.getInt("songPosition", 0);
            songPosition = Util.SONG_POSITION;
            //int songProgress = sharedPreferences.getInt("songProgress", 0);
            //Util.mediaPlayer.seekTo(songProgress);
        }else{
            songPosition = 0;
            Util.IS_REPEAT = 0;
            Util.IS_SHUFFLE = 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        savingStatusApp();
    }
}
