package com.example.findcoffee.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String name;
    String version;
    int id_;
    String image;
    String addressLon;
    String addressLat;
    String cuisines;
    String featured_image;
    String menu_url;
    String photos_url;
    String price_range;
    String timings;
    String storeUrl;
    String events_url;
    private MutableLiveData<String> mText;

//    public  HomeViewModel(){
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }
    public HomeViewModel(String name, String version, String coffee_placeholder,String addressLon,String addressLat,String cuisines,String featured_image,String menu_url, String photos_url,String price_range,String timings,String storeUrl,String events_url) {
        this.name = name;
        this.version = version;
        this.image = coffee_placeholder;
        this.addressLon = addressLon;
        this.addressLat = addressLat;
        this.cuisines = cuisines;
        this.featured_image = featured_image;
        this.menu_url = menu_url;
        this.photos_url = photos_url;
        this.price_range = price_range;
        this.timings = timings;
        this.storeUrl = storeUrl;
        this.events_url = events_url;


//        this.id_ = id_;

//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}