package com.pedrovalencia.trackmystock.services;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.domain.CompanyDetail;
import com.pedrovalencia.trackmystock.util.CompanySearchUtil;

/**
 * Created by pedrovalencia on 07/11/14.
 */
public class TrackMyStockSyncAdapter extends AbstractThreadedSyncAdapter {

    //Every 30 min
    public static final int SYNC_INTERVAL = 30 * 60 * 1000;

    public TrackMyStockSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        //Iterate all stored companies and update the details

        //1.- Get all company symbols
        Cursor cursor = getContext().getContentResolver().query(CompanyContract.CompanyEntry.CONTENT_URI,
                null, null, null, null);

        //2.- Iterate for all companies and update the details
        if(cursor.moveToFirst()) {
            while(cursor.moveToNext()) {
                String symbol = cursor.getString(CompanyContract.CompanyEntry.COLUMN_SYMBOL_POS);
                CompanyDetail companyNewDetail = CompanySearchUtil.getDetail(symbol);

                ContentValues newValues = new ContentValues();
                newValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, companyNewDetail.getDate());
                newValues.put(CompanyContract.CompanyEntry.COLUMN_PRICE, companyNewDetail.getPrice());
                newValues.put(CompanyContract.CompanyEntry.COLUMN_HIGH, companyNewDetail.getHigh());
                newValues.put(CompanyContract.CompanyEntry.COLUMN_LOW, companyNewDetail.getLow());

                getContext().getContentResolver().update(CompanyContract.CompanyEntry.buildCompanyUri(symbol),
                        newValues,
                        null,
                        null);
            }
        }
        //Close cursor
        cursor.close();
    }

    public static void initializeSyncAdapter(Context context) {
        TrackMyStockSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL);
    }

    public static void configurePeriodicSync(Context context, int syncInterval) {

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), "default_account");
        String authority = "default_authority";

        ContentResolver.addPeriodicSync(newAccount,
                authority, new Bundle(), syncInterval);

    }
}
