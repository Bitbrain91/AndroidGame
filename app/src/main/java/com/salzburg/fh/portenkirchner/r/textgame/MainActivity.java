package com.salzburg.fh.portenkirchner.r.textgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.text.SpannableString;
import android.text.Spanned;


public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnTextauswahl;

    String filename = "Text.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnTextauswahl = (Button)findViewById(R.id.btn_textauswahl);
    }

    public void onClick_btn_start(View v){
        /*
        Intent i = new Intent(this, AuswahlActivity.class);
        startActivity(i);
        */

        Intent i = new Intent(this, TextActivity.class);
        i.putExtra("filename", filename);
        startActivity(i);


    }
}
