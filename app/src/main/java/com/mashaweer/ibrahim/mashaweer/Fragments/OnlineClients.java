package com.mashaweer.ibrahim.mashaweer.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashaweer.ibrahim.mashaweer.volleyRequets.Mysingletone;
import com.mashaweer.ibrahim.mashaweer.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OnlineClients extends FragmentActivity
        {
    private String pushNotificationsForClients = "http://devsinai.com/mashaweer/messages/pushDriver.php";


    TextView PHONEclient, nameclient, TimeClient,Faceclient, idclient;
    Button callclient, SendMssage;

    String Phone;

    String NameMape;
    String sh_id,id_user,usernames;

    double latitude,longitude;
    SharedPreferences prefsh, SharPlace;
    AppCompatButton login_Sh;
    SharedPreferences.Editor editorsh, editorSharPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_online_clients);



        String LOCATION_URL = "http://devsinai.com/mashaweer/GoogleMaps/Location.php";

        StringRequest stringRequest = new StringRequest( Request.Method.POST,LOCATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(OnlineClients.this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonRootObject = new JSONObject(response);

                            //Get the instance of JSONArray that contains JSONObjects
                            JSONArray jsonArray = jsonRootObject.optJSONArray("");

                            //Iterate the jsonArray and print the info of JSONObjects
                            for(int i=0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                latitude = Double.parseDouble(jsonObject.optString("latitude").toString());
                                longitude = Double.parseDouble(jsonObject.optString("longitude").toString());

                            }

                        } catch (JSONException e) {e.printStackTrace();}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(OnlineClients.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })


        {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();

                map.put("sh_id", sh_id);



                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }


        getSharedPreferences("Loginsh.shofier", Context.MODE_PRIVATE);


        prefsh = getSharedPreferences("Loginsh.shofier", Context.MODE_PRIVATE);

        sh_id = prefsh.getString("sh_id", "sh_id");
        // lat = prefsh.getString("latitude", "latitude");
        // lang = prefsh.getString("longitude", "longitude");


        nameclient = (TextView) findViewById(R.id.nameclient);
        PHONEclient = (TextView) findViewById(R.id.PHONEclient);
        TimeClient = (TextView) findViewById(R.id.TimeClient);
        Faceclient = (TextView) findViewById(R.id.Faceclient);
        idclient= (TextView) findViewById(R.id.idclient);

        final Bundle bundle = getIntent().getExtras();
        nameclient.setText("الاسم         : " + bundle.getString("name"));
        PHONEclient.setText("الهاتف       : " + bundle.getString("phone"));
        TimeClient.setText("الوقت المطلوب : " + bundle.getString("traveTimeBB"));
        Faceclient.setText("الوجهه : " + bundle.getString("whereFace"));
        idclient.setText(" رقم الحجز:   "+bundle.getString("id_user_to_Dr"));

        NameMape = bundle.getString("name").trim();
      //  ٍusernames=bundle.getString("name");
        id_user=bundle.getString("id_user_to_Dr");

        callclient = (Button) findViewById(R.id.callclient);
        SendMssage = (Button) findViewById(R.id.SendMssage);

        SendMssage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final StringRequest stringRequest = new StringRequest( Request.Method.POST, pushNotificationsForClients,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonObject = new JSONObject(response);

                                    String Response = jsonObject.getString("response");

                                    Log.d("responsey", Response);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        Map<String, String> params = new HashMap<>();


                        params.put("username", NameMape);            // Add this line to send USER ID to server
                        params.put("id", id_user);


                        return params;
                    }
                };

                Mysingletone.getInstance(OnlineClients.this).addToRequestQueue(stringRequest);
            }

        });


    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }
    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }


    //=============================================
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();


    }

}
