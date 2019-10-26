package com.anugraha.project.moviegrid.model.PeopleModel;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class CrewCombinedResponse {
    @SerializedName("crew")
    @Expose
    private List<CrewCombined> crew = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public List<CrewCombined> getCrew() {
        return crew;
    }

    public void setCrew(List<CrewCombined> crew) {
        this.crew = crew;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}