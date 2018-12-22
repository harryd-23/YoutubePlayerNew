package com.example.youtube.presenter;

public interface SearchViewPresenter {
    void searchVideo(String channelId, String apiKey, String pageToken, String q);
}
