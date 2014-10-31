package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;
import com.pedrovalencia.trackmystock.data.CompanyContract;

import org.junit.After;
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
    public void testGoToNextActivityWithNoValue() throws Exception {
        //Create the activity and simulate the onResume() event.
        ActivityController activityController = Robolectric.buildActivity(CoverActivity.class).create();

        //Test the activity is not null
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);

        //Delete all values before starting the activity
        activity.getContentResolver().delete(CompanyContract.CompanyEntry.CONTENT_URI, null, null);
        //Start the activity
        activityController.start().resume();
        activity = (Activity)activityController.get();

        //Test we move to next activity (MainActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not MainActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(EmptyListActivity.class.getCanonicalName()));
    }

    @Test
    public void testGoToNextActivityWithValue() throws Exception {
        //Create the activity and simulate the onResume() event.
        ActivityController activityController = Robolectric.buildActivity(CoverActivity.class).create();

        //Test the activity is not null
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);

        ContentValues contentValues = new ContentValues();
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "ANOTHER");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        //Delete all values before starting the activity
        activity.getContentResolver().insert(CompanyContract.CompanyEntry.CONTENT_URI, contentValues);
        //Start the activity
        activityController.start().resume();
        activity = (Activity)activityController.get();

        //Test we move to next activity (MainActivity) from onResume() event.
        Intent intent = Robolectric.shadowOf(activity).peekNextStartedActivity();
        assertTrue("Type of activity is not MainActivity class: "+intent.getComponent().getClassName(),
                intent.getComponent().getClassName().equals(CompanyListActivity.class.getCanonicalName()));
    }


}
