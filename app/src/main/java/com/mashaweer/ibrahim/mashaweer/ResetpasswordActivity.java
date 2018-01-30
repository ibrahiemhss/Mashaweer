package com.mashaweer.ibrahim.mashaweer;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mashaweer.ibrahim.mashaweer.Network.BaseApiService;
import com.mashaweer.ibrahim.mashaweer.Network.UtilsApi;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetpasswordActivity extends AppCompatActivity {

    private Button btn_send_pass;
    private EditText et_reset_email;
    ProgressBar progress_reset;
    Context mContext;
    BaseApiService mApiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_reset_paswword );



        mContext = this;
        mApiService = UtilsApi.getAPIService(); // meng-init yang ada di package apihelper
        initComponents();


    }

    private void initComponents() {
        et_reset_email =  findViewById( R.id.et_reset_email );
        btn_send_pass =  findViewById( R.id.btn_send_pass );
        progress_reset =  findViewById( R.id.progress_reset );
        btn_send_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_reset .setVisibility(View.VISIBLE );
                requestResetPass();
            }
        });



    }


    private void requestResetPass(){


        mApiService.resetPassword(et_reset_email.getText().toString())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response != null) {
                                Log.i( "debug", "onResponse: start" );

                                try {
                                    String remoteResponse=response.body().string();

                                    Log.d( "JSON", remoteResponse );
                                    JSONObject jsonRESULTS = new JSONObject( remoteResponse );

                                    if (jsonRESULTS.getString( "error" ).equals( "false" )) {
                                        String error_message = jsonRESULTS.getString( "message" );
                                        Toast.makeText( mContext, jsonRESULTS.getString( "message" ), Toast.LENGTH_SHORT ).show();

                                        progress_reset.setVisibility( View.INVISIBLE );

                                        Log.e( "debug", "succeess: ERROR > " + error_message );
                                        Intent intent = new Intent( ResetpasswordActivity.this, LoginUserActivity.class );
                                        startActivity( intent );

                                    } else {
                                        // Jika login gagal
                                        Log.e( "debug", "onFailure: ERROR > " );

                                        Log.e( "debug", "noAcount: ERROR > " + jsonRESULTS.getString( "message" ) );

                                        Toast.makeText( mContext, jsonRESULTS.getString( "message" ), Toast.LENGTH_SHORT ).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            } else{
                            }
                        }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        Toast.makeText(mContext, "خطا بالاتصال بالانترنت", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}