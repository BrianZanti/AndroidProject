package com.cs4720.ms1;

import android.content.Context;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity{

    public static final String NAME = "com.cs4720.NAME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ButtonPress(View view) throws IOException {
        Intent intent = new Intent(this, DisplayCoords.class);
        EditText textBox = (EditText)findViewById(R.id.name_input);
        String name = textBox.getText().toString();
        String FILENAME = "data_storage.txt";
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_APPEND);
        fos.write(name.getBytes());
        fos.close();
        FileInputStream fis = openFileInput(FILENAME);
        byte[] input = new byte[fis.available()];
        String data_read = "";
        while(fis.read(input) != -1){
            data_read += new String(input);
        }
        fis.close();

        intent.putExtra(NAME, data_read);
        startActivity(intent);

    }
}