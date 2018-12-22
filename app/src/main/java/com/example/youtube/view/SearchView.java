package com.example.youtube.view;

import com.example.youtube.model.listvideomodel.Item;

import java.util.ArrayList;

public interface SearchView {
    void searchVideoSuccess(ArrayList<Item> items, String nextPageToken);
    void searchVideoError();
}
