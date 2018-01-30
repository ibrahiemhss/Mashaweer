package com.mashaweer.ibrahim.mashaweer.showDriver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashaweer.ibrahim.mashaweer.Network.CheckInternetConnection;
import com.mashaweer.ibrahim.mashaweer.R;
import com.mashaweer.ibrahim.mashaweer.adapters.DriverAdapter;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.model.DriverModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.CAR_MODEL_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.UID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.USERNAME_DRIVER;

public class List_driver_for_Client extends FragmentActivity {

    private Location mLastLocation;
    public double latitude,longitude;

    private ProgressBar pd;
    private RecyclerView Desplay_Shofires_For_Clients;
    private DriverAdapter adapter;
    private RequestQueue mRequestQueue;
    private List<DriverModel> forClientsModels;
    private SharedPreferences pref2, pref, prefsh;
    private SharedPreferences.Editor editorsh, editor, editor2;
    private JsonArrayRequest jsonArrayRequest;
    private RecyclerView.LayoutManager recyclerViewlayoutManager;
    private RecyclerView.Adapter recyclerViewadapter;
    private RequestQueue requestQueue;
    private String urlListDesplayShForClient = "http://devsinai.com/mashaweer/DisplayData/ListDesplayForClient.php";
    private String car_id, cr_id, LatitudeSend, LongtudeSend;
    private ProgressBar progressBar;
    private SharedPreferences SharPlace;
    private SharedPreferences.Editor editorSharPlace;
    private double litude, longtude;
    private String comminglitude, comminglongtudee;
    public LocationManager locationManager;
    String Car_id, Face_id,sh_id;
    Bundle bundle;
//

CheckInternetConnection checkInternetConnection;

    MDbHelber mDbHelber;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView( R.layout.dialoge_list_desplay_for_client);

        checkInternetConnection=new CheckInternetConnection();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mDbHelber =new MDbHelber( this );

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        } else {
            showGPSDisabledAlertToUser();
        }



        // do stuff with state and extras
        bundle = getIntent().getExtras();

        if (bundle == null) {
            finish();
            return;
        } else if (bundle != null) {

           // Car_id = bundle.getString( Activity_Booking.MY_CAR_ID);
           // Face_id = bundle.getString(Activity_Booking.MY_FACE_ID);

        }
        forClientsModels = new ArrayList<>();
        Desplay_Shofires_For_Clients = (RecyclerView) findViewById( R.id.Desplay_Shofires_For_Clients);
        Desplay_Shofires_For_Clients.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        Desplay_Shofires_For_Clients.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new DriverAdapter(forClientsModels, this);
        Desplay_Shofires_For_Clients.setAdapter(recyclerViewadapter);


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);


        pd = new ProgressBar(this);
       // pd.setMessage("Loading . . . ");


        SharPlace = getSharedPreferences("Share.Places", Context.MODE_PRIVATE);

        if (checkInternetConnection.CheckInternet( this )==true){
            JSON_DATA_WEB_CALL();

        }
        else if(checkInternetConnection.CheckInternet( this )==false) {
            Toast.makeText( this,"لايوجد اتصال بالانترنت لكن بمكنك الاطلاع علي القائمه السابقه ",Toast.LENGTH_LONG ).show();
            displayOfline();
        }

        comminglitude = SharPlace.getString("litude", "");
        comminglongtudee = SharPlace.getString("longtude", "");
        editorSharPlace = SharPlace.edit();



        progressBar = (ProgressBar) findViewById( R.id.progressBar1);


    }

    private void displayOfline(){
        forClientsModels.clear();

        Cursor cursor = mDbHelber.getDriverListForClient( Car_id);
            while (cursor.moveToNext()) {

                DriverModel forClientsModel2 = new DriverModel(
                        cursor.getString(cursor.getColumnIndex(USERNAME_DRIVER)),
                        cursor.getString(cursor.getColumnIndex(CAR_MODEL_DRIVER)),
                        cursor.getString(cursor.getColumnIndex(UID_DRIVER))
                );
                forClientsModels.add(forClientsModel2);
            }



        recyclerViewadapter = new DriverAdapter(forClientsModels, this);

        Desplay_Shofires_For_Clients.setAdapter(recyclerViewadapter);
        recyclerViewadapter.notifyDataSetChanged();


    }

    public void JSON_DATA_WEB_CALL() {
        StringRequest stringRequest = new StringRequest( Request.Method.POST, urlListDesplayShForClient,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String lat = String.valueOf(latitude);
                        String lang = String.valueOf(latitude);


                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            progressBar.setVisibility(View.GONE);

                            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                            Toast.makeText(List_driver_for_Client.this.getApplicationContext(), lat + "      " + lang, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("car_id", Car_id);
                params.put("face_id", Face_id);
                params.put("latitude", String.valueOf(latitude));
                params.put("longitude", String.valueOf(longitude));
                return params;
            }
        };


        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }



    private void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            DriverModel forClientsModel2 = new DriverModel();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                String name=json.getString( "name" );
                String sh_id =json.getString( "sh_id" );
                String model_car=json.getString( "model_car" );
                String phone=json.getString( "phone" );
                String distance=json.getString( "distance" );
                String car_id=json.getString( "car_id" );

                forClientsModel2.setNAME(name);
                forClientsModel2.setSH_ID(sh_id);
                forClientsModel2.setMODEL_CAR(model_car);
                //forClientsModel2.setPHONE(phone);
                //forClientsModel2.setDistance(distance);




                String rating=json.getString( "avg_rating" );


                mDbHelber.addDiverforList( name,sh_id,model_car,phone,rating ,car_id);




                if(rating.contains( "null" )){
                    try {
                      //  forClientsModel2.setRating( Float.parseFloat( rating ) );

                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else {
                   // forClientsModel2.setRating( Float.parseFloat( rating ) );

                }


                //  bookModels2.setCar_id(json.getString("car_id"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            forClientsModels.add(forClientsModel2);
        }

        recyclerViewadapter = new DriverAdapter(forClientsModels, this);

        Desplay_Shofires_For_Clients.setAdapter(recyclerViewadapter);


    }


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

    @Override
    protected void onResume() {
        super.onResume();

       /* if (mGoogleApiClient == null || !mGoogleApiClient.isConnected()) {

            mGoogleApiClient.connect();

        }*/


    }



    @Override
    protected void onPause() {
        super.onPause();

    }



    public String getSh_id(String sh_id){
        this.sh_id=sh_id;
        return sh_id;
    }

}
