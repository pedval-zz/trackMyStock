package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.preference.ListPreference;
import android.preference.Preference;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 23/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SettingsActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(SettingsActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.start().resume().get();
        assertTrue("SettingsActivity is null", activity != null);
    }

    @Test
    public void testElements() throws Exception {
        SettingsActivity activity = (SettingsActivity)activityController.start().resume().get();

        assertNotNull("SettingsActivity is null", activity);

        Preference preference = activity.findPreference("historic_range");
        assertNotNull("Preference object is null", preference);
        assertTrue("Preference is not instance of ListPreference", preference instanceof ListPreference);

        ListPreference listPreference = (ListPreference)preference;

        //Week
        int prefIndex = listPreference.findIndexOfValue("week");
        assertTrue("Index of week element is not 0", prefIndex == 0);

        //Month
        prefIndex = listPreference.findIndexOfValue("month");
        assertTrue("Index of month element is not 1", prefIndex == 1);

    }




}
