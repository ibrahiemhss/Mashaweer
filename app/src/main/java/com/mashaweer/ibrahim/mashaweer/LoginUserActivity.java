package com.mashaweer.ibrahim.mashaweer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.HttpHeaderParser;
import com.mashaweer.ibrahim.mashaweer.Network.BaseApiService;
import com.mashaweer.ibrahim.mashaweer.Network.UtilsApi;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ibrahim on 21/01/18.
 */

public class LoginUserActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    ProgressDialog loading;
    private AlertDialog.Builder builder;
    private static final String PROTOCOL_CHARSET = "utf-8";

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in_user );


        if (SharedPrefManager.getInstance( getApplicationContext() ).isUserLoggedIn()) {
            // PostUser is already logged in. Take him to main activity
            Intent intent = new Intent(LoginUserActivity.this, MainActivityUsers.class);
            startActivity(intent);
            finish();
        }

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();

    }

    private void initComponents() {
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(mContext, null,  getResources().getString( R.string.loging_in), true, false);
                requestLogin();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterUserActivity.class));
            }
        });

    }


    private void requestLogin(){


        mApiService.loginRequest(etEmail.getText().toString(), etPassword.getText().toString(),SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse:start ");

                            loading.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                Log.d("JSON", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                if (jsonRESULTS.getString("error").equals("false")){

                                    SharedPrefManager.getInstance( getApplicationContext() ).setLoginUser(true);

                                    Toast.makeText(mContext, jsonRESULTS.getString("error_msg"), Toast.LENGTH_SHORT).show();
                                    String uid = jsonRESULTS.getString("uid");
                                    String name = jsonRESULTS.getJSONObject("user").getString("name");
                                    String email = jsonRESULTS.getJSONObject("user").getString("email");
                                    String phone = jsonRESULTS.getJSONObject("user").getString("phone");
                                    String imageURl = jsonRESULTS.getJSONObject("user").getString("image");
                                    String created_at = jsonRESULTS.getJSONObject("user").getString("time");
                                    String error_message = jsonRESULTS.getString("error_msg");

                                    Log.i("tagconvertstr", "["+ jsonRESULTS.getString("error_msg")+"]");


                                    Log.e("debug", "succeess: ERROR > "+error_message );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveUserId( uid );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveNamesOfUsers( name );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveEmailOfUsers( email );
                                    SharedPrefManager.getInstance( getApplicationContext() ).savePhonefUsers( phone );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveImagefUsers( imageURl );
                                    Intent intent=new Intent(mContext, MainActivityUsers
                                            .class);
                                    startActivity(intent);

                                } else {
                                    // Jika login gagal
                                    Log.e("debug", "onFailure: ERROR > " );
                                    String error_message = jsonRESULTS.getString("error_msg");

                                    Log.e("debug", "noAcount: ERROR > "+error_message );

                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(mContext, "خطا بالاتصال بالانترنت", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void gooResetPass(View view) {
        Intent intent=new Intent(LoginUserActivity.this, ResetpasswordActivity.class);
        startActivity(intent);

    }
}