package com.pedrovalencia.trackmystock.adapters;

import android.app.Activity;

import com.pedrovalencia.trackmystock.activities.CompanyListActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 22/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CompanyListAdapterTest {

    @Test
    public void testCreationAdapter() {
        Activity activity = Robolectric.buildActivity(CompanyListActivity.class).create().get();
        CompanyListAdapter companyListAdapter = new CompanyListAdapter(activity, null, 0);
        assertTrue("CompanyListAdapter is null", companyListAdapter != null);

        activity.finish();
    }
}
