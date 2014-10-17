package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 15/10/2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CoverActivityTest  {

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = Robolectric.buildActivity(CoverActivity.class).create().get();
        assertTrue("Activity is null", activity != null);
    }


    @Test
    public void testTextInActivity() throws Exception {
        Activity activity = Robolectric.buildActivity(CoverActivity.class).create().get();
        TextView coverTextView = (TextView) activity.findViewById(R.id.fullscreen_content);
        assertTrue("Wrong cover text",
                coverTextView.getText().equals(activity.getString(R.string.main_title_text)));
        assertTrue("Wrong cover text color",
                coverTextView.getCurrentTextColor() == activity.getResources().getColor(R.color.main_text_color));
    }

    @Test
    public void testGoToNextActivity() throws Exception {
        //Create the activity and simulate the onResume() event.
        ActivityController activityController = Robolectric.buildActivity(CoverActivity.class).create().start().resume();

        //Test the activity is not null
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);

        //Test we move to next activity (MainActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not MainActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(MainActivity.class.getCanonicalName()));
    }

    @Test
    public void testInterruptedException() throws Exception {
        //Create the activity and simulate the onResume() event.
        ActivityController activityController = Robolectric.buildActivity(CoverActivity.class).create().start().resume();


    }


}
