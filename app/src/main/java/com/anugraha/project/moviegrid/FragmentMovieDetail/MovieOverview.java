package com.anugraha.project.moviegrid.FragmentMovieDetail;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.Adapter.CreditAdapter;
import com.anugraha.project.moviegrid.Adapter.CrewAdapter;
import com.anugraha.project.moviegrid.Adapter.MoviesAdapter;
import com.anugraha.project.moviegrid.data.FavoriteContract;
import com.anugraha.project.moviegrid.data.FavoriteDbHelper;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.Cast;
import com.anugraha.project.moviegrid.model.CreditResponse;
import com.anugraha.project.moviegrid.model.Crew;
import com.anugraha.project.moviegrid.model.Movie;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailGenre;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.MoviesResponse;
import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieOverview extends Fragment {
    ImageView iv_poster;
    TextView tv_status, tv_release_date, tv_budget, tv_revenue, tv_runtime, tv_title,tv_rating,tv_genre,tv_tagline,tv_overview;
    RecyclerView rv_cast;
    RecyclerView rv_crew;
    private List<Cast> castList;
    private CreditAdapter adaptercast;
    private List<Crew> crewList;
    private CrewAdapter adaptercrew;

    private FavoriteDbHelper favoriteDbHelper;
    private Movie favorite;
    private SQLiteDatabase mDb;

    Movie movie;
    String thumbnail, movieName, synopsis, rating, dateOfRelease;
    int movie_id;

    public MovieOverview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_movie_overview, container, false);
        iv_poster = view.findViewById(R.id.iv_poster);
        tv_title = view.findViewById(R.id.tv_title);
        tv_revenue = view.findViewById(R.id.tv_revenue);
        tv_runtime = view.findViewById(R.id.tv_runtime);
        tv_budget = view.findViewById(R.id.tv_budget);
        tv_release_date = view.findViewById(R.id.tv_release_date);
        tv_rating = view.findViewById(R.id.tv_rating);
        tv_genre = view.findViewById(R.id.tv_genre);
        tv_tagline = view.findViewById(R.id.tv_tagline);
        tv_overview = view.findViewById(R.id.tv_overview);
        rv_cast = view.findViewById(R.id.rv_cast);
        rv_crew = view.findViewById(R.id.rv_crew);

        FavoriteDbHelper dbHelper = new FavoriteDbHelper(getContext());
        mDb = dbHelper.getWritableDatabase();

        Intent intentStarted = getActivity().getIntent();

        if (intentStarted.hasExtra("movies")) {

            movie = getActivity().getIntent().getParcelableExtra("movies");

            thumbnail = movie.getPosterPath();
            movieName = movie.getOriginalTitle();
            synopsis = movie.getOverview();
            rating = Double.toString(movie.getVoteAverage());
            dateOfRelease = movie.getReleaseDate();
            movie_id = movie.getId();

            Service apiService = Client.getClient().create(Service.class);
            Call<MovieDetailResponse> call = apiService.getMovieDEtail(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MovieDetailResponse>() {
                @Override
                public void onResponse(Call<MovieDetailResponse> call, Response<MovieDetailResponse> response) {
                    Glide.with(getContext())
                            .load("https://image.tmdb.org/t/p/w300/"+response.body().getPosterPath())
                            .placeholder(R.drawable.load)
                            .into(iv_poster);
                    if (response.body().getBudget()==0){
                        tv_budget.setText("-");
                    }else{
                        tv_budget.setText("$"+NumberFormat.getNumberInstance(Locale.US).format(response.body().getBudget()));
                    }

                    if (response.body().getRevenue()==0){
                        tv_revenue.setText("-");
                    }else{
                        tv_revenue.setText("$"+NumberFormat.getNumberInstance(Locale.US).format(response.body().getRevenue()));
                    }

                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy" );
                    Date date;
                    try {
                        String release_date = response.body().getReleaseDate();
                        date = originalFormat.parse(release_date);
                        tv_release_date.setText(targetFormat.format(date));
                    } catch (ParseException ex) {
                        // Handle Exception.
                    }
                    if (response.body().getRuntime()!=0 ||response.body().getRuntime()!=null){
                        int hours = response.body().getRuntime() / 60; //since both are ints, you get an int
                        int minutes = response.body().getRuntime() % 60;
                        tv_runtime.setText(response.body().getRuntime().toString()+"m / "+hours+"h "+minutes+"m");
                    }else{
                        tv_runtime.setText("Not Found");
                    }

                    if (response.body().getVoteAverage() == 0 || response.body().getVoteAverage()==0.0){
                        tv_rating.setText("Not Rated");
                    }else{
                        tv_rating.setText(" "+response.body().getVoteAverage().toString()+" / 10");
                    }
                    List<MovieDetailGenre> moviesgenre = response.body().getGenres();
                    String concatenatedgenre = "";
                    for (int i = 0; i < moviesgenre.size(); i++) {
                        concatenatedgenre += moviesgenre.get(i).getName();
                        if (i < moviesgenre.size() - 1) {
                            concatenatedgenre += ", ";
                        }
                    }
                    tv_genre.setText(concatenatedgenre);
                    tv_tagline.setText('"'+response.body().getTagline()+'"');
                    tv_tagline.setTextColor(Color.parseColor("#00D277"));
                    tv_title.setText(response.body().getOriginalTitle());
                    tv_title.setTextColor(Color.parseColor("#00D277"));
                    tv_overview.setText(response.body().getOverview());


                }

                @Override
                public void onFailure(Call<MovieDetailResponse> call, Throwable t) {

                }
            });
        }

        MaterialFavoriteButton materialFavoriteButton  = (MaterialFavoriteButton) view.findViewById(R.id.favorite_button);

