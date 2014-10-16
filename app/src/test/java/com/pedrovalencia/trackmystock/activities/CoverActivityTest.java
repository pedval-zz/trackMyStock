package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 15/10/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CoverActivityTest  {

    private Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(CoverActivity.class).create().get();
    }

    @After
    public void tearDown() throws Exception {
        activity.finish();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        assertTrue("Activity is null", activity != null);
    }

    @Test
    public void testTextInActivity() throws Exception {
        TextView coverTextView = (TextView) activity.findViewById(R.id.fullscreen_content);
        assertTrue("Wrong cover text",
                coverTextView.getText().equals(activity.getString(R.string.main_title_text)));
        assertTrue("Wrong cover text color",
                coverTextView.getCurrentTextColor() == activity.getResources().getColor(R.color.main_text_color));

    }


}
