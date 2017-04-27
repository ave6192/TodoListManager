package com.example.ex2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class FeedReaderDbHelper extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public ArrayList<Item> getDbValues() {
//        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<Item> array_list = new ArrayList<Item>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ FeedReaderContract.FeedEntry.TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String id = res.getString(res.getColumnIndex(FeedReaderContract.FeedEntry._ID));
            String text = res.getString(res.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            Item t = new Item(id, text);
            array_list.add(t);
            res.moveToNext();
        }

        return array_list;
    }

    public ArrayList<String> getDbText() {
//        ArrayList<String> array_list = new ArrayList<String>();
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ FeedReaderContract.FeedEntry.TABLE_NAME, null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            String text = res.getString(res.getColumnIndex(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
            array_list.add(text);
            res.moveToNext();
        }
        return array_list;
    }

    public Integer delete(int id)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME,
                FeedReaderContract.FeedEntry._ID + "= ? ",
                new String[] { Integer.toString(id) });
        return result;
    }
}
