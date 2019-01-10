package com.salzburg.fh.portenkirchner.r.textgame;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.view.Gravity;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Integer;


public class TextActivity extends AppCompatActivity {

    //Fixed with of the gaps in the gap text
    int gap_width = 4;

    //global for access in onClick method

    Animation animationMuenzeRotieren;

    int luecke_nr=0;
    ArrayList<ArrayList<String>> lueckenArray = new ArrayList<ArrayList<String>>();

    Intent clickIntent;

    TextView tvFilename;
    TextView tvLueckentext;
    TextView tvScore;
    TextView tvTries;
    ImageView ivMuenze;
    Button btnBack;
    FeedReaderDbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String filename = "";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        tvFilename = findViewById(R.id.tv_filename);
        tvLueckentext = findViewById(R.id.tv_lueckentext);
        tvScore = findViewById(R.id.tv_score);
        tvTries = findViewById(R.id.tv_tries);
        btnBack = findViewById(R.id.btn_back);
        db = new FeedReaderDbHelper(this);
        clickIntent = new Intent(TextActivity.this, AuswahlActivity.class);
        ivMuenze = findViewById(R.id.iv_muenze);
        ivMuenze.setBackgroundResource(R.drawable.coin);
        AnimationDrawable frameAnimation = (AnimationDrawable) ivMuenze.getBackground();
        frameAnimation.start();

        Bundle extras = getIntent().getExtras();
        if (!extras.isEmpty()) {
            filename = extras.getString("filename");
            tvFilename.setText(filename);
        }

        char current;
        char[] buffer;

        try {
            InputStream input = getAssets().open(filename + ".txt");
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
                    for (int j = 0; j <= (gap_width - 1); j++) {
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
                    lueckenArray.get(lueckenZaehler).set(lsgZaehler, lueckenArray.get(lueckenZaehler).get(lsgZaehler) + current);
                    /*buffer[bufferZaehler] = '_';
                    bufferZaehler++;*/
                } else {
                    Log.d("char:", "" + current);
                    buffer[bufferZaehler] = current;
                    bufferZaehler++;
                }
            }



            bufferZaehler++;
            buffer[bufferZaehler] = '\0';
            Log.d("buffer:", String.valueOf(buffer));

            //Finaly make gaps klickable
            SpannableString spannable_string_buffer = new SpannableString(String.valueOf(buffer));
            ArrayList<ClickableSpan> spans = new ArrayList<ClickableSpan>();
            int li = 0;

            luecke_nr = 0;
            Iterator<java.lang.Integer> lii = lueckenIndex.iterator();
            while (lii.hasNext()) {
                li = lii.next();
                luecke_nr++;
                Log.d("establish", "installing clickable span # " + String.valueOf(luecke_nr));

                ClickableSpan sp = new ClickableSpan() {
                    final int lueckenr = luecke_nr;

                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(TextActivity.this, "LueckeNr " + String.valueOf(lueckenr), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        Log.d("gap clicked", "luecke#: " + String.valueOf(luecke_nr) + "Loesung1: " + String.valueOf(lueckenArray.get(lueckenr - 1).get(0)));
                        //Log.d("gap clicked", "luecke klicked!"+String.valueOf(this.lueckenr));
                        clickIntent.putExtra("loesungen", lueckenArray.get(lueckenr - 1));
                        startActivity(clickIntent);
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setColor(Color.BLUE);
                        ds.setUnderlineText(false);
                        ds.setFakeBoldText(true);
                    }
                };

                spans.add(sp);
                spannable_string_buffer.setSpan(spans.get(spans.size() - 1), li, li + gap_width, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            //Write spanned text to TextView field.
            //tvLueckentext.setText(String.valueOf(buffer));
            tvLueckentext.setText(spannable_string_buffer);
            tvLueckentext.setMovementMethod(LinkMovementMethod.getInstance());

            //Print gap - Solution list to Log
            Iterator<ArrayList<String>> ali = lueckenArray.iterator();
            while (ali.hasNext()) {
                ArrayList<String> lsgArray = ali.next();
                Iterator<String> aly = lsgArray.iterator();
                while (aly.hasNext()) {
                    String current_lsg = aly.next();
                    Log.d("lsg", current_lsg);
                }
            }

        } catch (IOException ex) {
            Log.d("ERROR DETECTED", "ERROR WHILE TRYING TO OPEN FILE");
        }

        GlobalVariables.getInstance().amountGaps = lueckenArray.size();
    }

    @Override
    protected void onResume() {
        super.onResume();
        int remainingTries = GlobalVariables.getInstance().amountGaps - GlobalVariables.getInstance().tries;
        tvTries.setText(String.valueOf(remainingTries));
        if(GlobalVariables.getInstance().amountGaps <= GlobalVariables.getInstance().tries)
        {
            db.insert(GlobalVariables.getInstance().Name, GlobalVariables.getInstance().Score);
            showDialog();
        }
}

    public void onClick_btn_back(View v)
    {
        TextActivity.this.finish();
    }

    public void showDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(TextActivity.this).create();
        alertDialog.setTitle("Dein Score:");
        alertDialog.setMessage(String.valueOf(GlobalVariables.getInstance().Score) + " Punkte");
        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        GlobalVariables.getInstance().reset();
                        TextActivity.this.finish();
                        dialog.dismiss();// use dismiss to cancel alert dialog
                    }
                });

        alertDialog.show();
        Button b = ((AlertDialog)alertDialog).getButton(AlertDialog.BUTTON_NEUTRAL);
        b.setTextColor(Color.BLACK);

    }
}
