package com.pedrovalencia.trackmystock.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 27/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CompanyProviderTest {

    private CompanyProvider mCompanyProvider;

    //Start with a clean DB state
    @Before
    public void setUp() throws Exception{
        mCompanyProvider = new CompanyProvider();
        mCompanyProvider.onCreate();
    }

    @After
    public void tearDown() throws Exception {
        mCompanyProvider.delete(CompanyContract.CompanyEntry.CONTENT_URI, null, null);
    }

    @Test
    public void testInsertElement() throws Exception {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, "GOOG");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_PERCENTAGE, 2.32);

        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertTrue("Query did not return expected row number: " + cursor.getCount(),
                cursor.getCount() == 1);

    }


    /*@Test
    public void deleteAllRecordsTest() throws Exception {
        //First, we delete all records
        mCompanyProvider.delete(
                CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null);


        //Try to get any record from DB
        Cursor cursor = mCompanyProvider.query(
                CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        //Test that the table is empty
        assertTrue("Table \"company\" is not empty", cursor.getCount() == 0);
        cursor.close();
    }*/





}
