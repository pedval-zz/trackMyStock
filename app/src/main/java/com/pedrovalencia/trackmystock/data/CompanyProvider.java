package com.pedrovalencia.trackmystock.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * Created by pedrovalencia on 26/10/14.
 */
public class CompanyProvider extends ContentProvider {

    private static final int COMPANY = 100;
    private static final int COMPANY_WITH_SYMBOL = 101;


    private CompanyDbHelper mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final SQLiteQueryBuilder sCompanyQueryBuilder;

    static {
        sCompanyQueryBuilder = new SQLiteQueryBuilder();
        sCompanyQueryBuilder.setTables(CompanyContract.CompanyEntry.TABLE_NAME);
    }

    //Obtain all companies in the table
    private Cursor getCompany(Uri uri) {
        return sCompanyQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                null,
                null,
                null,
                null,
                null);

    }

    //Obtain the company filtered by the symbol
    private Cursor getCompanyBySymbol(Uri uri, String[] projection) {

        String symbol = CompanyContract.CompanyEntry.getSymbolFromUri(uri);
        return sCompanyQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                CompanyContract.CompanyEntry.COLUMN_SYMBOL + " = ?",
                new String[]{symbol},
                null,
                null,
                null);

    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new CompanyDbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            //"company"
            case COMPANY:
            {
                retCursor = getCompany(uri);
                break;
            }
            //"company/*"
            case COMPANY_WITH_SYMBOL:
            {
                retCursor = getCompanyBySymbol(uri, projection);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);

        }
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case COMPANY:
                return CompanyContract.CompanyEntry.CONTENT_TYPE;

            case COMPANY_WITH_SYMBOL:
                return CompanyContract.CompanyEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }

    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri = null;

        switch(match) {
            case COMPANY:
            {
                long _id = db.insert(CompanyContract.CompanyEntry.TABLE_NAME, null, contentValues);
                if(_id > 0) {
                    returnUri = CompanyContract.CompanyEntry.buildCompanyUri(contentValues.getAsString(CompanyContract.CompanyEntry.COLUMN_SYMBOL));
                }
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case COMPANY: {
                rowsDeleted = db.delete(CompanyContract.CompanyEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case COMPANY_WITH_SYMBOL: {
                String symbol = CompanyContract.CompanyEntry.getSymbolFromUri(uri);
                rowsDeleted = db.delete(CompanyContract.CompanyEntry.TABLE_NAME,
                        CompanyContract.CompanyEntry.COLUMN_SYMBOL + " = ?",
                        new String[]{symbol});
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if(null == selection || 0 != rowsDeleted) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case COMPANY_WITH_SYMBOL: {
                String symbol = CompanyContract.CompanyEntry.getSymbolFromUri(uri);
                rowsUpdated = db.update(CompanyContract.CompanyEntry.TABLE_NAME,
                        contentValues,
                        CompanyContract.CompanyEntry.COLUMN_SYMBOL + " = ?",
                        new String[]{symbol});
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }
        if(0 != rowsUpdated) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;

    }

    private static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String auth = CompanyContract.CONTENT_AUTHORITY;

        matcher.addURI(auth,CompanyContract.PATH_COMPANY,COMPANY);
        matcher.addURI(auth,CompanyContract.PATH_COMPANY + "/*",COMPANY_WITH_SYMBOL);

        return matcher;

    }

}
