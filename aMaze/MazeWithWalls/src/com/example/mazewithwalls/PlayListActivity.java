package com.example.mazewithwalls;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

// accessed by clicking "Beat Our Levels"
// displays list of levels user can choose to play from

public class PlayListActivity extends ListActivity  {
	ArrayList<String> ids = new ArrayList<String>();
	ArrayList<String> levels = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list);
        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getAllLevels();
        //make ArrayLists with ids and names of levels
        if (c.moveToFirst()) 
        {
            do {
            	ids.add(c.getString(0));
                levels.add((c.getString(1)).substring(0, (c.getString(1).indexOf("  "))));
            } while (c.moveToNext());
        }
        db.close();
        // make list view with ArrayList of level names
        setListAdapter(new ArrayAdapter<String>(this,
            R.layout.newlist, levels));
        ((ListView) findViewById(android.R.id.list)).setFastScrollEnabled(true);
    }
    
    public void onListItemClick(
    ListView parent, View v, int position, long id)
    {
    	Intent i = new Intent("net.learn2develop.MazeActivity");
    	DBAdapter db = new DBAdapter(this);
    	db.open(); 
    	Cursor c = db.getLevel(Integer.parseInt(ids.get(position)));
    	//checks whether user can play level that user selected from the list 
    	Log.d("maze", c.getString(2).substring(0, 6));    	
    	if ((c.getString(2).substring(0, 6)).equals("unlock"))
    	{
        i.putExtra("id", ids.get(position));
        startActivity(i);
    	db.close();
        finish();
    	}
    	else
    		Toast.makeText(this, "Level not unlocked. Play more mazes!", Toast.LENGTH_SHORT).show();
        //---closes the activity---
        
    }
    

}

