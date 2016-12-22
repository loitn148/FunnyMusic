package com.deitel.funnymusic;

/**
 * Created by NgocLoi on 12/2/2016.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ListSongsAdapter extends ArrayAdapter<Song> {

    private List<Song> listSongs;
    private int resourceID;
    private Context context;

    public ListSongsAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceID = resource;
        this.listSongs = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = View.inflate(context, resourceID, null);

        TextView tvSongTitle, tvDuration, tvArtist;
        tvSongTitle = (TextView)convertView.findViewById(R.id.tvSongTitle);
        tvDuration = (TextView)convertView.findViewById(R.id.tvDuration);
        tvArtist = (TextView)convertView.findViewById(R.id.tvArtist);

        String songTitle = listSongs.get(position).getTitle();

        if(songTitle.length() > 30){
            songTitle = songTitle.substring(0, 30) + "...";
        }

        String songTimes = Util.getTextFormat(listSongs.get(position).getDuration());
        tvDuration.setText(songTimes);
        tvSongTitle.setText(songTitle);
        tvArtist.setText(listSongs.get(position).getArtist());

        return convertView;
    }
}
