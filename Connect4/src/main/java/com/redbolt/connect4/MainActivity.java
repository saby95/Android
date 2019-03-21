package com.redbolt.connect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView easy_button;
    private ImageView medium_button;
    private ImageView hard_button;
    private ImageView start_button;
    private ImageView about_button;
    private long backPressedTime;
    private TextView version_text;
    private TextView dev_text;
    private static String TAG = "com.redbolt.connect4";

    public void getDifficulty(View view){
        start_button.setVisibility(View.GONE);
        about_button.setVisibility(View.GONE);

        easy_button.setVisibility(View.VISIBLE);
        medium_button.setVisibility(View.VISIBLE);
        hard_button.setVisibility(View.VISIBLE);


    }

    public void startgame(View view){
        ImageView tappedView = (ImageView) view;
        String difficulty = tappedView.getTag().toString();
        Log.d(TAG,difficulty);

        Intent gameIntent = new Intent(this, gameActivity.class);
        gameIntent.putExtra("difficulty",difficulty);
        startActivity(gameIntent);

        start_button.setVisibility(View.VISIBLE);
        about_button.setVisibility(View.VISIBLE);

        easy_button.setVisibility(View.GONE);
        medium_button.setVisibility(View.GONE);
        hard_button.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easy_button = (ImageView) findViewById(R.id.easy_button);
        medium_button = (ImageView) findViewById(R.id.medium_button);
        hard_button = (ImageView) findViewById(R.id.hard_button);
        start_button = (ImageView) findViewById(R.id.start_button);
        about_button = (ImageView) findViewById(R.id.about_button);
        version_text = (TextView) findViewById(R.id.version_text);
        dev_text = (TextView) findViewById(R.id.dev_text);

        start_button.setVisibility(View.VISIBLE);
        about_button.setVisibility(View.VISIBLE);

        easy_button.setVisibility(View.GONE);
        medium_button.setVisibility(View.GONE);
        hard_button.setVisibility(View.GONE);
        version_text.setVisibility(View.GONE);
        dev_text.setVisibility(View.GONE);
    }

    public void getAbout(View view){
        start_button.setVisibility(View.GONE);
        about_button.setVisibility(View.GONE);

        version_text.setVisibility(View.VISIBLE);
        dev_text.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (start_button.getVisibility() == View.GONE){

            start_button.setVisibility(View.VISIBLE);
            about_button.setVisibility(View.VISIBLE);

            easy_button.setVisibility(View.GONE);
            medium_button.setVisibility(View.GONE);
            hard_button.setVisibility(View.GONE);
            version_text.setVisibility(View.GONE);
            dev_text.setVisibility(View.GONE);
        }

        else {
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {
                backPressedTime = t;
                Toast.makeText(this, "Press back again to logout",
                        Toast.LENGTH_SHORT).show();
            } else {
                super.onBackPressed();
            }
        }
    }
}
