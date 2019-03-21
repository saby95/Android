package com.android.auedit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


class CustomAdapter extends ArrayAdapter<Songs> {


    public CustomAdapter(Context context, ArrayList<Songs> songs) {
        super(context, R.layout.fragment_file_list, songs);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater myInflater = LayoutInflater.from(getContext());
        View customView = myInflater.inflate(R.layout.fragment_file_list, parent, false);

        Songs singlesong = getItem(position);
        TextView songname = (TextView)customView.findViewById(R.id.songname);
        ImageView fragimage = (ImageView)customView.findViewById(R.id.fragimage);

        songname.setText(singlesong.getSongTitle());
        fragimage.setImageResource(R.drawable.albumcover);

        return customView;
    }
}
