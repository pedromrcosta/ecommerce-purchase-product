package com.pedrocosta.ecommerceproject.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;

public class IpmaApiRequest {

    // TODO TO-DO TO DO Encapsulate connection , it repeats 2x

    private final static String PREVISION_REQUEST = "https://api.ipma.pt/open-data/forecast/meteorology/cities/daily/";
    private final static String DISTRICT_CODE_REQUEST = "https://api.ipma.pt/open-data/distrits-islands.json";

    /**
    * TODO DOC THIS
    **/

    public static String getPrevision(String deliveryCity, DayOfWeek dayOfWeek ) throws JSONException, IOException {
        final String districtCode = getDistrictCode(deliveryCity);

        System.out.println(districtCode);

        URL url = new URL(PREVISION_REQUEST + districtCode + ".json");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        JSONArray districtPrevisions = new JSONArray(new JSONObject(content.toString()).getString("data"));

        if (dayOfWeek.ordinal() == 4 || dayOfWeek.ordinal() == 5) {
            Integer previsionAverage = 0;
            for (int i = 1; i <= districtPrevisions.length(); i++) {
                Integer previsionDaily = (int) Double.parseDouble(new JSONObject(districtPrevisions.get(i-1).toString()).getString("precipitaProb"));
                previsionAverage += previsionDaily;
            }
            previsionAverage = previsionAverage / 5;
            return previsionAverage.toString();
        }

        return new JSONObject(districtPrevisions.get(0).toString()).getString("precipitaProb");
    }

    /**
     * Gets the district code by requestion distrits-islands.json
     * @param deliveryCity - The city to get the globalIdCode code
     * @return a globalIdCode of that city ( if it exists ) or null
     * */
    private static String getDistrictCode(String deliveryCity) throws IOException, JSONException {
        URL url = new URL(DISTRICT_CODE_REQUEST);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        con.disconnect();

        JSONArray ipmaCodes = new JSONArray(new JSONObject(content.toString()).getString("data"));

        String districtCode = null;

        for (int i = 0; i < ipmaCodes.length(); i++) {
            JSONObject district = (new JSONObject(ipmaCodes.get(i).toString()));
            String districtName = district.getString("local");
            if (districtName.equals(deliveryCity)) {
                districtCode = district.getString("globalIdLocal");
                break;
            }
        }

        return districtCode;
    }



}
