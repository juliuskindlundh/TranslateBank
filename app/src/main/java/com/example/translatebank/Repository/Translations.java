package com.example.translatebank.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public final class Translations extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Translations.db";

    public Translations(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final String TABLE_NAME="translations";
    public static final String COLUMN_NAME_ORIGINAL="original";
    public static final String COLUMN_NAME_RESULT="result";
    public static final String COLUMN_NAME_DATETIME="datetime";
    public static final String COLUMN_NAME_ORIGINAL_LANGUAGE="original_language";
    public static final String COLUMN_NAME_NEW_LANGUAGE="new_language";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE "
                + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME_ORIGINAL + " TEXT, "
                + COLUMN_NAME_RESULT + " TEXT, "
                + COLUMN_NAME_ORIGINAL_LANGUAGE + " TEXT, "
                + COLUMN_NAME_NEW_LANGUAGE + " TEXT, "
                + COLUMN_NAME_DATETIME + " TEXT);";
        Log.d("db",createQuery);
        db.execSQL(createQuery);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public boolean add(TranslationDTO dto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_NAME_DATETIME,dto.getDatetime());
        contentValues.put(COLUMN_NAME_ORIGINAL_LANGUAGE,dto.getFrom());
        contentValues.put(COLUMN_NAME_ORIGINAL,dto.getOriginal());
        contentValues.put(COLUMN_NAME_RESULT,dto.getResult());
        contentValues.put(COLUMN_NAME_NEW_LANGUAGE,dto.getTo());

        long id = db.insert(TABLE_NAME,null,contentValues);

        db.close();

        if(id == -1 ){
            return false;
        }
        else{
            return true;
        }
    }

    public ArrayList<TranslationDTO> getAll(){
        ArrayList<TranslationDTO> arrList = new ArrayList<TranslationDTO>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectAll = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            while(cursor.moveToNext()){
                TranslationDTO temp = new TranslationDTO();
                temp.setOriginal(cursor.getString(1));
                temp.setResult(cursor.getString(2));
                arrList.add(temp);
            }
        }
        cursor.close();
        db.close();
        return  arrList;
    }
}
