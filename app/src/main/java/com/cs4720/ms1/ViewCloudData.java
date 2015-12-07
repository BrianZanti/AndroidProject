package com.cs4720.ms1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ViewCloudData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cloud_data);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "Izpp6si7XyILb60PlFhPa4dms9DjtGcrT6cTTphK", "UFquFrrczNxyrVGZWdbiPs4M3197Sh6zBgtqQVlL");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("locationObject");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    SimpleDateFormat sdf = new SimpleDateFormat();
                    sdf.applyPattern("MM/dd/yyyy hh:mm");
                    TextView tv = ((TextView)findViewById(R.id.cloudData));
                    String write_string = "";
                    for(int i = 0; i<objects.size(); i++) {
                        Date time = objects.get(i).getCreatedAt();
                        String timeString = sdf.format(time.getTime());
                        write_string += timeString + ": Latitude = " + objects.get(i).getNumber("latitude") + ", Longitude = "+objects.get(i).getNumber("longitude")+"\n";
                    }
                    tv.setText(write_string);
                } else {
                    TextView tv = ((TextView)findViewById(R.id.cloudData));
                    tv.setText(String.valueOf(e.getCode()));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_cloud_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
