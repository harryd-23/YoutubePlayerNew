package com.example.youtube.presenteriplm;

import com.apkfuns.logutils.LogUtils;
import com.example.youtube.model.channel.ChannelInfo;
import com.example.youtube.network.APIRequests;
import com.example.youtube.presenter.MainViewPresenter;
import com.example.youtube.view.MainView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainViewPresenterIplm implements MainViewPresenter {
    private MainView mainView;
    private CompositeDisposable compositeDisposable;
    public MainViewPresenterIplm(MainView mainView, CompositeDisposable compositeDisposable){
        this.mainView = mainView;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void getChannelInfo(String channelId, String apiKey) {
        Disposable getChannelInfoRequest = APIRequests.getChannelInfo(channelId, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(jsonElement -> {
                    LogUtils.d("getChannelInfo success = " + jsonElement);
                    Gson gson = new Gson();
                    ChannelInfo channelInfo = gson.fromJson(jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0), new TypeToken<ChannelInfo>(){}.getType());
                    mainView.getChannelInfoSuccess(channelInfo);
                }, throwable -> {
                    LogUtils.e("getChannelInfo error = " + throwable);
                    throwable.printStackTrace();
                    mainView.getChannelInfoError();
                });
        compositeDisposable.add(getChannelInfoRequest);
    }
}
