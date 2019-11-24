package com.anugraha.project.moviegrid.api;

import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailGenre;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieSimilarResponse;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CastCombinedResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CastResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewCombinedResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewResponse;
import com.anugraha.project.moviegrid.model.Person;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.anugraha.project.moviegrid.model.ProfilesResponse;
import com.anugraha.project.moviegrid.model.ReviewsResponse;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.SeasonResponse;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.TVSimilarResponse;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;
import com.anugraha.project.moviegrid.model.TVResponse;
import com.anugraha.project.moviegrid.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {

    //MOVIES
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovis(@Query("api_key") String apiKey, @Query("page") int pageIndex, @Query("region") String region);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTop_ratedMovis(@Query("api_key") String apiKey, @Query("page") int pageIndex, @Query("region") String region);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcoming(@Query("api_key") String apiKey, @Query("page") int pageIndex,@Query("region") String region);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNow_playing(@Query("api_key") String apiKey, @Query("page") int pageIndex,@Query("region") String region);

    @GET("movie/{movie_id}")
    Call<MovieDetailResponse> getMovieDEtail(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}")
    Call<MovieDetailGenre> getMovieDetailGenre(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<MovieSimilarResponse> getSimiliarMovie(@Path("movie_id") int id, @Query("api_key") String apiKey);


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

    @GET("tv/{tv_id}")
    Call<TVDetailResponse> getTVDetail(@Path("tv_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{movie_id}/credits")
    Call<CreditResponse> getCreditstv(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/videos")
    Call<TrailerResponse> getTrailertv(@Path("tv_id") int id, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/season/{season_number}")
    Call<SeasonResponse> getSeasonEpisodes(@Path("tv_id") int id, @Path("season_number") int sn, @Query("api_key") String apiKey);

    @GET("tv/{tv_id}/similar")
    Call<TVSimilarResponse> getSimiliarTV(@Path("tv_id") int id, @Query("api_key") String apiKey);
    //END TV

    //MOVIES PATH
    @GET("movie/{movie_id}/videos")
    Call<TrailerResponse> getTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/credits")
    Call<CreditResponse> getCredits(@Path("movie_id") int id, @Query("api_key") String apiKey);
    //END

    //MOVIE REVIEW
    @GET("movie/{movie_id}/reviews")
    Call<ReviewsResponse> getReviews(@Path("movie_id") int id, @Query("api_key") String apiKey);

    //PERSON

    @GET("person/popular")
    Call<PersonResponse> getPopularPerson(@Query("api_key") String Apikey);

    @GET("person/{person_id}")
    Call<Person> getPerson(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/movie_credits")
    Call<CastResponse> getCastmovie_credits(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/movie_credits")
    Call<CrewResponse> getCrewmovie_credits(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/images")
    Call<ProfilesResponse> getPersonImages(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/combined_credits")
    Call<CastCombinedResponse> getcombined_credits(@Path("person_id") int id, @Query("api_key") String apiKey);

    @GET("person/{person_id}/combined_credits")
    Call<CrewCombinedResponse> getcombined_creditscrew(@Path("person_id") int id, @Query("api_key") String apiKey);
    //ENDPERSON

    //search
    @GET("search/movie")
    Call<MoviesResponse> getSearchMovie(@Query("api_key") String apiKey, @Query("query") String searchquery);

    @GET("search/tv")
    Call<TVResponse> getSearchtv(@Query("api_key") String apiKey, @Query("query") String searchquery);

    @GET("search/person")
    Call<PersonResponse> getSearchperson(@Query("api_key") String apiKey, @Query("query") String searchquery);


}
