package com.example.findcoffee.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String name;
    String version;
    int id_;
    int image;
    private MutableLiveData<String> mText;

    public  HomeViewModel(){
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }
//    public HomeViewModel(String name, String version, int id_, int image) {
//        this.name = name;
//        this.version = version;
//        this.id_ = id_;
//        this.image = image;
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public int getImage() {
        return image;
    }

    public int getId() {
        return id_;
    }
}