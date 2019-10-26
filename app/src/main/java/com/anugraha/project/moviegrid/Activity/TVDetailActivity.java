package com.anugraha.project.moviegrid.Activity;

import android.app.ProgressDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class TVDetailActivity extends AppCompatActivity {
    ImageView iv_colappsingtoolbar;
    ProgressDialog progressDialog;
    Toolbar toolbar;
    LinearLayout fragment_detail;

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvdetail);


    }
}
