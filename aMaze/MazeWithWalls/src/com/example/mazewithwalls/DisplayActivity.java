package com.example.mazewithwalls;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

//used to display data of level user clicked on in TimeListActivity

public class DisplayActivity extends Activity{
	String level;
	String status;
	String successes;
	String average;
	String best;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.display);
        //get level id from TimeListActivity
        int id = Integer.parseInt(getIntent().getStringExtra("id").toString());
        DBAdapter db = new DBAdapter(this);
        db.open();
        //get the level's information from the database
        Cursor c = db.getLevel(id);
        if (c.moveToFirst())
        {
        	    //get the correctly formatted information (with no extra whitespace)
            	level = c.getString(1).substring(0, (c.getString(1).indexOf("  ")));
            	status = c.getString(2).substring(0, ((c.getString(2).indexOf("d")) + 1));
            	successes = c.getString(3);
            	average = c.getString(4).substring(0, ((c.getString(4).indexOf(".")) + 2));
            	best = c.getString(5);
        }
        db.close();
        //put the level information in the text views
        TextView t2 =(TextView)findViewById(R.id.level_display);
        t2.setText("Level: " +level);
        TextView t3 =(TextView)findViewById(R.id.status_display);
        t3.setText("Status: " +status);
        TextView t4 =(TextView)findViewById(R.id.successes_display);
        t4.setText("Successes: " +successes);
        TextView t5 =(TextView)findViewById(R.id.average_display);
        float av = new Float(average);
        float b = new Float(best);
        //if default values for average and best (user hasn't played the level), display "TBD"
        if (av == 0){
        t5.setText("Average Time: TBD");}
        else{
        	t5.setText("Average Time: "+average);}
        TextView t6 =(TextView)findViewById(R.id.best_display);
        if (b > 999999){
        t6.setText("Best Time: TBD");}
        else{
        	t6.setText("Best Time: "+best);}
    }
    @Override
    //when user presses back button, return to the list of levels (TimeListActivity), not home activity 
	public void onPause() {
		super.onPause();
		startActivity(new Intent("net.learn2develop.TimeListActivity"));
	}
    
}