package com.pedrovalencia.trackmystock.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by pedrovalencia on 27/10/14.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CompanyProviderTest {

    private CompanyProvider mCompanyProvider;

    @Rule
    public ExpectedException exception = ExpectedException.none();

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
        ContentValues contentValues = getContentValues("GOOGL");

        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertTrue("Query did not return expected row number (1): " + cursor.getCount(),
                cursor.getCount() == 1);

    }

    @Test
    public void testInsertElementWrongUri() throws Exception {
        ContentValues contentValues = getContentValues("GOOGL");

        //We're ready for the exception thrown
        exception.expect(UnsupportedOperationException.class);

        mCompanyProvider.insert(getWrongUri(), contentValues);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertTrue("Query did not return expected row number (0): " + cursor.getCount(),
                cursor.getCount() == 0);
        cursor.close();

    }

    /*@Test
    public void testInsertSameElementTwice() throws Exception {
        ContentValues contentValues = getContentValues("GOOGL");

        //First insertion
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //We're ready for the exception thrown
        exception.expect(android.database.SQLException.class);

        //Second insertion
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        assertTrue("Query did not return expected row number (1): " + cursor.getCount(),
                cursor.getCount() == 1);
        cursor.close();

    }*/

    @Test
    public void deleteAllRecordsTest() throws Exception {
        ContentValues contentValues = getContentValues("GOOGL");

        //Insert a row
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //Then delete all records
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
    }

    @Test
    public void deleteBySymbolTest() throws Exception{
        //Insert a row with symbol GOOGL
        ContentValues contentValues = getContentValues("GOOGL");
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //Insert a row with symbol GOOG
        contentValues = getContentValues("GOOG");
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //Then delete all records
        mCompanyProvider.delete(
                CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                null,
                null);

        //Try to get the element just deleted
        Cursor cursor = mCompanyProvider.query(
                CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                null,
                null,
                null,
                null
        );

        //Test the row with symbol GOOGL has been deleted
        assertTrue("Row with symbol GOOGL has not been deleted",
                cursor.getCount() == 0);


        cursor = mCompanyProvider.query(
                CompanyContract.CompanyEntry.buildCompanyUri("GOOG"),
                null,
                null,
                null,
                null
        );
        assertTrue("Row with symbol GOOG is still in table",
                cursor.getCount() == 1);
        cursor.close();

    }

    @Test
    public void deleteAllRecordsWrongUri() throws Exception {

        //We're ready for the exception thrown
        exception.expect(UnsupportedOperationException.class);

        mCompanyProvider.delete(getWrongUri(), null, null);

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
    }

    @Test
    public void queryWithWrongUri() throws Exception {
        //Insert a row with symbol GOOGL
        ContentValues contentValues = getContentValues("GOOGL");
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //We're ready for the exception thrown
        exception.expect(UnsupportedOperationException.class);

        //Try to get any record from DB
        Cursor cursor = mCompanyProvider.query(
                getWrongUri(),
                null,
                null,
                null,
                null
        );
        assertNull("Cursor is not null", cursor);

    }

    @Test
    public void updateElementTest() throws Exception {
        //Insert a row with symbol GOOGL
        ContentValues contentValues = getContentValues("GOOGL");
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            String lastUpdate = (String)contentValues.get(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE);
            assertTrue("Date does not match",lastUpdate.equals("10272014"));
        }


        //update the row changing a value
        ContentValues newValues = new ContentValues();
        newValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10282014");
        mCompanyProvider.update(CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                newValues,
                null,
                null);

        cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst()) {
            String lastUpdate = (String)newValues.get(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE);
            assertTrue("Date does not match",lastUpdate.equals("10282014"));
        }
        cursor.close();
    }

    @Test
    public void updateElementWithWrongUri() throws Exception {
        //Insert a row with symbol GOOGL
        ContentValues contentValues = getContentValues("GOOGL");
        mCompanyProvider.insert(CompanyContract.CompanyEntry.CONTENT_URI,
                contentValues);

        //update the row changing a value
        ContentValues newValues = new ContentValues();
        newValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10282014");
        //We're ready for the exception thrown
        exception.expect(UnsupportedOperationException.class);
        mCompanyProvider.update(getWrongUri(),
                newValues,
                null,
                null);

        Cursor cursor = mCompanyProvider.query(CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"),
                null,
                null,
                null,
                null);

        assertNull("Cursor is not null", cursor);
        cursor.close();

    }

    @Test
    public void testGetType() throws Exception {
        String type = mCompanyProvider.getType(CompanyContract.CompanyEntry.CONTENT_URI);
        assertTrue("Content type does not match", type.equals("vnd.android.cursor.dir/com.pedrovalencia.trackmystock.app/company"));

        type = mCompanyProvider.getType(CompanyContract.CompanyEntry.buildCompanyUri("GOOGL"));
        assertTrue("Content type does not match",type.equals("vnd.android.cursor.item/com.pedrovalencia.trackmystock.app/company"));
    }

    @Test
    public void testGetTypeWrongUri() throws Exception {
        //We're ready for the exception thrown
        exception.expect(UnsupportedOperationException.class);

        String type = mCompanyProvider.getType(getWrongUri());
        assertNull("Content type is not null", type);
    }


    //Populates the content values
    private ContentValues getContentValues(String symbol) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_SYMBOL, symbol);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_NAME, "Google Inc.");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LAST_UPDATE, "10272014");
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_PRICE, 30.20);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_HIGH, 32.15);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_LOW, 30.12);
        contentValues.put(CompanyContract.CompanyEntry.COLUMN_CHANGE, "+2.32");

        return contentValues;
    }

    //Creates a wrong uri using "conpany" as PATH
    private Uri getWrongUri() {
        return Uri.parse("content://com.pedrovalencia.trackmystock.app").buildUpon().
                appendPath("conpany").build();
    }

}
