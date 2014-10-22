package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.view.MenuInflater;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.tester.android.view.TestMenu;
import org.robolectric.tester.android.view.TestMenuItem;
import org.robolectric.util.ActivityController;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by pedrovalencia on 22/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class DetailActivityTest {

    private ActivityController activityController;

    @Before
    public void setUp() throws Exception {
        activityController = Robolectric.buildActivity(DetailActivity.class).create();
    }

    @After
    public void tearDown() throws Exception {
        activityController.destroy();
    }

    @Test
    public void testActivityNotNull() throws Exception {
        Activity activity = (Activity)activityController.get();
        assertTrue("Activity is null", activity != null);
    }

    @Test
    public void testOnCreateOptionsMenu() throws Exception {

        Activity activity = (Activity)activityController.get();

        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.detail, testMenu);

        activity.onCreateOptionsMenu(testMenu);

        //Test the first item is Settings
        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);
        assertTrue("First menu is not Settings: " + menuItem.getItemId(),
                menuItem.getItemId() == R.id.action_settings);

    }

    @Test
    public void testOnOptionsItemSelected() throws Exception {

        Activity activity = (Activity)activityController.get();
        //Simulate a Menu object
        TestMenu testMenu = new TestMenu(Robolectric.application);
        new MenuInflater(Robolectric.application).inflate(R.menu.detail, testMenu);

        TestMenuItem menuItem = (TestMenuItem)testMenu.getItem(0);

        //TODO test we go to SettingsActivity
        activity.onOptionsItemSelected(menuItem);
        menuItem.click();

        menuItem.setItemId(0);
        activity.onOptionsItemSelected(menuItem);
        menuItem.click();
    }

    @Test
    public void testElements() {
        Activity activity = (Activity)activityController.start().resume().get();

        //Name label
        TextView nameLabel = (TextView)activity.findViewById(R.id.detail_fragment_name_label);
        assertNotNull("Name element does not exist", nameLabel);
        assertTrue("Name label text does not match", nameLabel.getText().toString().equals("Name"));

        //Name content
        TextView nameContent = (TextView)activity.findViewById(R.id.detail_fragment_name_content);
        assertNotNull("Name content does not exist", nameContent);

        //Price label
        TextView priceLabel = (TextView)activity.findViewById(R.id.detail_fragment_price_label);
        assertNotNull("Price element does not exist", priceLabel);
        assertTrue("Price label text does not match", priceLabel.getText().toString().equals("Price"));

        //Price content
        TextView priceContent = (TextView)activity.findViewById(R.id.detail_fragment_price_content);
        assertNotNull("Price content does not exist", priceContent);

        //Date label
        TextView dateLabel = (TextView)activity.findViewById(R.id.detail_fragment_date_label);
        assertNotNull("Date label does not exist", dateLabel);
        assertTrue("Date label text does not match", dateLabel.getText().toString().equals("Last update"));

        //Date content
        TextView dateContent = (TextView)activity.findViewById(R.id.detail_fragment_date_content);
        assertNotNull("Date content does not exist", dateContent);

        //High label
        TextView highLabel = (TextView)activity.findViewById(R.id.detail_fragment_high_label);
        assertNotNull("High label does not exist", highLabel);
        assertTrue("High label text does not match", highLabel.getText().toString().equals("High"));

        //High content
        TextView highContent = (TextView)activity.findViewById(R.id.detail_fragment_high_content);
        assertNotNull("High content does not exist", highContent);

        //Low label
        TextView lowLabel = (TextView)activity.findViewById(R.id.detail_fragment_low_label);
        assertNotNull("High label does not exist", lowLabel);
        assertTrue("Low label text does not match", lowLabel.getText().toString().equals("Low"));

        //Low content
        TextView lowContent = (TextView)activity.findViewById(R.id.detail_fragment_low_content);
        assertNotNull("Low content does not exist", lowContent);
    }
}
