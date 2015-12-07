package com.cs4720.ms1;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class SplashActivity extends AppCompatActivity {
    String directory_path = "/data/data/com.cs4720.ms1/app_imageDir";
    Button b1;
    ImageView iv;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DBHelper db = new DBHelper(this);
        db.createEvent("e1", 10,20);
        /*Cursor c = db.getEvent("e1");
        c.moveToFirst();
        long start = c.getLong(1);
        long end = c.getLong(2);
        System.out.println();*/

       /* SQLiteDatabase mydb = openOrCreateDatabase("fu", MODE_PRIVATE, null);
        mydb.execSQL("CREATE TABLE IF NOT EXISTS Event(name VARCHAR(25),start BIGINT, end BIGINT);");
        ContentValues values = new ContentValues();
        double lat = 62.20465484;
        values.put("latitude", lat);
        values.put("longitude",lat);
        mydb.insert("GPSCoords", null, values);
        Cursor resultSet = mydb.rawQuery("Select * from GPSCoords",null);
        resultSet.moveToFirst();
        System.out.println(resultSet.getDouble(0));
        System.out.println(resultSet.getDouble(1));
        System.out.println(System.currentTimeMillis());*/


        setContentView(R.layout.content_splash);
        File f = new File(directory_path + "/profile_pic.jpg");
        if (f.exists()) {
            loadImageFromStorage(directory_path);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }, 3000);

        } else {

            b1 = (Button) findViewById(R.id.camera);
            iv = (ImageView) findViewById(R.id.mImageView);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
            });
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
        String photo_path = saveToInternalStorage(bp);
        Toast.makeText(this, "Saved to " + photo_path + "/profile_pic.jpg", Toast.LENGTH_LONG).show();
        loadImageFromStorage(photo_path);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile_pic.jpg");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile_pic.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView img=(ImageView)findViewById(R.id.mImageView);
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}