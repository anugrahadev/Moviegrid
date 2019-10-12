package com.anugraha.project.moviegrid.model.MovieDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieSimilarResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieSimilarResult> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieSimilarResult> getResults() {
        return results;
    }

    public void setResults(List<MovieSimilarResult> results) {
        this.results = results;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

}