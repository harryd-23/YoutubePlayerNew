package com.example.youtube.model.channel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentDetails {

    @SerializedName("relatedPlaylists")
    @Expose
    private RelatedPlaylists relatedPlaylists;

    public RelatedPlaylists getRelatedPlaylists() {
        return relatedPlaylists;
    }

    public void setRelatedPlaylists(RelatedPlaylists relatedPlaylists) {
        this.relatedPlaylists = relatedPlaylists;
    }

}
