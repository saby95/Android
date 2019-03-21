package com.redbolt.connect4;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AIMedium {

    private static String TAG = "com.redbolt.connect4";

    public int mediumAI(int[] gamestate,HashMap<Integer,ArrayList<int[]>> winningmap){
        int confirmWin[] = threeCoinMatch(1,gamestate,winningmap);
        if (confirmWin[0] == 1) {
            gamestate[confirmWin[1]] = 1;
            return confirmWin[1];
        }
        int weights[] = {1, 1, 1, 1, 1, 1, 1};
        int twoconf[] = twoCoinMatch(1,gamestate,winningmap);
        if (twoconf[0] == 1) {
            weights[(twoconf[1]%7)] += 3;
        }
        int oppwin[] = threeCoinMatch(0,gamestate,winningmap);
        if (oppwin[0] == 1) {
            weights[(oppwin[1]%7)] += 8;
        }
        int position = insertPosition(weights);
        position = position + (5 * 7);
        while (gamestate[position] != 2) {
            position = insertPosition(weights);
            position = position + (5 * 7);
        }
        int tappos = position;
        while (tappos > 6) {
            if (gamestate[tappos - 7] == 2)
                tappos = tappos - 7;
            else
                break;
        }
        return tappos;
    }

    private int insertPosition(int[] weightarray){
        int sum = 0;
        for (int a : weightarray){
            sum+=a;
        }
        Random r = new Random();
        int s = r.nextInt(sum);
        int prev_value = 0;
        int current_max_value = 0;
        int found_index = -1;
        for(int i=0; i< weightarray.length; i++){
            current_max_value = prev_value + weightarray[i];
            boolean found = (s >= prev_value && s < current_max_value);
            if( found ){
                found_index = i;
                break;
            }
            prev_value = current_max_value;
        }
        return found_index;
    }

    private int[] threeCoinMatch(int player, int[] gamestate, HashMap<Integer,ArrayList<int[]>> winningmap){
        int match = 0;
        int position = 42;
        boolean exitflag = false;
        for (int i=0; i<21; i++) {
            ArrayList<int[]> gotlist = winningmap.get(i);
            if (gamestate[i] == player) {
                for (int[] each : gotlist) {
                    if (gamestate[each[0]] == gamestate[each[1]]) {
                        if (gamestate[each[1]] == gamestate[each[2]]) {
                            if (gamestate[each[3]] == 2) {
                                if (each[3] > 6) {
                                    if (gamestate[each[3] - 7] != 2) {
                                        match = 1;
                                        position = each[3];
                                        exitflag = true;
                                        break;
                                    }
                                } else {
                                    match = 1;
                                    position = each[3];
                                    exitflag = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (gamestate[each[0]] == gamestate[each[1]]) {
                        if (gamestate[each[1]] == gamestate[each[3]]) {
                            if (gamestate[each[2]] == 2) {
                                if (each[2] > 6) {
                                    if (gamestate[each[2] - 7] != 2) {
                                        match = 1;
                                        position = each[2];
                                        exitflag = true;
                                        break;
                                    }
                                } else {
                                    match = 1;
                                    position = each[2];
                                    exitflag = true;
                                    break;
                                }
                            }
                        }
                    }

                    if (gamestate[each[0]] == gamestate[each[2]]) {
                        if (gamestate[each[2]] == gamestate[each[3]]) {
                            if (gamestate[each[1]] == 2) {
                                if (each[1] > 6) {
                                    if (gamestate[each[1] - 7] != 2) {
                                        match = 1;
                                        position = each[1];
                                        exitflag = true;
                                        break;
                                    }
                                } else {
                                    match = 1;
                                    position = each[1];
                                    exitflag = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            } else if (gamestate[i] == 2) {
                if (i > 7) {
                    if (gamestate[i - 7] == 2)
                        continue;
                }
                for (int[] each : gotlist) {
                    if (gamestate[each[1]] == gamestate[each[2]]
                            && gamestate[each[2]] == gamestate[each[3]]
                            && gamestate[each[1]] == player) {

                        match = 1;
                        position = each[0];
                        exitflag = true;
                        break;
                    }
                }
            }
            if (exitflag) {
                break;
            }
        }
        //int arr[] = {match,position};
        return new int[]{match,position};
    }

    private int[] twoCoinMatch(int player, int[] gamestate, HashMap<Integer,ArrayList<int[]>> winningmap){
        int match = 0;
        ArrayList<Integer> returnElement = new ArrayList<>();
        int returnValue;
        for (int i=0; i< (gamestate.length/2); i++){
            ArrayList<int[]> currentList = winningmap.get(i);
            if (gamestate[i]==player){
                for (int[] currentArray : currentList){
                    int b,rand;
                    int temp[] = new int[2];
                    for (b=1;b<4;b++){
                        if (currentArray[0]==currentArray[b]){
                            match = 1;
                            switch (b) {
                                case 1:
                                    temp[0]=2;temp[1]=3;
                                    rand = new Random().nextInt(temp.length);
                                    returnElement.add(currentArray[temp[rand]]);
                                    break;
                                case 2:
                                    temp[0]=1;temp[1]=3;
                                    rand = new Random().nextInt(temp.length);
                                    returnElement.add(currentArray[temp[rand]]);
                                    break;
                                case 3:
                                    temp[0]=1;temp[1]=2;
                                    rand = new Random().nextInt(temp.length);
                                    returnElement.add(currentArray[temp[rand]]);
                                    break;
                            }
                        }
                    }
                }
            }
            else if (gamestate[i]==2){
                for (int[] each: currentList){
                    if (gamestate[each[1]]==gamestate[each[2]]){
                        if (gamestate[each[3]]==2){
                            returnElement.add(each[0]);
                        }
                    }
                    else if (gamestate[each[1]]==gamestate[each[3]]){
                        if (gamestate[each[2]]==2){
                            returnElement.add(each[0]);
                        }
                    }
                    else if (gamestate[each[2]]==gamestate[each[3]]){
                        if (gamestate[each[2]]==2){
                            returnElement.add(each[0]);
                        }
                    }
                }
            }
        }
        Log.d(TAG,"Iterations complete");
        if (returnElement.size()!=0){
            int rando = new Random().nextInt(returnElement.size());
            returnValue = (returnElement.get(rando))%7;
        }
        else returnValue = 0;
        return new int[]{match,returnValue};
    }
}
