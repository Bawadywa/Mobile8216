package ua.kpi.comsys.IO8216.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Половінкін Петро\nГрупа ІО-82\nЗК ІО-8216");
    }

    public LiveData<String> getText() {
        return mText;
    }
}