package com.example.mazewithwalls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* Pre-existing database of level ids and level names (in mydb file in assets folder) 
* Will be edited to hold the user's level data 
* user data include whether or not user has unlocked the level (status)
* how many times user has beaten the level (successes)
* the average time it took user to beat the level (average)
* user's best time beating the level (best)
*/

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_LEVEL = "level";
    static final String KEY_STATUS = "status";
    static final String KEY_SUCCESSES = "successes";
    static final String KEY_AVERAGE = "average";
    static final String KEY_BEST = "best";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "stats";
    static final int DATABASE_VERSION = 1;

    static final String DATABASE_CREATE =
        "create table stats (_id integer primary key autoincrement, "
        + "level text not null, status text not null, successes text not null, average text not null, best text not null);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS stats");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close() 
    {
        DBHelper.close();
    }

    //---insert a level into the database---
    public long insertLevel(String level, String status, String successes, String average, String best)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LEVEL, level);
        initialValues.put(KEY_STATUS, status);
        initialValues.put(KEY_SUCCESSES, successes);
        initialValues.put(KEY_AVERAGE, average);
        initialValues.put(KEY_BEST, best);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular level---
    public boolean deleteLevel(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the levels---
    public Cursor getAllLevels()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_LEVEL, KEY_STATUS,
        		 KEY_SUCCESSES, KEY_AVERAGE, KEY_BEST}, null, null, null, null, null);
    }

    //---retrieves a particular level---
    public Cursor getLevel(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                		KEY_LEVEL, KEY_STATUS,
                		 KEY_SUCCESSES, KEY_AVERAGE, KEY_BEST}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a level---
    public boolean updateLevel(long rowId, String level, String status, String successes, String average, String best)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_LEVEL, level);
        args.put(KEY_STATUS, status);
        args.put(KEY_SUCCESSES, successes);
        args.put(KEY_AVERAGE, average);
        args.put(KEY_BEST, best);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}