package com.salzburg.fh.portenkirchner.r.textgame;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.salzburg.fh.portenkirchner.r.textgame.GlobalVariables;

public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Spinner dropDownListTextauswahl;
    String filename = "empty Text";
    FeedReaderDbHelper db;
    TextView tvHallOfFame;
    EditText etxtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btn_start);
        dropDownListTextauswahl = (Spinner) findViewById(R.id.spin_dropDownListTextauswahl);
        tvHallOfFame = (TextView) findViewById(R.id.tv_hallOfFame);
        tvHallOfFame.setMovementMethod(new ScrollingMovementMethod());
        etxtName = (EditText) findViewById(R.id.etxt_name);

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

        db = new FeedReaderDbHelper(this);
        tvHallOfFame.setText(db.getValues());
    }
    @Override
    protected void onResume() {
        super.onResume();
        tvHallOfFame.setText(db.getValues());
    }

    public void onClick_btn_start(View v){
        GlobalVariables globalVariables = GlobalVariables.getInstance();
        globalVariables.Name = etxtName.getText().toString();
        Intent i = new Intent(this, TextActivity.class);
        i.putExtra("filename", filename);
        startActivity(i);
    }
}
