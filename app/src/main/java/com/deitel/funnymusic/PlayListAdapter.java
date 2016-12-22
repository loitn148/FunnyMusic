package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/6/2016.
 */

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PlayListAdapter extends ArrayAdapter<Song> {

    private AnimationDrawable frameAnimation;
    private ArrayList<Song> listSongs;
    private int resourceID;
    private Context context;

    public PlayListAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.listSongs = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, resourceID, null);

        TextView tvSongTitle, tvDuration, tvArtist;
        ImageView ivAnimNowPlaying;
        ivAnimNowPlaying = (ImageView)convertView.findViewById(R.id.ivAinmNowPlaying);
        tvSongTitle = (TextView)convertView.findViewById(R.id.tvSongTitle);
        tvArtist = (TextView)convertView.findViewById(R.id.tvArtist);
        String songTitle = listSongs.get(position).getTitle();
        tvSongTitle.setText(songTitle);
        tvArtist.setText(listSongs.get(position).getArtist());

        if(ivAnimNowPlaying != null && Util.SONG_POSITION == position){

            ivAnimNowPlaying.setVisibility(View.VISIBLE);

            frameAnimation = (AnimationDrawable) ivAnimNowPlaying.getDrawable();

            frameAnimation.setCallback(ivAnimNowPlaying);

            frameAnimation.setVisible(true, true);
            if(Util.mediaPlayer.isPlaying()){
                frameAnimation.start();
            }else{
                frameAnimation.stop();
            }
        }else{
            ivAnimNowPlaying.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}