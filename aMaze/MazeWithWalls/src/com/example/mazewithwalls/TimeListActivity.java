
package com.example.mazewithwalls;

import java.util.ArrayList;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
// list of levels for user to select level and see their data
// accessed by pressing button "Brag About Your Times"

public class TimeListActivity extends ListActivity  {
	ArrayList<String> ids = new ArrayList<String>();
	ArrayList<String> levels = new ArrayList<String>();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.listt);
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
        setListAdapter(new ArrayAdapter<String>(this,
            R.layout.newlist, levels));
        ((ListView) findViewById(android.R.id.list)).setFastScrollEnabled(true);
    }
    
    public void onListItemClick(
    ListView parent, View v, int position, long id)
    {
    	Intent i = new Intent("net.learn2develop.DisplayActivity");
        i.putExtra("id", ids.get(position));
        startActivity(i);
        //---closes the activity---
        finish();
        
    }
    
}

