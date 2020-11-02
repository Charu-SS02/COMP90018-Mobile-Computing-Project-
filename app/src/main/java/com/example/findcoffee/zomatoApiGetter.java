

package com.example.findcoffee;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.findcoffee.R;
import com.example.findcoffee.ui.home.CoffeeShopAdapter;
import com.example.findcoffee.ui.home.HomeFragment;
import com.example.findcoffee.ui.search.SearchFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class zomatoApiGetter extends FragmentActivity {

    private Fragment fragment;

    public zomatoApiGetter(HomeFragment homeFragment){
        this.fragment = homeFragment;
    }

    public zomatoApiGetter(SearchFragment searchFragment){
        this.fragment = searchFragment;
    }

    private String baseURL = "https://developers.zomato.com/api/v2.1/search?";
//    private float xSearch;
//    private float ySearch;
    private JSONArray getCafeList;
    private JSONObject coffeeShop;

    public RequestQueue queue;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void search(Map<String, String> searchString, final FragmentManager fragmentManager, final int maxEntries){

        String query = "";
        for (String key : searchString.keySet())
        {
            String value = searchString.get(key);
            query = query+key+value+"&";
        }

        String url = baseURL+query;
        System.out.println(url);

        List<Fragment> getFragments = fragmentManager.getFragments();

        String currentFragment = null;
        HomeFragment homeFragment = null;
        SearchFragment searchFragment = null;

        for (Fragment frag : getFragments) {
            if(frag instanceof HomeFragment){
//                Log.d("fraging","Home");
                currentFragment = "home";
                queue = Volley.newRequestQueue(Objects.requireNonNull(frag.getActivity()));
                homeFragment = new HomeFragment();
            }else{
                currentFragment = "search";
                queue = Volley.newRequestQueue(Objects.requireNonNull(frag.getActivity()));
                searchFragment = new SearchFragment();
            }
        }

        final String finalCurrentFragment = currentFragment;
        final HomeFragment finalHomeFragment = homeFragment;
        final SearchFragment finalSearchFragment = searchFragment;

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

                                    if(finalCurrentFragment.equals("home")){

                                        finalHomeFragment.drawShop(name,address,getThumb);
                                    }else{
                                        finalSearchFragment.drawShop(name,address,getThumb);

                                    }

                                    i += 1;

                                }


                                if(finalCurrentFragment.equals("home")){
                                    HomeFragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(HomeFragment.getData()));
                                }else{
                                    SearchFragment.getRecyclerView().setAdapter(new CoffeeShopAdapter(SearchFragment.getData()));
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


