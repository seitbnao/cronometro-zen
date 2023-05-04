package com.isa_djunio.cronometro_maluco.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Versão: 3.0.1");
    }

    public LiveData<String> getText() {
        return mText;
    }
}