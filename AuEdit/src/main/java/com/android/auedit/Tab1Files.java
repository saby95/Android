package com.android.auedit;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import android.os.Handler;

public class Tab1Files extends Fragment {

    public String TAG = "com.android.auedit";
    private ProgressBar progressBar;

    public MySongService SongService;
    private boolean isBound = false;

    private ArrayList<Songs> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1_files,container,false);

        Log.d(TAG,"rootView inflated");
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        Log.d(TAG,"Progressbar set");

        arrayList = new ArrayList<Songs>();

        getMusic();

        ListView myListView = (ListView)rootView.findViewById(R.id.filelistdisplay);
        ListAdapter newAdapter = new CustomAdapter(getActivity(), arrayList);
        Log.d(TAG,"List Creation started");
        myListView.setAdapter(newAdapter);
        progressBar.setVisibility(View.GONE);


        return rootView;
    }

    public void getMusic(){
        Log.d(TAG, "Song Creation Started");
        Context applicationContext = SelectActivity.getContextOfApplication();
        ContentResolver contentResolver = applicationContext.getContentResolver();
        Uri songuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songcursor = contentResolver.query(songuri, null, null, null, null);

        if (songcursor != null && songcursor.moveToFirst()){
            int songid = songcursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songtitle = songcursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            do{
                long currentId = songcursor.getLong(songid);
                String currentTitle = songcursor.getString(songtitle);
                arrayList.add(new Songs(currentId,currentTitle));
            }while (songcursor.moveToNext());
        }
        Log.d(TAG, "Song Creation complete");
    }



    private ServiceConnection songConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MySongService.LocalBinder newBind = (MySongService.LocalBinder)iBinder;
            SongService = newBind.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };

}
