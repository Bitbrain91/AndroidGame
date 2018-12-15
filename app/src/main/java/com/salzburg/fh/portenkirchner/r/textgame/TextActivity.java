package com.salzburg.fh.portenkirchner.r.textgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class TextActivity extends AppCompatActivity {

    Button btnStart;
    Button btnTextauswahl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnTextauswahl = (Button)findViewById(R.id.btn_textauswahl);
    }

    public void onClick_btn_start()
    {
    }


}
