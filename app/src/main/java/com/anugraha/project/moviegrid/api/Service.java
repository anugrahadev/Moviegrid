package com.anugraha.project.moviegrid.api;

import com.anugraha.project.moviegrid.model.AccountResponse;
import com.anugraha.project.moviegrid.model.BodyFavorite;
import com.anugraha.project.moviegrid.model.BodyWatchlist;
import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.FavResponse;
import com.anugraha.project.moviegrid.model.FavoriteMovie;
import com.anugraha.project.moviegrid.model.LoginResponse;
import com.anugraha.project.moviegrid.model.LogoutResponse;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailGenre;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieSimilarResponse;
import com.anugraha.project.moviegrid.model.MovieStateResponse;
import com.anugraha.project.moviegrid.model.MovieStateResponse2;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CastCombinedResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CastResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewCombinedResponse;
import com.anugraha.project.moviegrid.model.PeopleModel.CrewResponse;
import com.anugraha.project.moviegrid.model.Person;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.anugraha.project.moviegrid.model.ProfilesResponse;
import com.anugraha.project.moviegrid.model.RequestTokenResponse;
import com.anugraha.project.moviegrid.model.ReviewsResponse;
import com.anugraha.project.moviegrid.model.SessionResponse;
import com.anugraha.project.moviegrid.model.TVDetail.Seasons.SeasonResponse;
import com.anugraha.project.moviegrid.model.TVDetail.TVDetailResponse;
import com.anugraha.project.moviegrid.model.TVDetail.TVSimilarResponse;
import com.anugraha.project.moviegrid.model.TVResponse;
import com.anugraha.project.moviegrid.model.TVStateResponse;
import com.anugraha.project.moviegrid.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
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
    Call<MovieDetailResponse> getMovieDEtail(@Path("movie_id") int id, @Query("api_key") String apiKey, @Query("language") String language);

    @GET("movie/{movie_id}")
    Call<MovieDetailGenre> getMovieDetailGenre(@Path("movie_id") int id, @Query("api_key") String apiKey);

    @GET("movie/{movie_id}/similar")
    Call<MoviesResponse> getSimiliarMovie(@Path("movie_id") int id, @Query("api_key") String apiKey);


    //ENDMOVIES

    //TV
    @GET("tv/popular")
    Call<TVResponse> getPopularTV(@Query("api_key") String apiKey, @Query("page") int pageIndex,@Query("region") String region);

    @GET("tv/on_the_air")
    Call<TVResponse> getOTRTV(@Query("api_key") String apiKey,@Query("page") int pageIndex,@Query("region") String region);

    @GET("tv/airing_today")
    Call<TVResponse> getAiringTV(@Query("api_key") String apiKey,@Query("page") int pageIndex,@Query("region") String region);

    @GET("tv/top_rated")
    Call<TVResponse> getTop_ratedTV(@Query("api_key") String apiKey,@Query("page") int pageIndex,@Query("region") String region);

    @GET("tv/{tv_id}")
    Call<TVDetailResponse> getTVDetail(@Path("tv_id") int id, @Query("api_key") String apiKey, @Query("language") String language);

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


    //LOGIN

    @GET("authentication/token/new")
    Call<RequestTokenResponse> getRequestToken(@Query("api_key") String apiKey);

    @FormUrlEncoded
    @POST("authentication/token/validate_with_login")
    Call<LoginResponse> Login(@Query("api_key") String apiKey,
                              @Field("username") String username,
                              @Field("password") String password,
                              @Field("request_token") String request_token);

    @FormUrlEncoded
    @POST("authentication/session/new")
    Call<SessionResponse> LoginSession(@Query("api_key") String apiKey,
                                @Field("request_token") String request_token);

    @GET("account")
    Call<AccountResponse> getAccount(@Query("api_key") String apiKey, @Query("session_id") String session_id);


    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    Call<LogoutResponse> deleteSomething(@Query("api_key") String apiKey, @Field("session_id") String session_id);

    //favorite

    @GET("account/{account_id}/favorite/movies")
    Call<MoviesResponse> getFavoriteMovie(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);

    @GET("account/{account_id}/favorite/tv")
    Call<TVResponse> getFavoriteTV(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);

    @GET("account/{account_id}/rated/movies")
    Call<MoviesResponse> getRatedMovie(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);

    @GET("account/{account_id}/rated/tv")
    Call<TVResponse> getRatedTV(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);

    @GET("account/{account_id}/watchlist/movies")
    Call<MoviesResponse> getWatchlistMovie(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);

    @GET("account/{account_id}/watchlist/tv")
    Call<TVResponse> getWatchlistTV(@Query("api_key") String apiKey,@Query("session_id") String session_id, @Query("sort_by") String sort_by, @Query("page") int pageIndex);


    @FormUrlEncoded
    @POST("account/{account_id}/favorite")
    Call<FavResponse> postfav(@Path("account_id") int account_id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Field("media_type") String media_type, @Field("media_id") int media_id,@Field("favorite") boolean favorite);

    @FormUrlEncoded
    @POST("movie/{movie_id}/rating")
    Call<FavResponse> postRate(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Field("value") double value);

    @FormUrlEncoded
    @POST("tv/{tv_id}/rating")
    Call<FavResponse> postTVRate(@Path("tv_id") int tv_id, @Query("api_key") String apiKey, @Query("session_id") String session_id, @Field("value") double value);


    @POST("account/{account_id}/favorite")
    Call<FavResponse> markAsFavorite(@Body BodyFavorite bodyFavorite,
                                  @Query("api_key") String apiKey,
                                  @Query("session_id") String sessionId);

    @POST("account/{account_id}/watchlist")
    Call<FavResponse> markAsWatchlisted(@Body BodyWatchlist bodyWatchlist,
                                     @Query("api_key") String apiKey,
                                     @Query("session_id") String sessionId);


    @GET("movie/{movie_id}/account_states")
    Call<MovieStateResponse> getMovieState(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("session_id") String session_id);

    @GET("movie/{movie_id}/account_states")
    Call<MovieStateResponse2> getMovieState2(@Path("movie_id") int movie_id, @Query("api_key") String apiKey, @Query("session_id") String session_id);

    @GET("tv/{tv_id}/account_states")
    Call<TVStateResponse> getTVState(@Path("tv_id") int tv_id, @Query("api_key") String apiKey, @Query("session_id") String session_id);

    @GET("tv/{tv_id}/account_states")
    Call<MovieStateResponse2> getTVStateObject(@Path("tv_id") int tv_id, @Query("api_key") String apiKey, @Query("session_id") String session_id);


}
