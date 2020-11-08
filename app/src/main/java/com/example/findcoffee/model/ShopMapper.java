package com.example.findcoffee.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by: Xixiang Wu
 * Date:       7/11/20.
 * Email:      xixiangw@student.unimelb.edu.au
 */

public class ShopMapper {

    /**
     * Shop Mapper is a Singleton class that is an encapsulation for all the loaded coffee shop
     * nearby. It is also used in the AR view for extending functions.
     * */

    /* a list for all the shops */
    private List<Shop> shopList;

    private static ShopMapper instance = new ShopMapper();

    private ShopMapper() {
        shopList = new ArrayList<Shop>();
    }

    public static ShopMapper getInstance() {
        return instance;
    }

    public void addShop(Shop shop) {
        if (!shopList.contains(shop))
            shopList.add(shop);
    }


    /* Retrieve Shop by ID */
    public Shop retrieveById(String uuid) {
        for (Shop shop: shopList) {
            if (shop.getShopId().equals(uuid))
                return shop;
        }
        return null;
    }


    /* Retrieve Shop by Name */
    public Shop retrieveByName(String shopName) {
        for (Shop shop: shopList) {
            if (shop.getShopName().equals(shopName))
                return shop;
        }
        return null;
    }


    /* Retrieve Shop randomly */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Shop retrieveRandomly() {
        int min = 0;
        int max = shopList.size();
        int index = ThreadLocalRandom.current().nextInt(min, max + 1);
        return shopList.get(index);
    }

}
