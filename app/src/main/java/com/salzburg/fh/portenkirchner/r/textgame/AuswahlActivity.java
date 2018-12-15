package com.salzburg.fh.portenkirchner.r.textgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class AuswahlActivity extends AppCompatActivity {

    Button btnLoesung1;
    Button btnLoesung2;
    Button btnLoesung3;
    Button btnLoesung4;
    ImageView imgCheck;
    String loesung;

    Intent i = new Intent(this, AuswahlActivity.class);
    boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auswahl);

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
            ok = true;
        }
        else
        {
            ok = false;
            imgCheck.setImageResource(R.drawable.false_smiley);
        }
        i.putExtra("ok",ok);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.finish();
    }

    public void onClick_btn_loesung2(View v)
    {}

    public void onClick_btn_loesung3(View v)
    {}

    public void onClick_btn_loesung4(View v)
    {}

}
