package com.salzburg.fh.portenkirchner.r.textgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class TextActivity extends AppCompatActivity {


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
        ArrayList<ArrayList<String>> lueckenArray = new ArrayList<ArrayList<String>>();
        try {
            InputStream input = getAssets().open(filename);
            int size = input.available();
            buffer = new char[size];

            int bufferZaehler = 0;
            int lsgZaehler = 0;
            int lueckenZaehler = 0;
            boolean altFlag = false;

            while (input.available() > 0) {
                current = (char) input.read();
                Log.d("caracter:", "" + current);
                if (current == '<') {
                    altFlag = true;
                    lueckenArray.add(new ArrayList<String>());
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
                    continue;
                }
                if (altFlag == true) {
                    lueckenArray.get(lueckenZaehler).add(""+current);
                    buffer[bufferZaehler] = '_';
                    bufferZaehler++;
                } else {
                    Log.d("char:", ""+current);
                    buffer[bufferZaehler] = current;
                    bufferZaehler++;
                }
            }
            Log.d("buffer:", String.valueOf(buffer));
            tvLueckentext.setText(String.valueOf(buffer));
        }
        catch(IOException ex){
                Log.d("ERROR DETECTED", "ERROR WHILE TRYING TO OPEN FILE");
        }

    }
}
