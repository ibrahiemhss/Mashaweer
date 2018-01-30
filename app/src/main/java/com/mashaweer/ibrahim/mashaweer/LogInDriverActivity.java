package com.mashaweer.ibrahim.mashaweer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mashaweer.ibrahim.mashaweer.Network.BaseApiService;
import com.mashaweer.ibrahim.mashaweer.Network.UtilsApi;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ibrahim on 28/01/18.
 */

public class LogInDriverActivity extends AppCompatActivity {

    EditText etEmailDriver;
    EditText etPasswordDriver;
    Button btnLoginDriver;
    Button btnRegisterDriver;
    ProgressDialog loadingDriver;
    private AlertDialog.Builder builder;
    private static final String PROTOCOL_CHARSET = "utf-8";

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_log_in_driver );


        if (SharedPrefManager.getInstance( getApplicationContext() ).isDriverLoggedIn()) {
            // PostUser is already logged in. Take him to main activity
            Intent intent = new Intent(LogInDriverActivity.this, MainActivityDrivers.class);
            startActivity(intent);
            finish();
        }

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();

    }

    private void initComponents() {
        etEmailDriver = (EditText) findViewById(R.id.etEmailDriver);
        etPasswordDriver = (EditText) findViewById(R.id.etPasswordDriver);
        btnLoginDriver = (Button) findViewById(R.id.btnLoginDriver);
        btnRegisterDriver = (Button) findViewById(R.id.btnRegisterDriver);

        btnLoginDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDriver = ProgressDialog.show(mContext, null, getResources().getString( R.string.loging_in), true, false);
                requestLogin();
            }
        });

        btnRegisterDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, RegisterUserActivity.class));
            }
        });

    }


    private void requestLogin(){


        mApiService.loginRequestDriver(etEmailDriver.getText().toString(), etPasswordDriver.getText().toString(),SharedPrefManager.getInstance( this ).getDeviceToken())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Log.i("debug", "onResponse:start ");

                            loadingDriver.dismiss();
                            try {
                                String remoteResponse=response.body().string();

                                Log.d("JSON", remoteResponse);

                                JSONObject jsonRESULTS = new JSONObject(remoteResponse);
                                if (jsonRESULTS.getString("error").equals("false")){

                                    SharedPrefManager.getInstance( getApplicationContext() ).setLoginDriver(true);

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
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveDriverId( uid );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveNamesOfDrivers( name );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveEmailOfDriver( email );
                                    SharedPrefManager.getInstance( getApplicationContext() ).savePhonefDriver( phone );
                                    SharedPrefManager.getInstance( getApplicationContext() ).saveImagefDriver( imageURl );
                                    Intent intent=new Intent(mContext, MainActivityDrivers.class);
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
                            loadingDriver.dismiss();
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
        Intent intent=new Intent(LogInDriverActivity.this, ResetpasswordActivity.class);
        startActivity(intent);

    }
}