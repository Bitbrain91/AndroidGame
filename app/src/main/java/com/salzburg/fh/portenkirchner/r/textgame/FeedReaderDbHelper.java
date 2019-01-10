package com.salzburg.fh.portenkirchner.r.textgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;

import java.io.File;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

public class FeedReaderDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Bestenliste";
    public static final String COLUMN_NAME_SCORE = "Score";
    public static final String COLUMN_NAME_NICKNAME = "Nick";
    public static final String _ID = "_id";
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "HallOfFame";
    public static final String DATABASE_PATH = "/data/data/com.salzburg.fh.portenkirchner.r.textgame/databases/";
    SQLiteDatabase dbWritable;
    SQLiteDatabase dbReadable;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_SCORE + " INTEGER," +
                    COLUMN_NAME_NICKNAME + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;



    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Check database already exists or not
    private boolean checkDatabaseExists() {
        boolean checkDB = false;
        try {
            String PATH = DATABASE_PATH + DATABASE_NAME;
            File dbFile = new File(PATH);
            checkDB = dbFile.exists();

        } catch (SQLiteException e) {

        }
        return checkDB;
    }

    public void onCreate(SQLiteDatabase db) {
        boolean checkDB = checkDatabaseExists();
        if(checkDB==false) {
            SQLiteDatabase ndb = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH + DATABASE_NAME, null);
            ndb.close();
        }

        db.execSQL(SQL_CREATE_ENTRIES);
        //dbWritable = this.getWritableDatabase();
        //dbReadable = this.getReadableDatabase();

        //dbWritable.execSQL(SQL_CREATE_ENTRIES);
        //dbWritable.close();
}
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        dbWritable = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void insert(String Nick, int score) {

        SQLiteDatabase dbWritable = this.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_SCORE, score);
        values.put(COLUMN_NAME_NICKNAME, Nick);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = dbWritable.insert(TABLE_NAME, null, values);
        dbWritable.close();
    }

    public String getValues() {

        SQLiteDatabase dbReadable = this.getReadableDatabase();
        String result = "";

        final String SQL_QUERY =
                "SELECT " + COLUMN_NAME_SCORE + "," + COLUMN_NAME_NICKNAME + " FROM " +
                        TABLE_NAME + " ORDER BY " + COLUMN_NAME_SCORE + " DESC;";
        Cursor cursor = dbReadable.rawQuery(SQL_QUERY,null);

        cursor.getColumnNames().toString();

        if (cursor.moveToFirst()) {
            do {
                result = result + cursor.getString(0);
                result = result + "  ";
                result = result + cursor.getString(1);
                result = result + "\n";
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void myDelete() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,null,null);
        // 2. delete
        //db.rawQuery("DELETE FROM " + TABLE_NAME, null);

        // 3. close
        db.close();
    }
}