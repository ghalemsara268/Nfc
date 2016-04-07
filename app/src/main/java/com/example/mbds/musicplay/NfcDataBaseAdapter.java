package com.example.mbds.musicplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by MBDS on 06/04/2016.
 */
public class NfcDataBaseAdapter  extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = NfcDataBaseAdapter.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_TODO_TAG = "todo_tags";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    // NOTES Table - column nmaes
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";

    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";

    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";
    public SQLiteDatabase db;

    // Table Create Statements
    // Todo table create statement
    static final String DATABASE_CREATE = "create table " + "LOGIN" +
            "( " + "ID" + " integer primary key autoincrement," + "USERNAME  text,PASSWORD text); ";

    static final String DATABASE_CREATE_nfc = "create table " + "NFCDATA" +
            "( " + "ID" + " integer primary key autoincrement," + "IDTAG  text,TAGNAME text,URLMUSIC text,USERNAME text); ";

    public NfcDataBaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE_nfc);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_nfc);


        // create new tables
        onCreate(db);
    }


    public void close()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

    public void insertEntry(String userName,String password)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("USERNAME", userName);
        newValues.put("PASSWORD",password);

        // Insert the row into your table
        db.insert("LOGIN", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insertEntry(String idTag,String nameTag,String urlMusic,String idUser)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("IDTAG", idTag);
        newValues.put("TAGNAME",nameTag);
        newValues.put("URLMUSIC",urlMusic);
        newValues.put("USERNAME",idUser);

        // Insert the row into your table
        db.insert("NFCDATA", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String UserName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //String id=String.valueOf(ID);
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("LOGIN", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }


    public int deleteAll(String UserName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where="USERNAME=?";
        int numberOFEntriesDeleted= db.delete("NFCDATA", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;

    }


    public String getSinlgeEntry(String userName)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query("LOGIN", null, " USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("PASSWORD"));
        cursor.close();
        return password;
    }

    public String getNfcId(String idTag,String username)
    {

        SQLiteDatabase db = this.getReadableDatabase();
       // Cursor cursor=db.query("NFCDATA", null, " IDTAG=?", new String[]{idTag}, " IDTAG=?", new String[]{username}, null);
        String TABLE = "NFCDATA";
       // String[] FIELDS = { "URLMUSIC" };
        String WHERE =  "IDTAG='idTag' AND USERNAME='username'";
// Execute
       Cursor cursor = db.query(TABLE, null, WHERE, null, null, null, null);

        if(cursor.getCount()<1)
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String myurl= cursor.getString(cursor.getColumnIndex("URLMUSIC"));
        cursor.close();
        return myurl;
    }

    public String getUser(String idTag)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query("NFCDATA", null, " IDTAG=?", new String[]{idTag}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String usr= cursor.getString(cursor.getColumnIndex("USERNAME"));
        cursor.close();
        return usr;
    }



    public void  updateEntry(String userName,String idNfc,String myUrl)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("URLMUSIC", myUrl);


        String where="USERNAME ='userName' AND IDTAG='idNfc' ";
        db.update("NFCDATA", updatedValues, where, null);
    }









}