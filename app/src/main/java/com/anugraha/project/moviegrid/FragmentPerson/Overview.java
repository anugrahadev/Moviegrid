package com.anugraha.project.moviegrid.FragmentPerson;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anugraha.project.moviegrid.Activity.BuildConfig;
import com.anugraha.project.moviegrid.Activity.R;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.Person;
import com.anugraha.project.moviegrid.model.PersonResponse;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Overview extends Fragment {
    TextView tv_nama,tv_job,tv_gender,tv_birthday,tv_birthplace,tv_bio;
    ImageView iv_foto;
    Integer id;
    ProgressDialog progressDialog;
    public Overview() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading movies...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        tv_nama = (TextView) view.findViewById(R.id.tv_name);
        tv_job = (TextView) view.findViewById(R.id.tv_job);
        tv_gender = (TextView) view.findViewById(R.id.tv_gender);
        tv_birthday = (TextView) view.findViewById(R.id.tv_birthday);
        tv_birthplace = (TextView) view.findViewById(R.id.tv_birthplace);
        tv_bio = (TextView) view.findViewById(R.id.tv_bio);
        iv_foto = (ImageView) view.findViewById(R.id.iv_foto);

        Intent intentStarted = getActivity().getIntent();
        id = getActivity().getIntent().getExtras().getInt("id");
        if (intentStarted.hasExtra("id")) {
            Service apiService = Client.getClient().create(Service.class);
            Call<Person> call = apiService.getPerson(id, BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<Person>() {
                @Override
                public void onResponse(Call<Person> call, Response<Person> response) {
                    progressDialog.dismiss();
                    tv_nama.setText(response.body().getName());
                    tv_job.setText(response.body().getKnownForDepartment());
                    tv_birthplace.setText(response.body().getPlaceOfBirth());
                    tv_bio.setText(response.body().getBiography());
                    Glide.with(getContext())
                            .load("https://image.tmdb.org/t/p/w300"+response.body().getProfilePath())
                            .into(iv_foto);
                    if (response.body().getGender()==1){
                        tv_gender.setText("Female");
                    }else{
                        tv_gender.setText("Male");
                    }
                    SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy" );
                    Date date;
                    try {
                        String release_date = response.body().getBirthday();
                        date = originalFormat.parse(release_date);
                        tv_birthday.setText(targetFormat.format(date));
                    } catch (ParseException ex) {
                        // Handle Exception.
                    }
                }

                @Override
                public void onFailure(Call<Person> call, Throwable t) {

                }
            });

        }
        return view;
    }

}
