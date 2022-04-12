package com.example.android.hammersystemsapp.ui.basket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BasketViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BasketViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is basket fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}