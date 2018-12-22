package com.example.youtube.view;

import com.example.youtube.model.channel.ChannelInfo;

public interface MainView {

    void getChannelInfoSuccess(ChannelInfo channelInfo);
    void getChannelInfoError();
}
