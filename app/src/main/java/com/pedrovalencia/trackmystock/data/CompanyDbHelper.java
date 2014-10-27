package com.pedrovalencia.trackmystock.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pedrovalencia on 26/10/14.
 */
public class CompanyDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "company.db";
    public static final int DATABASE_VERSION = 1;

    public CompanyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //This method will be called first time the application executes,
    //creating the table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_COMPANY_TABLE = "CREATE TABLE " + CompanyContract.CompanyEntry.TABLE_NAME + " (" +
                CompanyContract.CompanyEntry.COLUMN_SYMBOL + " TEXT PRIMARY KEY, " +
                CompanyContract.CompanyEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                CompanyContract.CompanyEntry.COLUMN_PRICE + " REAL NOT NULL, " +
                CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE + " TEXT NOT NULL, " +
                CompanyContract.CompanyEntry.COLUMN_HIGH + " REAL NOT NULL, " +
                CompanyContract.CompanyEntry.COLUMN_LOW + " REAL NOT NULL, " +
                CompanyContract.CompanyEntry.COLUMN_PERCENTAGE + " REAL);";

        sqLiteDatabase.execSQL(SQL_CREATE_COMPANY_TABLE);

    }

    //This method is called everytime the version of database changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CompanyContract.CompanyEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
