package com.example.findcoffee.ui.home;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.findcoffee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class jsonAPIGetter {

    private String baseURL = "https://data.melbourne.vic.gov.au/resource/xt2y-tnn9.json?";
    private String areaSearch = "clue_small_area=";
    private String nameSearch = "trading_name=";
    private float xSearch;
    private float ySearch;
    private JSONArray responseJson;
    private JSONObject coffeeShop;
    private ArrayList<String> streetAddress;
    private ArrayList<String> tradingNames;


    public boolean search(String query, int searchType){
        String queryType = nameSearch;
        switch(searchType){
            case 1:
                queryType = areaSearch;
                break;
        }
        String url = baseURL+queryType+query;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        if(response!=null){
                            try{
                                responseJson = new JSONArray(response);
                                ArrayList<Integer> visited = new ArrayList<Integer>(responseJson.length());
                                int shopCount = 0;
                                for(int i=0;i<responseJson.length();i++)
                                {
                                    coffeeShop = responseJson.getJSONObject(i);
                                    int censusYear = coffeeShop.getInt("census_year");
                                    int base_property_id = coffeeShop.getInt("base_property_id");
                                    boolean checkVisited = visited.contains(base_property_id);
                                    if(! checkVisited){
                                        visited.add(base_property_id);
                                        if(coffeeShop.has("street_address"))
                                        {
                                            streetAddress.add(coffeeShop.getString("street_address"));
                                        }
                                        if(coffeeShop.has("trading_name"))
                                        {
                                            tradingNames.add(coffeeShop.getString("trading_name"));
                                        }
                                        shopCount += 1;
                                    }
                                }
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }else{
                            responseJson = null;
                            streetAddress = null;
                            tradingNames = null;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            textView.setText("That didn't work!");
                Log.d("Response", "Did Not work");


            }
        });
        if(responseJson == null){
            return false;
        }
        else{
            return true;
        }
    }

    public ArrayList<String> getTradingNames(){
        return tradingNames;
    }

    public ArrayList<String> getStreetAddress() {
        return streetAddress;
    }
}
