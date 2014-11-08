package com.pedrovalencia.trackmystock.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;
import com.pedrovalencia.trackmystock.domain.CompanyDetail;
import com.pedrovalencia.trackmystock.util.CompanySearchUtil;

/**
 * Created by pedrovalencia on 07/11/14.
 */
public class TrackMyStockSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = TrackMyStockSyncAdapter.class.getSimpleName();

    //Every 1 hour
    public static final int SYNC_INTERVAL = 60*60;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL/3;

    private ContentResolver mContentResolver;

    public TrackMyStockSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        //Iterate all stored companies and update the details
        Log.d(TAG, "New iteration");

        //1.- Get all company symbols
        Cursor cursor = mContentResolver.query(CompanyContract.CompanyEntry.CONTENT_URI,
                null, null, null, null);

        //2.- Iterate for all companies and update the details
        while(cursor.moveToNext()) {
            String symbol = cursor.getString(CompanyContract.CompanyEntry.COLUMN_SYMBOL_POS);
            CompanyDetail companyNewDetail = CompanySearchUtil.getDetail(symbol);

            ContentValues newValues = new ContentValues();
            newValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, companyNewDetail.getDate());
            newValues.put(CompanyContract.CompanyEntry.COLUMN_PRICE, companyNewDetail.getPrice());
            newValues.put(CompanyContract.CompanyEntry.COLUMN_HIGH, companyNewDetail.getHigh());
            newValues.put(CompanyContract.CompanyEntry.COLUMN_LOW, companyNewDetail.getLow());

            mContentResolver.update(CompanyContract.CompanyEntry.buildCompanyUri(symbol),
                    newValues,
                    null,
                    null);
        }

        //Close cursor
        cursor.close();
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }


    /**
     * Helper method to have the sync adapter sync immediately
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if ( null == accountManager.getPassword(newAccount) ) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */

            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        TrackMyStockSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
