

package com.example.findcoffee.ui.home;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class zomatoApiGetter extends FragmentActivity {

    private String baseURL = "https://developers.zomato.com/api/v2.1/search?";
    //    private String areaSearch = "clue_small_area=";
//    private String nameSearch = "trading_name=";
//    private float xSearch;
//    private float ySearch;
    private JSONArray getCafeList;
    private JSONObject coffeeShop;
    //private ArrayList<String> streetAddress = new ArrayList<>();
    //private ArrayList<String> tradingNames  = new ArrayList<>();
//    private String streetAddress;
//    private String tradingNames;


    public void search(Map<String, String> searchString, final HomeFragment fragment, final int maxEntries){

//        String queryType = nameSearch;

        String query = "";
        for (String key : searchString.keySet())
        {
            String value = searchString.get(key);
            query = query+key+value+"&";
        }

        String url = baseURL+query;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        if(response!=null){



                            try{
                                JSONObject getResponseJSON = new JSONObject(response);


                                // Getting JSON Array node
                                getCafeList = getResponseJSON.getJSONArray("restaurants");

                                ArrayList<Integer> visited = new ArrayList<Integer>(getCafeList.length());
                                int shopCount = 0;
                                int i = 0;
                                while(i < getCafeList.length() && shopCount < maxEntries)
                                {
                                    coffeeShop = getCafeList.getJSONObject(i);
                                    JSONObject coffeeShopDetails = coffeeShop.getJSONObject("restaurant");
                                   String name = coffeeShopDetails.getString("name");
                                    String getThumb = coffeeShopDetails.getString("thumb");

                                    JSONObject coffeeShopLocation = coffeeShopDetails.getJSONObject("location");
                                    String address = coffeeShopLocation.getString("address");

//                                    JSONArray coffeeShopLocation = coffeeShop.getJSONArray("location");
//                                    String address = null;
//                                    // iterate to jsonArrayData
//                                    for (int j = 0; j < coffeeShopLocation.length(); j++) {
//                                        JSONObject coffeeShopLocationObject = coffeeShopLocation.getJSONObject(j);
//                                        address = coffeeShopLocationObject.getString("address");
//                                    }



                                    fragment.drawShop(name,address,getThumb);

//                                    int censusYear = coffeeShop.getInt("census_year");
//                                    int base_property_id = coffeeShop.getInt("base_property_id");
//                                    boolean checkVisited = visited.contains(base_property_id);
                                    i += 1;
//                                    if(! checkVisited){
//                                        visited.add(base_property_id);
//                                        if(coffeeShop.has("street_address"))
//                                        {
//                                            streetAddress = (coffeeShop.getString("street_address"));
//                                        }
//                                        if(coffeeShop.has("trading_name"))
//                                        {
//                                            tradingNames = (coffeeShop.getString("trading_name"));
//                                        }
//
//                                        fragment.drawShop(tradingNames,streetAddress);
//
//                                        shopCount +=1;
//                                    }
                                }

                                fragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(fragment.getData()));
                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                        }else{

                            getCafeList = null;
//                            streetAddress.clear();
//                            tradingNames.clear();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//            textView.setText("That didn't work!");
                Log.d("Response", "Did Not work");


            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user-key", "d705f8ba814a19da288511430e7f3fb3");
                params.put("Accept", "application/json");

                return params;
            }
        };
        fragment.queue.add(stringRequest);

    }

//    public ArrayList<String> getTradingNames(){
//        System.out.println(tradingNames);
//        return tradingNames;
//    }
//
//    public ArrayList<String> getStreetAddress() {
//        return streetAddress;
//    }



}
