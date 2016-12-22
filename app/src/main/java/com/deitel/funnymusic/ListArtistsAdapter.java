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


public class ListArtistsAdapter extends ArrayAdapter<Artist> {

    private ArrayList<Artist> listArtists;
    private int resourceID;
    private Context context;

    public ListArtistsAdapter(Context context, int resource, ArrayList<Artist> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.listArtists = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, resourceID, null);

        TextView tvArtistTitle, tvNumberOfTracks;
        tvArtistTitle = (TextView)convertView.findViewById(R.id.tvAlbumTitle);
        tvNumberOfTracks = (TextView)convertView.findViewById(R.id.tvTotalSongsOfArtist);

        String artistTitle = listArtists.get(position).getTitle();

        tvArtistTitle.setText(artistTitle);
        tvNumberOfTracks.setText(listArtists.get(position).getNumberOfTracks());

        return convertView;
    }
}