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


public class ListAlbumsAdapter extends ArrayAdapter<Album> {

    private ArrayList<Album> listAlbums;
    private int resourceID;
    private Context context;

    public ListAlbumsAdapter(Context context, int resource, ArrayList<Album> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.listAlbums = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, resourceID, null);

        TextView tvAlbumTitle, tvNumberOfSongs;
        tvAlbumTitle = (TextView)convertView.findViewById(R.id.tvAlbumTitle);
        tvNumberOfSongs = (TextView)convertView.findViewById(R.id.tvTotalSongsOfAlbum);

        String albumTitle = listAlbums.get(position).getTitle();

        tvAlbumTitle.setText(albumTitle);
        tvNumberOfSongs.setText(String.valueOf(listAlbums.get(position).getNumberOfSongs()) + " song");

        return convertView;
    }
}