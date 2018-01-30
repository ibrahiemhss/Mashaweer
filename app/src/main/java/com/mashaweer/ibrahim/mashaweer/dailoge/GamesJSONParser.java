package com.mashaweer.ibrahim.mashaweer.dailoge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrahim on 15/12/17.
 */

public class GamesJSONParser {
    static List<ListRate> rateList;

    public static List<ListRate> parseData(String content) {

        JSONArray jsonArray = null;
        ListRate rate = null;
        try {

            jsonArray = new JSONArray(content);
            rateList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject obj = jsonArray.getJSONObject(i);
                rate = new ListRate();

                rate.setRatingString(obj.getString("driv_rating"));

                rateList.add(rate);
            }
            return rateList;

        }
        catch (JSONException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
