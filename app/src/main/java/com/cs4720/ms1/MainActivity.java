package com.cs4720.ms1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    public static final String NAME = "com.cs4720.NAME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ButtonPress(View view){
        Intent intent = new Intent(this, DisplayCoords.class);
        EditText textBox = (EditText)findViewById(R.id.name_input);
        String name = textBox.getText().toString();
        intent.putExtra(NAME, name);
        startActivity(intent);

    }
}