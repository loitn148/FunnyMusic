package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/6/2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class ListSongsSearchAdapter extends ArrayAdapter<Song> {

    private ArrayList<Song> listSongs;
    private int resourceID;
    private Context context;

    public ListSongsSearchAdapter(Context context, int resource, ArrayList<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.listSongs = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, resourceID, null);

        TextView tvSongTitle, tvDuration, tvArtist, tvAlbum;
        tvSongTitle = (TextView)convertView.findViewById(R.id.tvSongTitle);
        tvDuration = (TextView)convertView.findViewById(R.id.tvDuration);
        tvArtist = (TextView)convertView.findViewById(R.id.tvArtist);
        tvAlbum = (TextView)convertView.findViewById(R.id.tvAlbum);

        String songTitle = listSongs.get(position).getTitle();

        String songTimes = Util.getTextFormat(listSongs.get(position).getDuration());
        tvDuration.setText(songTimes);
        tvSongTitle.setText(songTitle);
        tvArtist.setText(listSongs.get(position).getArtist());
        tvAlbum.setText(listSongs.get(position).getAlbum());

        return convertView;
    }
}