package com.example.mazewithwalls;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import javax.security.auth.callback.Callback;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

// activity that contains the maze game
// accessed by clicking on level in PlayListActivity

public class MazeActivity extends Activity {
	TextView timerTextView;
	long timeOfLastResume;	
	long prevTime;
	int minutes;
	int seconds;
	float partSec;
	float millis;
	boolean allLevelsFinished = false;
	String id;
	FinishLine finish;
	String score;
	Level lv;
	Marble ship;
	DBAdapter db;
	boolean check;
	int counter;
	SensorEventListener listener;
	Handler timerHandler = new Handler();
	Runnable timerRunnable = new Runnable() {

		@SuppressLint("DefaultLocale")
		@Override
		public void run() {
			//set timer and increment
			if (check){
			millis = (float)(prevTime + System.currentTimeMillis() - timeOfLastResume);
			partSec = (millis/1000);
			score = String.format("%3.1f", partSec);
			timerTextView.setText(score);
			timerHandler.postDelayed(this, 50);}
			
			//marble reaches finishLine
			if(finish.isFinished(ship)){
				Context context = getApplicationContext();	        	
				int duration = Toast.LENGTH_SHORT;
				counter++;
				if (counter == 1){
				//display final time to user in Toast pop-up
				Toast toast = Toast.makeText(context, score, duration);
				toast.show();
				
				db.open();
				//update the level's data with the last game's data
				Cursor c = db.getLevel(Integer.parseInt(id));
				//add one to successes
				int newAttempts = Integer.parseInt(c.getString(3)) + 1;
				//update the average time
				String newAv = String.format(Locale.ENGLISH, "%3.1f", (Float.valueOf(c.getString(4))*(newAttempts - 1) + Float.valueOf(score))/newAttempts);	
				// if past best time > current best time
				if (Float.parseFloat(c.getString(5)) > Float.valueOf(score))
				{
					//update best time with current best time
					db.updateLevel(new Long(id), c.getString(1), c.getString(2), Integer.toString(newAttempts), newAv, score);
					//if there is a next level, unlock it by updated status to "unlocked"
					if (Integer.parseInt(id) < 6){
					c = db.getLevel(Integer.parseInt(id) + 1);
					db.updateLevel(c.getLong(0), c.getString(1), "unlocked", c.getString(3), c.getString(4), c.getString(5));}	
					else{
					if (!allLevelsFinished){
						Toast toast1 = Toast.makeText(context,"Congratulations! You have finished all the levels!", Toast.LENGTH_LONG);
						toast1.show();
						allLevelsFinished = true;
				}}}
				else
				{
					//don't update best time
					db.updateLevel(new Long(id), c.getString(1), c.getString(2), Integer.toString(newAttempts), newAv, c.getString(5));
				}
				db.close();
				//return to Home Activity
				finish();	
			}
		}
		}


	};  
	
	//runs without a timer by reposting this handler at the end of the runnable
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		prevTime  = 0;
		setTitle("a Maze");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
		listener = new OurSensorEventListener();
		setContentView(R.layout.activity_maze);
		//get selected level id from PlayListActivity
		id = getIntent().getStringExtra("id");
		counter = 0;
		Log.d("maze", id);
		timerTextView = (TextView) findViewById(R.id.timerTextView);
		FrameLayout fl = (FrameLayout)findViewById(R.id.titles);		
		db = new DBAdapter(this);
		//use id to select the correct parameters to make the desired level view and access the desired level's data
		switch (Integer.parseInt(id)) {
		//"Your Maze is Here"
		case 1: lv = new Level(this, new Marble(20, 10, 20, 10), new Wall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break;  
		// "Minimum Speed Limit"
		case 2: lv = new Level(this, new SpeedyMarble(20, 10, 20, 10), new Wall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break;  
		//"Caffeine Boost"
		case 3: lv = new Level(this, new PositiveEnergyMarble(20, 10, 20, 10), new Wall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break; 
		// "What Happens in Vegas . . ."
		case 4: lv = new Level(this, new ChameleonMarble(20, 10, 20, 10), new RandomColorWall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break; 
		// "Limited Visibility"
		case 5: lv = new Level(this, new Marble(20, 10, 20, 10), new DisappearingWall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break; 
		// "Mind = Blown"
		case 6: lv = new Level(this, new ReverseMarble(20, 10, 20, 10), new Wall(20, 10, 20, 10));
		fl.addView(lv);
		ship = lv.getMarble();
		finish = lv.getFinishLine();
		break; 
		}
		
		Log.d("tag","3");
		timerHandler.postDelayed(timerRunnable, 0);
		Log.d("tag","4");        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.maze, menu);
		return true; 
	}

	@Override
	//save maze to last state of game
	public void onPause() {
		super.onPause();
		check = false;
		SensorManager ourSensorManager=((SensorManager)this.getSystemService(Context.SENSOR_SERVICE));
        ourSensorManager.unregisterListener(listener);
        prevTime = ((long)millis);
	}
	
	@Override
	//open saved maze/game state
	public void onResume(){
		super.onResume();	
		check = true;
		timeOfLastResume =System.currentTimeMillis();
		timerHandler.postDelayed(timerRunnable, 50);
		SensorManager ourSensorManager=((SensorManager)this.getSystemService(Context.SENSOR_SERVICE));

		//adds a listener for the Sensor of Type Accelerometer
		//the listener will trigger the onSensorChanged method 
		//whenever the accelerometer recognizes a change
		ourSensorManager.registerListener(
				listener,
				((SensorManager)this.getSystemService(Context.SENSOR_SERVICE))
				.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),   
				SensorManager.SENSOR_DELAY_GAME);
	}
	class OurSensorEventListener implements SensorEventListener{
		@Override  
		public void onSensorChanged(SensorEvent event) { 
			lv.updateGame(event);
		}
		@Override  
		public void onAccuracyChanged(Sensor sensor, int accuracy) {} //ignore
	}


}
