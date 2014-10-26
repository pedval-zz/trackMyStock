package com.pedrovalencia.trackmystock.util;

import android.util.Log;

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

    private static final String REQUEST_PREFIX = "http://autoc.finance.yahoo.com/autoc?query=";
    private static final String REQUEST_POSTFIX = "&callback=YAHOO.Finance.SymbolSuggest.ssCallback";

    private static final String LOG_TAG = CompanySearchUtil.class.getSimpleName();

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

        try {
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
        }  catch (IOException ex) {
            Log.e(LOG_TAG, "Error trying to connect to external API", ex);
            return companyList;

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

    //Remove the prefix and last bracket
    private static String removeNoJSONPart(String response) {
        return response.replace("YAHOO.Finance.SymbolSuggest.ssCallback(","").replace(")","");
    }
}
