package com.android.auedit;

import android.app.Activity;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MySongService extends Service {

    private final IBinder songBinder = new LocalBinder();

    public ArrayList<Songs> arrayList;
    public String TAG = "com.android.auedit";

    public ProgressBar progressBar;

    public MySongService() {

    }

    public ListView viewINflater(ListView list, ProgressBar progressBar){

        Log.d(TAG, "Song Creation Started");

        arrayList = new ArrayList<Songs>();

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

        //ListView myListView = (ListView)((Activity)c).findViewById(R.id.filelistdisplay);
        ListView myListView = list;
        ListAdapter newAdapter = new CustomAdapter(getApplicationContext(), arrayList);
        Log.d(TAG,"List Creation started");
        myListView.setAdapter(newAdapter);
        //progressBar = (ProgressBar)((Activity)c).findViewById(R.id.progressBar);
        progressBar = progressBar;
        progressBar.setVisibility(View.GONE);

        return myListView;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return songBinder;
    }

    public class LocalBinder extends Binder{
        MySongService getService(){
            return MySongService.this;
        }
    }
}
