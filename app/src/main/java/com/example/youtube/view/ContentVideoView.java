package com.example.youtube.view;

import com.example.youtube.model.comment.Item;
import com.example.youtube.model.video.Video;

import java.util.ArrayList;

public interface ContentVideoView {
    void getVideoSuccess(Video video);
    void getVideoError();

    void getListCommentSuccess(ArrayList<Item> items, String nextPageToken);
    void getListCommentError();
}
