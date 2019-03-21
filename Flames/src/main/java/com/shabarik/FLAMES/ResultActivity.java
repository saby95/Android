package com.shabarik.FLAMES;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;

import com.shabarik.FLAMES.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        RelativeLayout ResultLayout = (RelativeLayout)findViewById(R.id.ResultLayout);
        //setContentView(ResultLayout);

        TextView ResultText = (TextView)findViewById(R.id.ResultText);
        ImageView ResultImage = (ImageView)findViewById(R.id.ResultImage);

        Bundle recievedData = getIntent().getExtras();
        if (recievedData == null)
            return;
        int Match = recievedData.getInt("Match");

        switch (Match){
            case 1 :
                ResultImage.setImageResource(R.drawable.friends);
                ResultText.setText("You two have a good chance of being FRIENDS!!!");
                break;
            case 2:
                ResultImage.setImageResource(R.drawable.love);
                ResultText.setText("You people could fall in LOVE!!!");
                break;
            case 3:
                ResultImage.setImageResource(R.drawable.affection);
                ResultText.setText("You people are really AFFECTIONate towards each other.");
                break;
            case 4:
                ResultImage.setImageResource(R.drawable.marriage);
                ResultText.setText("You two could end up getting MARRIED!!!");
                break;
            case 5:
                ResultImage.setImageResource(R.drawable.enemies);
                ResultText.setText("Both of you are ENEMIES");
                break;
            case 6:
                ResultImage.setImageResource(R.drawable.sister);
                ResultText.setText("You people have a SIBLING sort of bonding.");
                break;
        }



    }

    public void backbutton(View view){
        finish();
    }
}
