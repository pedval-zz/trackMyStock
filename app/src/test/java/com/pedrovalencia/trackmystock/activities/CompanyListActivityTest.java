package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;

import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by valenciap on 21/10/2014.
 */
@Config(emulateSdk=18)
@RunWith(RobolectricTestRunner.class)
public class CompanyListActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(CompanyListActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        Activity activity = (Activity)activityController.get();
        activity.getContentResolver().delete(
                CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null);
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);
    }

}
