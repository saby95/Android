package com.shabarik.smartbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.RelativeLayout;
import android.content.Intent;

import java.util.Arrays;


public class ButtonActivity extends AppCompatActivity {

/*    class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        public void uncaughtException(Thread t, Throwable e) {
            System.err.println ("Uncaught exception thrown in thread " + t);
            e.printStackTrace();

            // This is a bad place to be.
            // Do something here other than crashing and enraging users.
            // You can add diagnostics here to design a specific exception handler.
            return null;
        }
    }

    Thread.UncaughtExceptionHandler(new MyUncaughtExceptionHandler());*/

    private static EditText UserName;
    private static EditText CrushName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button);
        RelativeLayout Main_View = (RelativeLayout)findViewById(R.id.Main_View);
        //setContentView(Main_View);

        final Button EnterButton = (Button)findViewById(R.id.EnterButton);
        this.UserName = (EditText)findViewById(R.id.UserName);
        this.CrushName = (EditText)findViewById(R.id.OtherName);

        EnterButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        buttonclicked(v);
                    }
                }

        );


    }

    public void buttonclicked(View view){

        String Name1 = UserName.getText().toString();
        String Name2 = CrushName.getText().toString();
        int k=0,j,flag=0,lengt=0,t;
        for (int i=0; i<Name1.length(); i++){
            if(Name1.charAt(i)==' '){
                Name1 = new StringBuilder(Name1).deleteCharAt(i).toString();
            }
            if((Name1.charAt(i)<'Z') && (Name1.charAt(i)>'A')){

                Name1 = new StringBuilder(Name1).replace(i,i+1,Integer.toString(Name1.charAt(i)+32)).toString();
            }
        }

        for (int i=0; i<Name2.length(); i++){
            if(Name2.charAt(i)==' '){
                Name2 = new StringBuilder(Name2).deleteCharAt(i).toString();
            }
            if((Name2.charAt(i)<'Z') && (Name2.charAt(i)>'A')){

                Name2 = new StringBuilder(Name2).replace(i,i+1,Integer.toString(Name2.charAt(i)+32)).toString();
            }
        }

        for(int i=0; i<Name1.length(); i++){
            for(j=0; j<Name2.length(); j++){
                if (Name1.charAt(i) == Name2.charAt(j)){
                    Name2 = new StringBuilder(Name2).deleteCharAt(j).toString();
                    flag =1;
                    break;
                }
            }
            if (flag == 1){
                lengt+=1;
                flag=0;
            }
        }
        lengt+=Name2.length();
        String Res = new String("FLAMES");
        String Res1 = new String("FLAMES");
        while (Res.length()!=1){
            t = lengt%Res.length();
            Res = new StringBuilder(Res).deleteCharAt(t).toString();
        }
        char a = Res.charAt(0);
        int b = Res1.indexOf(a)+1;

        Intent i = new Intent(this, ResultActivity.class);
        i.putExtra("Match", b);

        startActivity(i);
    }
}
