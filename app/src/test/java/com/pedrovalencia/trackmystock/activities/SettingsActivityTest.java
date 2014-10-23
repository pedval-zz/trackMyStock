package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.pedrovalencia.trackmystock.R;

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
    public void testWhenSelectWeek() throws Exception {
        SettingsActivity activity = (SettingsActivity)activityController.get();

        assertNotNull("SettingsActivity is null", activity);
        String historic_key = activity.getString(R.string.pref_historic_key);
        Preference preference = activity.findPreference(historic_key);
        //activity.onPreferenceChange(preference,"month");
        String historic_value =
                PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString("historic_range","");
        /*assertTrue("Historic range values does not match: "+historic_value,
                historic_value.equals("week"));*/
    }




}
