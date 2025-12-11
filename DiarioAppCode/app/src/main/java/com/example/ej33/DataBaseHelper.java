package com.example.ej33;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "diary.db";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_TABLE = "CREATE TABLE diary (" +
                                                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                    "title TEXT NOT NULL," +
                                                    "content TEXT NOT NULL," +
                                                    "category TEXT NOT NULL);";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS diary");
        onCreate(db);
    }

    public int insertEntry(String title, String content, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("category", category);
        return (int) db.insert("diary", null, values);
    }

    public int updateEntry(int id, String title, String content, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("category", category);
        return (int) db.update("diary", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteEntry(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diary", "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteAllEntries() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("diary", null, null);
    }

    public Cursor getAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM diary", null);
    }
}
