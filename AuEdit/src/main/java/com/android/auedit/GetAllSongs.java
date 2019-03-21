package com.android.auedit;


import android.app.IntentService;
import android.content.Intent;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.util.Log;

import java.util.ArrayList;
import java.util.logging.Handler;

public class GetAllSongs extends IntentService {

    public ArrayList<Songs> arrayList;
    public String TAG = "com.android.auedit";
    public static final String onCompletion = "com.android.auedit.RESPONSE";

    private Handler myHandler;

    public GetAllSongs() {
        super("GetAllSongs");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        arrayList = new ArrayList<Songs>();
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

        Intent completeIntent = new Intent();
        completeIntent.setAction(onCompletion);
        completeIntent.addCategory(Intent.CATEGORY_DEFAULT);
        completeIntent.putExtra("Songs",arrayList);
        sendBroadcast(completeIntent);
    }
}
