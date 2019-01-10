package com.salzburg.fh.portenkirchner.r.textgame;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import com.salzburg.fh.portenkirchner.r.textgame.GlobalVariables;

public class AuswahlActivity extends AppCompatActivity {

    Button btnLoesung1;
    Button btnLoesung2;
    Button btnLoesung3;
    Button btnLoesung4;
    ImageView imgCheck;

    private MediaPlayer mp_ok;
    private MediaPlayer mp_fail;


    String loesung;
    boolean status;

    //Intent zurueck_zum_text_intent;
    Handler handler = new Handler();

    Thread auswahlActivity;

    private Runnable finishRunnable = new Runnable()
    {
        @Override
        public void run() {
            GlobalVariables.getInstance().tries++;
            if(status)
                GlobalVariables.getInstance().Score++;
            AuswahlActivity.this.finish();
        }
    };

    @Override
    protected void onDestroy() {
        mp_ok.release();
        mp_fail.release();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswahl);

        auswahlActivity = Thread.currentThread();

        mp_ok = MediaPlayer.create(this, R.raw.cheer);
        mp_fail = MediaPlayer.create(this, R.raw.fail);

        //zurueck_zum_text_intent = new Intent(this, TextActivity.class);

        btnLoesung1 = findViewById(R.id.btn_loesung1);
        btnLoesung2 = findViewById(R.id.btn_loesung2);
        btnLoesung3 = findViewById(R.id.btn_loesung3);
        btnLoesung4 = findViewById(R.id.btn_loesung4);
        imgCheck = findViewById(R.id.img_check);
        imgCheck.setImageResource(R.drawable.gruebel_smiley);
        //Alle Extras des Intents holen
        Bundle extras = getIntent().getExtras();
        //Überprüfen ob Extras vorhanden
        if(extras != null) {
            //nach message in extras suchen
            ArrayList<String> loesungen = extras.getStringArrayList("loesungen");
            //ArrayList<String> loesungen = new ArrayList<>(Arrays.asList("Baum","Strauch","Kraut","Moos"));

            loesung = loesungen.get(0);

            Collections.shuffle(loesungen);

            btnLoesung1.setText(loesungen.get(0));
            btnLoesung2.setText(loesungen.get(1));
            btnLoesung3.setText(loesungen.get(2));
            btnLoesung4.setText(loesungen.get(3));
        }
    }


    public void onClick_btn_loesung1(View v)
    {
        if(btnLoesung1.getText().equals(loesung))
        {
            imgCheck.setImageResource(R.drawable.ok_smiley);
            mp_ok.start();
            status = true;
        }
        else
        {
            mp_fail.start();
            status = false;
            imgCheck.setImageResource(R.drawable.false_smiley);
        }
        //zurueck_zum_text_intent.putExtra("ok",status);

        handler.postDelayed(finishRunnable, 2000);

    }

    public void onClick_btn_loesung2(View v)
    {
        if(btnLoesung2.getText().equals(loesung))
        {
            imgCheck.setImageResource(R.drawable.ok_smiley);
            mp_ok.start();
            status = true;
        }
        else
        {
            mp_fail.start();
            status = false;
            imgCheck.setImageResource(R.drawable.false_smiley);
        }
        //zurueck_zum_text_intent.putExtra("ok",status);

        handler.postDelayed(finishRunnable, 2000);
    }

    public void onClick_btn_loesung3(View v)
    {
        if(btnLoesung3.getText().equals(loesung))
        {
            imgCheck.setImageResource(R.drawable.ok_smiley);
            mp_ok.start();
            status = true;
        }
        else
        {
            mp_fail.start();
            status = false;
            imgCheck.setImageResource(R.drawable.false_smiley);
        }
        //zurueck_zum_text_intent.putExtra("ok",status);

        handler.postDelayed(finishRunnable, 2000);
    }

    public void onClick_btn_loesung4(View v)
    {
        if(btnLoesung4.getText().equals(loesung))
        {
            imgCheck.setImageResource(R.drawable.ok_smiley);
            mp_ok.start();
            status = true;
        }
        else
        {
            mp_fail.start();
            status = false;
            imgCheck.setImageResource(R.drawable.false_smiley);
        }
        //zurueck_zum_text_intent.putExtra("ok",status);

        handler.postDelayed(finishRunnable, 2000);
    }
}
