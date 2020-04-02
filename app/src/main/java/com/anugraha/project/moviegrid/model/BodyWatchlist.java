package com.anugraha.project.moviegrid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyWatchlist {
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("media_id")
    @Expose
    private Integer mediaId;
    @SerializedName("watchlist")
    @Expose
    private Boolean watchlist;

    public BodyWatchlist(String mediaType, Integer mediaId, Boolean watchlist) {
        this.mediaType = mediaType;
        this.mediaId = mediaId;
        this.watchlist = watchlist;
    }
}