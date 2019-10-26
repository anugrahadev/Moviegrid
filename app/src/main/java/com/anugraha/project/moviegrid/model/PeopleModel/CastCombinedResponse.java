package com.anugraha.project.moviegrid.model.PeopleModel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CastCombinedResponse {

    @SerializedName("cast")
    @Expose
    private List<CastCombined> cast = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<CastCombined> getCast() {
        return cast;
    }

    public void setCast(List<CastCombined> cast) {
        this.cast = cast;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}