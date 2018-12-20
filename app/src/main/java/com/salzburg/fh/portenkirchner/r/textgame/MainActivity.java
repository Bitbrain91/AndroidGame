package com.salzburg.fh.portenkirchner.r.textgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Spinner dropDownListTextauswahl;

    String filename = "empty Text";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);
        dropDownListTextauswahl = (Spinner) findViewById(R.id.spin_dropDownListTextauswahl);


        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                        android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        staticAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        dropDownListTextauswahl.setAdapter(staticAdapter);

        filename = dropDownListTextauswahl.getSelectedItem().toString();
    }

    public void onClick_btn_start(View v){
        Intent i = new Intent(this, TextActivity.class);
        i.putExtra("filename", filename);
        startActivity(i);
    }

}
