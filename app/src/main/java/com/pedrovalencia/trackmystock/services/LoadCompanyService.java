package com.pedrovalencia.trackmystock.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by pedrovalencia on 07/11/14.
 */
public class LoadCompanyService extends Service{

    private static final String TAG = LoadCompanyService.class.getSimpleName();

    private static final Object sSyncAdapterLock = new Object();
    private static TrackMyStockSyncAdapter sTrackMyStockSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate - LoadCompanyService");
        synchronized (sSyncAdapterLock) {
            if (sTrackMyStockSyncAdapter == null) {
                sTrackMyStockSyncAdapter = new TrackMyStockSyncAdapter(getApplicationContext(), true);
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
