package com.anugraha.project.moviegrid.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.anugraha.project.moviegrid.SharedPrefManager;
import com.anugraha.project.moviegrid.api.Client;
import com.anugraha.project.moviegrid.api.Service;
import com.anugraha.project.moviegrid.model.LoginResponse;
import com.anugraha.project.moviegrid.model.RequestTokenResponse;
import com.anugraha.project.moviegrid.model.SessionResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInAct extends AppCompatActivity {
    TextView ivtoken,btn_new_account;
    String reqtoken, username,pw;
    EditText et_password, et_username;
    Button btn_sign_in;
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        sharedPrefManager = new SharedPrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(SignInAct.this, ProfileActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        btn_new_account = (TextView) findViewById(R.id.btn_new_account);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        btn_sign_in =  (Button) findViewById(R.id.button_sign_in);


        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                username = et_username.getText().toString();
                pw = et_password.getText().toString();
                if (username.isEmpty() || pw.isEmpty()){
                    Toast.makeText(SignInAct.this, "Username and Password cant be empty", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    Service apiServicerequesttoken = Client.getClient().create(Service.class);
                    Call<RequestTokenResponse> callreqtoken = apiServicerequesttoken.getRequestToken(BuildConfig.THE_MOVIE_DB_API_TOKEN);
                    callreqtoken.enqueue(new Callback<RequestTokenResponse>() {
                        @Override
                        public void onResponse(Call<RequestTokenResponse> call, Response<RequestTokenResponse> response) {
                            if (response.isSuccessful()){
                                reqtoken = response.body().getRequestToken();
                                Service apiService = Client.getClient().create(Service.class);
                                Call<LoginResponse> calllgn = apiService.Login(BuildConfig.THE_MOVIE_DB_API_TOKEN, username, pw, reqtoken);
                                calllgn.enqueue(new Callback<LoginResponse>() {
                                    @Override
                                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                        if (response.isSuccessful()){
                                            Service apiService2 = Client.getClient().create(Service.class);
                                            Call<SessionResponse> callsession = apiService2.LoginSession(BuildConfig.THE_MOVIE_DB_API_TOKEN, reqtoken);
                                            callsession.enqueue(new Callback<SessionResponse>() {
                                                @Override
                                                public void onResponse(Call<SessionResponse> call, Response<SessionResponse> response) {
                                                    if (response.isSuccessful()){
                                                        String session = response.body().getSessionId();
                                                        Toast.makeText(SignInAct.this, "Berhasil Login", Toast.LENGTH_SHORT).show();
                                                        progressDialog.dismiss();
                                                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_SUDAH_LOGIN, true);
                                                        Intent gotoprofile = new Intent(SignInAct.this, ProfileActivity.class);
                                                        gotoprofile.putExtra("session", response.body().getSessionId());
                                                        sharedPrefManager.saveSPSession(session);
                                                        startActivity(gotoprofile);
                                                        finish();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<SessionResponse> call, Throwable t) {

                                                }
                                            });

                                        }else{
                                            Toast.makeText(SignInAct.this, "Gagal Login", Toast.LENGTH_SHORT).show();

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onFailure(Call<RequestTokenResponse> call, Throwable t) {

                        }
                    });
                }



            }
        });





    }
}