//        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
//
//
//        materialFavoriteButtonNice.setOnFavoriteChangeListener(
//                new MaterialFavoriteButton.OnFavoriteChangeListener(){
//                    @Override
//                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite){
//                        if (favorite){
//                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("com.anugraha.project.moviegrid.Activity.DetailActivity", Context.MODE_PRIVATE).edit();
//                            editor.putBoolean("Favorite Added", true);
//                            editor.commit();
//                            saveFavorite();
//                            Snackbar.make(buttonView, "Added to Favorite",
//                                    Snackbar.LENGTH_SHORT).show();
//                        }else{
//                            int movie_id = getActivity().getIntent().getExtras().getInt("id");
//                            favoriteDbHelper = new FavoriteDbHelper(getContext());
//                            favoriteDbHelper.deleteFavorite(movie_id);
//
//                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("com.anugraha.project.moviegrid.Activity.DetailActivity", Context.MODE_PRIVATE).edit();
//                            editor.putBoolean("Favorite Removed", true);
//                            editor.commit();
//                            Snackbar.make(buttonView, "Removed from Favorite",
//                                    Snackbar.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }
//        );
        if (Exists(movieName)){
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                favoriteDbHelper = new FavoriteDbHelper(getContext());
                                favoriteDbHelper.deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });


        }else {
            materialFavoriteButton.setOnFavoriteChangeListener(
                    new MaterialFavoriteButton.OnFavoriteChangeListener() {
                        @Override
                        public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                            if (favorite == true) {
                                saveFavorite();
                                Snackbar.make(buttonView, "Added to Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            } else {
                                int movie_id = getActivity().getIntent().getExtras().getInt("id");
                                favoriteDbHelper = new FavoriteDbHelper(getContext());
                                favoriteDbHelper.deleteFavorite(movie_id);
                                Snackbar.make(buttonView, "Removed from Favorite",
                                        Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });


        }

        castList= new ArrayList<>();
        adaptercast = new  CreditAdapter(getContext(), castList);
        RecyclerView.LayoutManager mLayoutManagerCast = new LinearLayoutManager(getContext(),LinearLayout.HORIZONTAL,false);
        rv_cast.setLayoutManager(mLayoutManagerCast);
        rv_cast.setAdapter(adaptercast);
        rv_cast.setHasFixedSize(false);
        adaptercast.notifyDataSetChanged();
        loadJSONCast();

        crewList = new ArrayList<>();
        adaptercrew = new CrewAdapter(getContext(), crewList);
        RecyclerView.LayoutManager layoutManagerCrew = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        rv_crew.setLayoutManager(layoutManagerCrew);
        rv_crew.setAdapter(adaptercrew);
        rv_crew.setHasFixedSize(false);
        adaptercrew.notifyDataSetChanged();
        loadJSONCrew();
        return view;
    }

    private void loadJSONCrew() {
        Service apiService = Client.getClient().create(Service.class);
        Call<CreditResponse> call = apiService.getCredits(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                crewList = response.body().getCrew();
                rv_crew.setAdapter(new CrewAdapter(getContext(), crewList));
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {

            }
        });
    }

    private void loadJSONCast() {
        Service apiService = Client.getClient().create(Service.class);
        Call<CreditResponse> call = apiService.getCredits(movie_id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
        call.enqueue(new Callback<CreditResponse>() {
            @Override
            public void onResponse(Call<CreditResponse> call, Response<CreditResponse> response) {
                castList = response.body().getCast();
                rv_cast.setAdapter(new CreditAdapter(getContext(), castList));
            }

            @Override
            public void onFailure(Call<CreditResponse> call, Throwable t) {

            }
        });
    }

    public void saveFavorite(){
        favoriteDbHelper = new FavoriteDbHelper(getContext());
        favorite = new Movie();

        Double rate = movie.getVoteAverage();


        favorite.setId(movie_id);
        favorite.setOriginalTitle(movieName);
        favorite.setPosterPath(thumbnail);
        favorite.setVoteAverage(rate);
        favorite.setOverview(synopsis);

        favoriteDbHelper.addFavorite(favorite);
    }

    public boolean Exists(String searchItem) {

        String[] projection = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoriteEntry.COLUMN_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_USERRATING,
                FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS

        };
        String selection = FavoriteContract.FavoriteEntry.COLUMN_TITLE + " =?";
        String[] selectionArgs = { searchItem };
        String limit = "1";

        Cursor cursor = mDb.query(FavoriteContract.FavoriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null, limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

}