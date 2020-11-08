package com.example.findcoffee.model;

/**
 * Created by: Xixiang Wu
 * Date:       7/11/20.
 * Email:      xixiangw@student.unimelb.edu.au
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

public class Shop {

    /* A unique shop ID for every shop */
    final private String shopId = UUID.randomUUID().toString();
    final private String SEATS_OUTDOOR_STR = "Seats - Outdoor";
    final private String SEATS_INDOOR_STR  = "Seats - Indoor";
    final private String END_YEAR = "2019";

    /** Shop class, include all the data that can be retrieved from ZomatoAPI */
    private String shopName;
    private String shopThumb;
    private String shopAddress;
    private String shopFeaturedImage;
    private String shopCuisines;
    private String shopMenuUrl;
    private String shopPhotoUrl;
    private String shopPriceRange;
    private String shopStoreUrl;
    private String shopTimings;
    private String shopEventsUrl;
    private String shopAggregateRating;

    /* location data for AR functions */
    private double shopAddressLon;
    private double shopAddressLat;
    private double shopAddressAlt;

    /* database loaded */
    private boolean dbLoaded = false; // if the data is loaded
    private boolean noData   = false; // if the melbourne database has no matching name
    private int     shopIndoorSeatNum;
    private int     shopOutdoorSeatNum;

    /* init the Shop and save it to the factory */
    public Shop(JSONObject shopJSON) throws JSONException {

        if (shopJSON != null) {

            // Assume all JSON file are structured bug-free.
            JSONObject shopDetails = shopJSON.getJSONObject("restaurant");
            shopName           = shopDetails.getString("name");
            shopThumb          = shopDetails.getString("thumb");

            JSONObject coffeeShopLocation = shopDetails.getJSONObject("location");
            shopAddress        = coffeeShopLocation.getString("address");
            shopAddressLon     = Double.valueOf(coffeeShopLocation.getString("longitude"));
            shopAddressLat     = Double.valueOf(coffeeShopLocation.getString("latitude"));
            shopAddressAlt     = 0.0f;
            shopCuisines       = shopDetails.getString("cuisines");
            shopFeaturedImage  = shopDetails.getString("featured_image");
            shopMenuUrl        = shopDetails.getString("menu_url");
            shopPhotoUrl       = shopDetails.getString("photos_url");
            shopPriceRange     = shopDetails.getString("price_range");
            shopTimings        = shopDetails.getString("timings");
            shopStoreUrl       = shopDetails.getString("url");
            shopEventsUrl      = shopDetails.getString("events_url");

            JSONObject userRating = shopDetails.getJSONObject("user_rating");
            shopAggregateRating = userRating.getString("aggregate_rating");

            // add this shop into the ShopMapper
            ShopMapper.getInstance().addShop(this);
        }
    }


    /* History of coffee shop from https://data.melbourne.vic.gov.au/resource/xt2y-tnn9.json */
    public void loadSeatsNum() throws IOException, JSONException {

        if (!dbLoaded) { // to check if the data is already loaded

            final String databaseUrl = "https://data.melbourne.vic.gov.au/resource/xt2y-tnn9.json?trading_name="+shopName;
            JSONArray jsonArray = ApiFactory.readJsonArrayFromUrl(databaseUrl);
            System.out.println("debug: " + jsonArray);

            if (jsonArray.length() == 0) // the database may have no record of this restaurant
                noData = true;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String census_year = (String) json.get("census_year");

                /* get indoor and outdoor seats */
                if (census_year.equals(END_YEAR)) {

                    String seating_type    = (String) json.get("seating_type");
                    int number_of_seats = Integer.valueOf((String) json.get("number_of_seats"));
                    switch (seating_type) {
                        case SEATS_OUTDOOR_STR: shopOutdoorSeatNum = number_of_seats; break;
                        case SEATS_INDOOR_STR :  shopIndoorSeatNum  = number_of_seats; break;
                        default: break;
                    }
                }
            }

            dbLoaded = true;
        }
    }


    /* Getter */
    public String getShopId()              { return shopId; }
    public String getShopName()            { return shopName; }
    public String getShopThumb()           { return shopThumb; }
    public String getShopAddress()         { return shopAddress; }
    public String getShopFeaturedImage()   { return shopFeaturedImage; }
    public String getShopCuisines()        { return shopCuisines; }
    public String getShopMenuUrl()         { return shopMenuUrl; }
    public String getShopPhotoUrl()        { return shopPhotoUrl; }
    public String getShopPriceRange()      { return shopPriceRange; }
    public String getShopStoreUrl()        { return shopStoreUrl; }
    public String getShopTimings()         { return shopTimings; }
    public String getShopEventsUrl()       { return shopEventsUrl; }
    public String getShopAggregateRating() { return shopAggregateRating; }
    public double getShopAddressLon()      { return shopAddressLon; }
    public double getShopAddressLat()      { return shopAddressLat; }
    public double getShopAddressAlt()      { return shopAddressAlt; }
    public int getShopIndoorSeatNum()      { return shopIndoorSeatNum; }
    public int getShopOutdoorSeatNum()     { return shopOutdoorSeatNum; }

}
