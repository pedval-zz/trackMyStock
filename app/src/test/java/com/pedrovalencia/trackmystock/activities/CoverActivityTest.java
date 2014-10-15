package com.pedrovalencia.trackmystock.activities;

import android.app.Activity;
import android.widget.TextView;

import com.pedrovalencia.trackmystock.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 15/10/2014.
 */
@RunWith(RobolectricTestRunner.class)
public class CoverActivityTest  {

    @Test
    public void testElementsInActivity() throws Exception {
        Activity activity = Robolectric.buildActivity(CoverActivity.class).create().get();
        TextView coverTextView = (TextView) activity.findViewById(R.id.fullscreen_content);

        assertTrue("DUMMY\\nCONTENT".equals(coverTextView.getText()));

    }

}
