package com.honestman.piotrk.notes;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/**
 * Created by PiotrK on 2016-09-25.
 */

public class DBHelper extends SQLiteOpenHelper{

    
    /*create database and column for it*/
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String NOTES_TABLE_NAME = "notes";
    public static final String NOTES_COLUMN_ID = "id";
    public static final String NOTES_COLUMN_NAME = "name";
    public static final String NOTES_COLUMN_NOTE = "note";

    /*constructor required*/
    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    /*to avoid problems with deleting records I created two tables "notes" and temporary table "tymczasowa" */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table notes " + "(id integer primary key AUTOINCREMENT  not null, name text, note text)");
        db.execSQL("create table tymczasowa " + "(id integer primary key AUTOINCREMENT  not null, raz text, dwa text)" );
    }

    /*when I upgrade I realy destroy table and create it back*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes" );
        onCreate(db);
    }

    /*to insert notes i use ContentValues class and store a set of values which i can put to my database*/
    public boolean insertNotes  (String name, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("note", note);
        db.insert("notes", null, contentValues);
        return true;
    }

    /*when I update notes I also use ContentValues*/
    public boolean updateNotes (Integer id, String name, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("note", note);
        db.update("notes", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    /*this is mostly sql command, before deleting a table I write everything without id to table "tymczasowa" 
    then I delete table notes and recreate it from table "tymczasowa" */
    public void deleteNotes (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from notes where id = " + id.toString() +"");
        db.execSQL("delete from sqlite_sequence where name = 'notes'");
        db.execSQL("insert into tymczasowa (raz, dwa) select name raz, note dwa from notes");
        db.execSQL("delete from notes");
        db.execSQL("delete from sqlite_sequence where name = 'notes'");
        db.execSQL("insert into notes (name, note) select raz name, dwa note from tymczasowa");
        db.execSQL("delete from tymczasowa");
        db.execSQL("delete from sqlite_sequence where name = 'tymczasowa'");
    }

    /*get all data for one note*/
    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from notes where id = " +  id + "", null );
        return res;
    }

    /*get all notes and puts it to string array which i use in MainActivity with ListView*/
    public ArrayList<String> getAllNotes()
    {
        ArrayList<String> array_list = new ArrayList<String>();


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from notes", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(NOTES_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }


}










