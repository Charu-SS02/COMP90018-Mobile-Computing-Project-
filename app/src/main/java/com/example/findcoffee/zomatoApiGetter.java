

package com.example.findcoffee;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.findcoffee.ui.explore.ExploreFragment;
import com.example.findcoffee.ui.home.CoffeeShopAdapter;
import com.example.findcoffee.ui.home.HomeFragment;
import com.example.findcoffee.ui.search.SearchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class zomatoApiGetter extends FragmentActivity {

    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private ExploreFragment exploreFragment;
    private RequestQueue queue;

    public zomatoApiGetter(HomeFragment homeFragment){
        this.homeFragment = homeFragment;
        this.searchFragment = null;
        this.exploreFragment = null;
    }

    public zomatoApiGetter(SearchFragment searchFragment){
        this.searchFragment = searchFragment;
        this.homeFragment = null;
        this.exploreFragment = null;
    }

    public zomatoApiGetter(ExploreFragment exploreFragment){
        this.searchFragment = null;
        this.homeFragment = null;
        this.exploreFragment = exploreFragment;
    }


    private String baseURL = "https://developers.zomato.com/api/v2.1/search?";
//    private float xSearch;
//    private float ySearch;
    private JSONArray getCafeList;
    private JSONObject coffeeShop;





    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void search(Map<String, String> searchString, RequestQueue queue, final int maxEntries){


        String query = "";
        Log.d("CurrentLoca",""+searchString);
        for (String key : searchString.keySet())
        {
            String value = searchString.get(key);
            query = query+key+value+"&";

        }

        String url = baseURL+query;
        System.out.println(url);


//        List<Fragment> getFragments = fragmentManager.getFragments();
//
//        String currentFragment = null;
//        HomeFragment homeFragment = null;
//        SearchFragment searchFragment = null;
//
//        for (Fragment frag : getFragments) {
//            if(frag instanceof HomeFragment){
////                Log.d("fraging","Home");
//                currentFragment = "home";
//                queue = Volley.newRequestQueue(Objects.requireNonNull(frag.getActivity()));
//                homeFragment = new HomeFragment();
//            }else{
//                currentFragment = "search";
//                queue = Volley.newRequestQueue(Objects.requireNonNull(frag.getActivity()));
//                searchFragment = new SearchFragment();
//            }
//        }
//
//        final String finalCurrentFragment = currentFragment;
//        final HomeFragment finalHomeFragment = homeFragment;
//        final SearchFragment finalSearchFragment = searchFragment;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        Log.d("REsponse","Response");
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

                                    String addressLon = coffeeShopLocation.getString("longitude");
                                    String addressLat = coffeeShopLocation.getString("latitude");

                                    String cuisines = coffeeShopDetails.getString("cuisines");

                                    String featured_image = coffeeShopDetails.getString("featured_image");
                                    String menu_url = coffeeShopDetails.getString("menu_url");

                                    String photos_url = coffeeShopDetails.getString("photos_url");
                                    String price_range = coffeeShopDetails.getString("price_range");

                                    String timings = coffeeShopDetails.getString("timings");
                                    String storeUrl = coffeeShopDetails.getString("url");


                                    String events_url = coffeeShopDetails.getString("events_url");

                                    if(homeFragment != null){

                                        homeFragment.drawShop(name,address,getThumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url);
                                    }else if(searchFragment != null){
                                        searchFragment.drawShop(name,address,getThumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url);

                                    }else if(exploreFragment != null){
                                        exploreFragment.drawShop(name,address,getThumb,addressLon,addressLat,cuisines,featured_image,menu_url,photos_url,price_range,timings,storeUrl,events_url);

                                    }

                                    i += 1;

                                }


                                if(homeFragment != null){
                                    HomeFragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(HomeFragment.getData()));
                                }else if(searchFragment != null){
                                    SearchFragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(SearchFragment.getData()));
                                }else if(exploreFragment != null){
                                    ExploreFragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(ExploreFragment.getData()));

                                }
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
        queue.add(stringRequest);
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


