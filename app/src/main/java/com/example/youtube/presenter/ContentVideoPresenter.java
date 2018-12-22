package com.example.youtube.presenter;

public interface ContentVideoPresenter {
    void getVideo(String videoId, String apiKey);
    void getListComment(String videoId, String apiKey, String pageToken);
}
