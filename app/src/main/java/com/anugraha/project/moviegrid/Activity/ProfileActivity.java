package com.anugraha.project.moviegrid.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.AccountResponse;
import com.anugraha.project.moviegrid.model.LogoutResponse;
import com.anugraha.project.moviegrid.model.RequestTokenResponse;
import com.anugraha.project.moviegrid.model.SessionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView tv_username,tv_name;
    String reqtoken,session;
    ProgressDialog progressDialog;
    Button btn_sign_out,btn_home;
    SharedPrefManager sharedPrefManager;

    Service apiService;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefManager = new SharedPrefManager(this);
        initComponents();
//        Bundle extras = getIntent().getExtras();
//        session = extras.getString("session");
        session = sharedPrefManager.getSPSession();

        apiService = Client.getClient().create(Service.class);
        Call<AccountResponse> call = apiService.getAccount(BuildConfig.THE_MOVIE_DB_API_TOKEN, session);
        call.enqueue(new Callback<AccountResponse>() {
            @Override
            public void onResponse(Call<AccountResponse> call, Response<AccountResponse> response) {
                if (response.isSuccessful()){
                    sharedPrefManager.setSpUsername(response.body().getUsername());
                    sharedPrefManager.setSpName(response.body().getName());
                    sharedPrefManager.setSpAccountID(response.body().getId());
                    tv_name.setText(response.body().getName());
                    tv_username.setText(response.body().getUsername());
                }else{
                    Toast.makeText(ProfileActivity.this, "Gagal Load", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccountResponse> call, Throwable t) {

            }
        });

        btn_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(ProfileActivity.this, SignInAct.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                finish();
                apiService = Client.getClient().create(Service.class);
                Call<LogoutResponse> call = apiService.deleteSomething(BuildConfig.THE_MOVIE_DB_API_TOKEN, session);
                call.enqueue(new Callback<LogoutResponse>() {
                    @Override
                    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(ProfileActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
                            Intent logout = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(logout);
                            finish();
                        }else{
                            Toast.makeText(ProfileActivity.this, "Logout Failed", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<LogoutResponse> call, Throwable t) {

                    }
                });
            }
        });

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backhome = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(backhome);
            }
        });


    }

    private void initComponents() {
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_name = (TextView) findViewById(R.id.tv_name);
        btn_sign_out = (Button) findViewById(R.id.btn_sign_out);
        btn_home = (Button) findViewById(R.id.btn_home);

    }
}
