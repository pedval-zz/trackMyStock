package com.pedrovalencia.trackmystock.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by pedrovalencia on 08/11/14.
 */
public class CompanyAuthenticatorService  extends Service {
    // Instance field that stores the authenticator object
    private CompanyAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new CompanyAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
