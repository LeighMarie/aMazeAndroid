package com.example.mazewithwalls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
//home screen of aMaze

public class HomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBAdapter db = new DBAdapter(this);
        //get pre-created level database if not already there
        try {
        	String destPath = "/data/data/" + getPackageName() + "/databases";
        	File f = new File(destPath);
        	if(!f.exists()){
        		f.mkdirs();
        		f.createNewFile();
        		CopyDB(getBaseContext().getAssets().open("mydb"), new FileOutputStream(destPath + "/MyDB"));
        	}
        } catch (FileNotFoundException e){
        	e.printStackTrace();
        }catch (IOException e){
        	e.printStackTrace();
        }       
    	db.open();
    	db.close();
    }
    
    //when "Beat Our Levels" button is clicked
    //goes to list of levels user can choose to play from
    public void onClickPlayList(View view) {
    	startActivity(new Intent("net.learn2develop.PlayListActivity"));
        }
    
    //when "Brag About Your Times" button is clicked
    //goes to list of levels user can choose to see their data from each level
    public void onClickTimeList(View view) {
    	startActivity(new Intent("net.learn2develop.TimeListActivity"));
        }
    
    //copy pre-existing database
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = inputStream.read(buffer)) > 0) {
    		outputStream.write(buffer,0,length);
    	}
    	inputStream.close();
    	outputStream.close();
    }
}
    
    /*(public void onClickCreate(View view) {
    	startActivity(new Intent("net.learn2develop.CreateActivity"));
        }*/


        
  
