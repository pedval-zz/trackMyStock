package com.pedrovalencia.trackmystock.util;

import android.util.Log;

import com.pedrovalencia.trackmystock.domain.CompanyDetail;
import com.pedrovalencia.trackmystock.domain.CompanySignature;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by pedrovalencia on 24/10/14.
 */
public class CompanySearchUtil {

    //Request prefix and postfix for the getCompany query
    private static final String REQUEST_PREFIX = "http://autoc.finance.yahoo.com/autoc?query=";
    private static final String REQUEST_POSTFIX = "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";

    //Request prefix and postfix for detail query
    private static final String DETAIL_REQUEST_PREFIX =
            "https://query.yahooapis.com/v1/public/yql?q=select * from csv where url='http://download.finance.yahoo.com/d/quotes.csv?s=";
    private static final String DETAIL_REQUEST_POSTFIX =
            "&f=nl1d1t1c1hg&e=.csv' and columns='name,price,date,time,change,high,low'&format=json&env=store://datatables.org/alltableswithkeys";


    private static final String LOG_TAG = CompanySearchUtil.class.getSimpleName();

    /**
     * Get a list of CompanySignature (name + symbol)
     * @param query
     * @return
     */
    public static ArrayList<CompanySignature> getCompany(String query) {
        //Contains the result
        ArrayList<CompanySignature> companyList = null;
        //Connection object
        HttpURLConnection conn = null;
        //Contains the JSON response
        StringBuilder jsonResults = new StringBuilder();

        //Object that contains the request
        StringBuilder request =  new StringBuilder();
        request.append(REQUEST_PREFIX).append(query).append(REQUEST_POSTFIX);

        try{
            //Create the url from the request string
            URL url = new URL(request.toString());

            //Create connection
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader inReader = new InputStreamReader(conn.getInputStream());

            // And oad the results into a StringBuilder
            int read;
            char[] buff = new char[1024];

            while ((read = inReader.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (IOException ex) {
            Log.e(LOG_TAG, "Error trying to connect to external API", ex);
            return null;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(removeNoJSONPart(jsonResults.toString()));
            JSONArray resultJsonArray = jsonObj.getJSONObject("ResultSet").getJSONArray("Result");

            // Extract the Place descriptions from the results
            companyList = new ArrayList<CompanySignature>(resultJsonArray.length());
            for (int i = 0; i < resultJsonArray.length(); i++) {
                CompanySignature company = new CompanySignature(
                        resultJsonArray.getJSONObject(i).getString("name"),
                        resultJsonArray.getJSONObject(i).getString("symbol"));
                companyList.add(company);
            }
        } catch (JSONException ex) {
            Log.e(LOG_TAG, "Error parsing JSON", ex);
            return companyList;
        }

        return companyList;
    }

    /**
     * Get the company detail info searching by its symbol
     * @param symbol
     * @return
     */
    public static CompanyDetail getDetail(String symbol) {
        //Contains the result
        CompanyDetail companyDetail = null;
        //Connection object
        HttpURLConnection conn = null;
        //Contains the JSON response
        StringBuilder jsonResults = new StringBuilder();

        //Object that contains the request
        StringBuilder request =  new StringBuilder();
        request.append(DETAIL_REQUEST_PREFIX).append(symbol).append(DETAIL_REQUEST_POSTFIX);

        try{
            //Create the url from the request string
            URL url = new URL(request.toString());

            //Create connection
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader inReader = new InputStreamReader(conn.getInputStream());

            // And oad the results into a StringBuilder
            int read;
            char[] buff = new char[1024];

            while ((read = inReader.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (IOException ex) {
            Log.e(LOG_TAG, "Error trying to connect to external API", ex);
            return null;

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(removeNoJSONPart(jsonResults.toString()));
            JSONArray resultJsonArray = jsonObj.getJSONObject("query").getJSONObject("results").getJSONArray("row");

            // Extract the Place descriptions from the results
            if(resultJsonArray.length() > 0) {
                companyDetail = new CompanyDetail(
                        new CompanySignature(
                                resultJsonArray.getJSONObject(0).getString("name"),
                                resultJsonArray.getJSONObject(0).getString("symbol")
                        ),
                        Double.parseDouble(resultJsonArray.getJSONObject(0).getString("price")),
                        resultJsonArray.getJSONObject(0).getString("date") + resultJsonArray.getJSONObject(0).getString("time"),
                        Double.parseDouble(resultJsonArray.getJSONObject(0).getString("high")),
                        Double.parseDouble(resultJsonArray.getJSONObject(0).getString("low")),
                        resultJsonArray.getJSONObject(0).getString("change")
                );
            }

        } catch (JSONException ex) {
            Log.e(LOG_TAG, "Error parsing JSON", ex);
            return null;
        }

        return companyDetail;

    }

    /**
     * Get the company historic by its symbol
     * @param
     * @return
     */
    /*public static Historic getHistoric(String symbol) {

    }*/

    //Remove the prefix and last bracket
    private static String removeNoJSONPart(String response) {
        return response.replace("YAHOO.Finance.SymbolSuggest.ssCallback(","").replace(")","");
    }
}
