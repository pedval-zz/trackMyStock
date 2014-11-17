package com.pedrovalencia.trackmystock.fragments;

import android.os.Bundle;

import com.pedrovalencia.trackmystock.activities.DetailActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;
import org.robolectric.util.FragmentTestUtil;

import static org.junit.Assert.assertNotNull;

/**
 * Created by pedrovalencia on 17/11/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class HistoricFragmentTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        Bundle arg = new Bundle();
        arg.putString(DetailActivity.SYMBOL, "GOOG");
        activityController = Robolectric.buildActivity(DetailActivity.class).create(arg);
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testFragment() {
        //Start PlaceholderFragment
        HistoricFragment historicFragment = new HistoricFragment();
        FragmentTestUtil.startFragment(historicFragment);

        assertNotNull("Fragment has not been initialized", historicFragment);
    }
}
