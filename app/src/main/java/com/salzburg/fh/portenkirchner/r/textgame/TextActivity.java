package com.salzburg.fh.portenkirchner.r.textgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.lang.Integer;


public class TextActivity extends AppCompatActivity {

    //Fixed with of the gaps in the gap text
    int gap_width = 4;

    //global for access in onClick method
    int luecke_nr=0;
    ArrayList<ArrayList<String>> lueckenArray = new ArrayList<ArrayList<String>>();
    Intent clickIntent = new Intent(this,AuswahlActivity.class);

    TextView tvFilename;
    TextView tvLueckentext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String filename = "";


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        tvFilename = findViewById(R.id.tv_filename);
        tvLueckentext = findViewById(R.id.tv_lueckentext);

        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) {
            filename = extras.getString("filename");
            tvFilename.setText(filename);
        }

        char current;
        char[] buffer;

        try {
            InputStream input = getAssets().open(filename);
            int size = input.available();
            buffer = new char[size];

            int bufferZaehler = 0;
            int lsgZaehler = 0;
            int lueckenZaehler = 0;
            boolean altFlag = false;
            final ArrayList<java.lang.Integer> lueckenIndex = new ArrayList<Integer>();

            while (input.available() > 0) {
                current = (char) input.read();
                Log.d("caracter:", "" + current);
                if (current == '<') {
                    altFlag = true;
                    //save position of gap in buffer for later span
                    lueckenIndex.add(bufferZaehler);
                    //Add solution-string to gap
                    lueckenArray.add(new ArrayList<String>());
                    lueckenArray.get(lueckenZaehler).add("");

                    //Add gap in Textbuffer (standardized 4 unterscore chars)
                    for(int j = 0; j<=(gap_width-1);j++) {
                        buffer[bufferZaehler] = '_';
                        bufferZaehler++;
                    }
                    continue;
                }
                if (current == '>') {
                    lueckenZaehler++;
                    altFlag = false;
                    lsgZaehler = 0;
                    continue;
                }
                if (current == ',') {
                    lsgZaehler++;
                    //Add solution-string to gap
                    lueckenArray.get(lueckenZaehler).add("");
                    continue;
                }
                if (altFlag == true) {
                    lueckenArray.get(lueckenZaehler).set(lsgZaehler, lueckenArray.get(lueckenZaehler).get(lsgZaehler)+current);
                    /*buffer[bufferZaehler] = '_';
                    bufferZaehler++;*/
                } else {
                    Log.d("char:", ""+current);
                    buffer[bufferZaehler] = current;
                    bufferZaehler++;
                }
            }
            Log.d("buffer:", String.valueOf(buffer));


            //Finaly make gaps klickable
            ArrayList<ClickableSpan> spans = new ArrayList<ClickableSpan>();
            SpannableString spannable_string_buffer = new SpannableString(String.valueOf(buffer));

            int li = 0;

            Iterator<java.lang.Integer> lii = lueckenIndex.iterator();
            while(lii.hasNext()) {
                li = lii.next();
                luecke_nr++;
                ClickableSpan sp = new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        //Toast.makeText(TextActivity.this, "Luecke", Toast.LENGTH_SHORT).show();
                        Log.d("gap clicked","luecke#: "+String.valueOf(luecke_nr)+"Loesung1: "+String.valueOf(lueckenArray.get(luecke_nr).get(0)));
                        //clickIntent.putExtra(lueckenArray.get(luecke_nr));
                    }
                };

                spans.add(sp);
                spannable_string_buffer.setSpan(spans.get(spans.size()-1), li, li+gap_width, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            //Write spanned text to TextView field.
            //tvLueckentext.setText(String.valueOf(buffer));
            tvLueckentext.setText(spannable_string_buffer);

            //Print gap - Solution list to Log
            Iterator<ArrayList<String>> ali = lueckenArray.iterator();
            while(ali.hasNext()){
                ArrayList<String> lsgArray = ali.next();
                Iterator<String> aly = lsgArray.iterator();
                while(aly.hasNext()){
                    String current_lsg = aly.next();
                    Log.d("lsg",current_lsg);
                }

            }

        }
        catch(IOException ex){
                Log.d("ERROR DETECTED", "ERROR WHILE TRYING TO OPEN FILE");
        }

    }
}
