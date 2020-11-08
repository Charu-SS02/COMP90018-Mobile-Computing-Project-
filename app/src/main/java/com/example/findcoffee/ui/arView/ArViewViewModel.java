package com.example.findcoffee.ui.arView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ArViewViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ArViewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Ar view fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
