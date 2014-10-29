package com.pedrovalencia.trackmystock.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by pedrovalencia on 26/10/14.
 */
public class CompanyContract {

    public static final String CONTENT_AUTHORITY = "com.pedrovalencia.trackmystock.app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_COMPANY = "company";

    public static final class CompanyEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COMPANY).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" +
                        PATH_COMPANY;

        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" +
                        PATH_COMPANY;

        //Table company
        public static final String TABLE_NAME = "company";

        //Columns
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_LAST_UPDATE = "last_update";
        public static final String COLUMN_HIGH = "high";
        public static final String COLUMN_LOW = "low";
        public static final String COLUMN_CHANGE = "change";

        public static Uri buildCompanyUri(String symbol) {
            return CONTENT_URI.buildUpon().appendPath(symbol).build();
        }

        public static String getSymbolFromUri(Uri uri) {
            return uri.getPathSegments().get(1);
        }

    }
}
