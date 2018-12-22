package com.example.youtube.view;

import com.example.youtube.model.listvideomodel.Item;

import java.util.ArrayList;

public interface ListVideoView {
    void getListVideoSuccess(ArrayList<Item> items, String nextPageToken, String totalResults);
    void getListVideoError();
}
