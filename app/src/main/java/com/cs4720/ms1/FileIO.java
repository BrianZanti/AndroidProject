package com.cs4720.ms1;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Brian on 10/6/2015.
 */
public class FileIO {
    private String FILENAME = "events.txt";

    /*public FileIO(String filename) throws IOException {
        FILENAME = filename;
        File file = new File(FILENAME);
        if(!file.exists())
        {
            file.createNewFile();
        }
    }*/
    public FileIO() throws IOException {

    }

    public String[][] readFile(Context context) throws IOException {
        FileInputStream fis = context.openFileInput(FILENAME);
        byte[] input = new byte[fis.available()];
        String data_read = "";
        while(fis.read(input) != -1){
            data_read += new String(input);
        }
        if(data_read.equals("")){
            return null;
        }
        String[] lines = data_read.split("\\r?\\n");
        String[][] args = new String[lines.length][];
        for(int i = 0; i < lines.length; i++)
        {
            args[i] = lines[i].split(" ");
        }
        return args;
    }

    public boolean containsEventName(String eventName, Context context) throws IOException {
        String[][] args = readFile(context);
        if(args == null)
        {
            return false;
        }
        for(int i = 0; i < args.length; i++){
            if(args[i][0].equals(eventName)){
                return true;
            }
        }
        return false;
    }

    public void write(String data, Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_APPEND);
        byte[] b = data.getBytes();
        fos.write(b);
        fos.close();
    }

    public void clearDB(Context context) throws IOException {
        FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        String s = "";
        fos.write(s.getBytes());
        fos.close();
        /*File file = new File(FILENAME);
        file.delete();*/
    }
}