package com.anugraha.project.moviegrid.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anugraha.project.moviegrid.SharedPrefManager;

import java.util.Arrays;

public class SettingsActivity extends AppCompatActivity {

    TextView region,language,imgq;
    LinearLayout layoutregion, layoutlanguage,layoutimgq;
    SharedPrefManager sharedPrefManager;
    CharSequence[] regionitems = {"US","GB","ID","DE","NL","ES","CN","JP"};
    CharSequence[] langitems = {"EN","ID","DE","NL","ES","PT","CN","JP"};
    CharSequence[] imagequalityitems = {"High","Medium","Low"};
    int selectedregion;
    int selectedlanguage;
    int selectedimgquality;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        sharedPrefManager = new SharedPrefManager(this);
        init();
        region.setText(sharedPrefManager.getSpRegion());
        selectedregion = Arrays.asList(regionitems).indexOf(sharedPrefManager.getSpRegion());
        language.setText(sharedPrefManager.getSpLang());
        selectedlanguage = Arrays.asList(langitems).indexOf(sharedPrefManager.getSpLang());
        imgq.setText(sharedPrefManager.getImgQuality());
        selectedimgquality = Arrays.asList(imagequalityitems).indexOf(sharedPrefManager.getImgQuality());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        layoutregion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Select Region");
                alertDialogBuilder.setSingleChoiceItems(regionitems, selectedregion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedregion=which;
                        region.setText(regionitems[selectedregion]);
                        sharedPrefManager.setSpRegion(regionitems[selectedregion].toString());
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });

        layoutlanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Select Language");
                alertDialogBuilder.setSingleChoiceItems(langitems, selectedlanguage, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedlanguage=which;
                        language.setText(langitems[selectedlanguage]);
                        sharedPrefManager.setSpLang(langitems[selectedlanguage].toString());
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });

        layoutimgq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SettingsActivity.this);
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setTitle("Select Iamge Quality");
                alertDialogBuilder.setSingleChoiceItems(imagequalityitems, selectedimgquality, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedimgquality=which;
                        imgq.setText(imagequalityitems[selectedimgquality]);
                        sharedPrefManager.setSpImgQuality(imagequalityitems[selectedimgquality].toString());
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();
            }
        });


    }

    private void init() {
        region = (TextView) findViewById(R.id.tv_choiceregion);
        language = (TextView) findViewById(R.id.tv_choicelang);
        imgq = (TextView) findViewById(R.id.tv_imgquality);
        layoutregion = (LinearLayout) findViewById(R.id.layout_region);
        layoutlanguage = (LinearLayout) findViewById(R.id.layout_lang);
        layoutimgq = (LinearLayout) findViewById(R.id.layout_imgq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
}
