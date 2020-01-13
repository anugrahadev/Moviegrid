package com.anugraha.project.moviegrid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anugraha.project.moviegrid.FragmentDrawer.FavoriteFragment;
import com.anugraha.project.moviegrid.FragmentDrawer.MoviesFragment;
import com.anugraha.project.moviegrid.FragmentDrawer.PersonFragment;
import com.anugraha.project.moviegrid.FragmentDrawer.SearchFragment;
import com.anugraha.project.moviegrid.FragmentDrawer.TvFragment;
import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.MovieDetail.MovieDetailResponse;
import com.anugraha.project.moviegrid.model.RequestTokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    ImageView iv_photop;
    String request_token;
    TextView tv_username;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPrefManager = new SharedPrefManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MoviesFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_movies);
        }


        iv_photop = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_photop);
        iv_photop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gotoSign = new Intent(MainActivity.this, SignInAct.class);
                startActivity(gotoSign);
            }
        });

        tv_username = navigationView.getHeaderView(0).findViewById(R.id.tv_username);
        if (sharedPrefManager.getSPSudahLogin()==true){
            tv_username.setText(sharedPrefManager.getSPUsername());
        }else{
            tv_username.setText("Click to Login");
        }



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_movies:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MoviesFragment()).commit();
                break;
            case R.id.nav_tv:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TvFragment()).commit();
                break;
            case R.id.nav_person:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new PersonFragment()).commit();
                break;
            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchFragment()).commit();
                break;
            case R.id.nav_favorite:

                if (sharedPrefManager.getSPSudahLogin()==true){
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new FavoriteFragment()).commit();
                }else{
                    Toast.makeText(MainActivity.this, "You must have Logged in to access this function", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item){
//        if (item.getItemId()==R.id.search_button){
//            startActivity(new Intent(this, SearchActivity.class));
//        }
//        return true;
//    }
}