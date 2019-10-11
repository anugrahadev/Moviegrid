package com.anugraha.project.moviegrid.api;

import com.anugraha.project.moviegrid.Adapter.PersonAdapter;
import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.Person;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.anugraha.project.moviegrid.model.ProfilesResponse;
import com.anugraha.project.moviegrid.model.TVResponse;
import com.anugraha.project.moviegrid.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    //MOVIES
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovis(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTop_ratedMovis(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcoming(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNow_playing(@Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMovieDEtail(@Path("movie_id") int id, @Query("api_key") String apiKey);
    //ENDMOVIES

    //TV
    @GET("tv/popular")
    Call<TVResponse> getPopularTV(@Query("api_key") String apiKey);

    @GET("tv/on_the_air")
    Call<TVResponse> getOTRTV(@Query("api_key") String apiKey);

    @GET("tv/airing_today")
    Call<TVResponse> getAiringTV(@Query("api_key") String apiKey);

    @GET("tv/top_rated")
    Call<TVResponse> getTop_ratedTV(@Query("api_key") String apiKey);
    //END TV

    //MOVIES PATH
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CreditResponse> getCredits(@Path("movie_id") int id, @Query("api_key") String apiKey);
    //END

    //PERSON

    @GET("person/popular")
    Call<PersonResponse> getPopularPerson(@Query("api_key") String Apikey);

    @GET("person/{person_id}")
    Call<Person> getPerson(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/images")
    Call<ProfilesResponse> getPersonImages(@Path("person_id") int id, @Query("api_key") String apiKey);
    //ENDPERSON
}
