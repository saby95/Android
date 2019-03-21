package com.redbolt.connect4;


import android.content.DialogInterface;
import android.os.Handler;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class gameActivity extends AppCompatActivity {

    private static String TAG = "com.redbolt.connect4";
    private static int PlayerWins = 0;
    private static int SystemWins = 0;
    private TextView playerDisp;
    private TextView systemDisp;
    private AIEasy aiEasy;
    private AIMedium aiMedium;
    private AIHard aiHard;
    boolean gameplayable;
    int[] gamestate;
    int tappedtag;
    int inserttag;
    ArrayList<int[]> verticalWinning;
    ArrayList<int[]> horizontalWinning;
    ArrayList<int[]> diagonalWinning;
    HashMap<Integer,ArrayList<int[]>> winningmap;
    private String difficulty;
    private int[] winning_coins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        difficulty = getIntent().getStringExtra("difficulty");
        Log.d(TAG,difficulty.toString());

        playerDisp = (TextView)findViewById(R.id.PlayerCount);
        systemDisp = (TextView)findViewById(R.id.SystemCount);

        playerDisp.setText(Integer.toString(PlayerWins));
        systemDisp.setText(Integer.toString(SystemWins));


        gamestate = new int[42];
        for(int i=0;i<42;i++){
            gamestate[i]=2;
        }

        winningmap = new HashMap<>();
        findWinnings();
        gameplayable =true;

        aiEasy = new AIEasy();
        aiMedium = new AIMedium();
        aiHard = new AIHard();
    }


    public void insertcoin(View view){

        if (!gameplayable) {
            return;
        }
        gameplayable=false;
        ImageView tappedimage = (ImageView) view;
        tappedtag = Integer.parseInt(tappedimage.getTag().toString());
        inserttag = tappedtag;
        while (tappedtag > 6) {
            if (gamestate[tappedtag - 7] == 2)
                tappedtag = tappedtag - 7;
            else
                break;
        }
        if (gamestate[tappedtag] != 2) {
            return;
        } else {
            gamestate[tappedtag] = 0;
            insertCoin(0, tappedtag);
        }

        if (hasWon(0)) {
            PlayerWins+=1;
            alertBuilder(0);
            return;
        }

        Handler h = new Handler();

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (difficulty.equalsIgnoreCase("easy")) {
                    tappedtag = aiEasy.easyAI(gamestate, winningmap);
                }
                if (difficulty.equalsIgnoreCase("medium")){
                    tappedtag = aiMedium.mediumAI(gamestate, winningmap);
                }
                else if (difficulty.equalsIgnoreCase("hard")){
                    tappedtag = aiHard.hardAI(gamestate, winningmap);
                }
                gamestate[tappedtag] = 1;
                insertCoin(1,tappedtag);
                if (hasWon(1)){
                        SystemWins+=1;
                        alertBuilder(1);
                    return;
                }

                for (int i=0;i<gamestate.length;i++){
                    if (gamestate[i]==2)
                        break;
                    if (i==(gamestate.length-1))
                        alertBuilder(2);
                }
                gameplayable=true;
            }
        }, 1000);
    }


    public void findWinnings(){
        verticalWinning = new ArrayList<>();
        horizontalWinning = new ArrayList<>();
        diagonalWinning = new ArrayList<>();

        for(int i = 0; i<3; i++){
            for(int j = 0;j<7;j++){
                int point = j+(i*7);
                int arr[] = {point,point+(7),point+(7*2),point+(7*3)};
                verticalWinning.add(arr);
                ArrayList<int[]> poinlist =new ArrayList<>();
                poinlist.add(arr);
                winningmap.put(point,poinlist);
            }
        }

        for(int i=0;i<6;i++){
            for(int j=0;j<4;j++){
                int point1 = j+(i*7);
                int arr1[] = {point1,point1+1,point1+2,point1+3};
                ArrayList<int[]> pointlist;
                if (winningmap.containsKey(point1)){
                    pointlist = winningmap.get(point1);
                }
                else{
                    pointlist = new ArrayList<>();
                }
                pointlist.add(arr1);
                winningmap.put(point1,pointlist);
                horizontalWinning.add(arr1);
            }
        }

        for(int i=0;i<3;i++){
            for(int j=0;j<4;j++){
                int point = j+(i*7);
                int arr[] = {point,point+(8),point+(8*2),point+(8*3)};
                ArrayList<int[]> pointlist;
                if (winningmap.containsKey(point)){
                    pointlist = winningmap.get(point);
                }
                else{
                    pointlist = new ArrayList<>();
                }
                pointlist.add(arr);
                winningmap.put(point,pointlist);
                diagonalWinning.add(arr);
            }
        }

        for(int i=0;i<3;i++){
            for(int j=6;j>2;j--){
                int point = j+(i*7);
                int arr[] = {point,point+6,point+5+(7),point+4+(14)};
                ArrayList<int[]> pointlist;
                if (winningmap.containsKey(point)){
                    pointlist = winningmap.get(point);
                }
                else{
                    pointlist = new ArrayList<>();
                }
                pointlist.add(arr);
                winningmap.put(point,pointlist);
                diagonalWinning.add(arr);
            }
        }
    }

    public boolean hasWon(int player){
        boolean winner = false;
        for (int i=0; i<21; i++){
            if (gamestate[i]==player){
                ArrayList<int[]> possiblewins = winningmap.get(i);
                for (int[] each : possiblewins){
                    if (gamestate[each[0]]==gamestate[each[1]]){
                        if (gamestate[each[1]]==gamestate[each[2]]){
                            if (gamestate[each[2]]==gamestate[each[3]]){
                                winner=true;
                                winning_coins=each;
                                break;
                            }
                        }
                    }
                }
            }
            if (winner){
                break;
            }
        }
        return winner;
    }

    public void alertBuilder(final int player){
        Handler v = new Handler();
        v.postDelayed(new Runnable() {
            @Override
            public void run() {
                playerDisp.setText(Integer.toString(PlayerWins));
                systemDisp.setText(Integer.toString(SystemWins));
                final String message;
                if (player == 0 )
                    message = "You have won";
                else if (player == 1)
                    message = "You have lost";
                else
                    message = "The game is a draw";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new AlertDialog.Builder(gameActivity.this)
                                .setTitle(message)
                                .setMessage("Play again?")
                                .setCancelable(false)
                                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        PlayerWins = 0;
                                        SystemWins = 0;
                                        gameActivity.super.onBackPressed();
                                    }
                                })
                                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        recreate();
                                    }
                                })
                                .show();
                    }
                });
            }
        },1000);

    }

    public void insertCoin(int player, int tag) {
        String ImageId = "box" + Integer.toString(tag + 1);
        int imageid = gameActivity.this.getResources().getIdentifier(ImageId, "id", gameActivity.this.getPackageName());
        ImageView insertImage = (ImageView) findViewById(imageid);

        insertImage.setTranslationY(-1000f);
        if (player == 0)
            insertImage.setImageResource(R.drawable.red_coin);
        else if (player == 1)
            insertImage.setImageResource(R.drawable.yellow_coin);
        insertImage.animate().translationYBy(1000f);
    }

    @Override
    public void onBackPressed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(gameActivity.this)
                        .setTitle("EXIT")
                        .setMessage("Do you really want to quit?")
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        })
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PlayerWins = 0;
                                SystemWins = 0;
                                gameActivity.super.onBackPressed();
                            }
                        }).show();
            }
        });
    }
}
