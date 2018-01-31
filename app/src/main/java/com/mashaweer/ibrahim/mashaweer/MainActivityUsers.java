package com.mashaweer.ibrahim.mashaweer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.mashaweer.ibrahim.mashaweer.LocationUtil.DirectionsJSONParser;
import com.mashaweer.ibrahim.mashaweer.data.DbGetSpinnerBackend;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;
import com.google.android.gms.location.places.GeoDataClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivityUsers extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivityUsers.class.getSimpleName();

    private MDbHelber mDbHelber;
    String Car_id, Face_id, sh_id;
    private TextView txtName, TxtUserEmail;
    private Spinner SP_CARKIND, SP_FACE;
    private CircleImageView imageUser;
    private ArrayList<String> FaceSpFromSqlite, carKndSpFromSqlite;
    private DbGetSpinnerBackend dbGetSpinnerBackend;
    int i = 0;
    String selctedCarName, selectedFaceName;

    private MyDrawerLayout mDrawerLayout;
    Button btn_Book,btn_Location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_users );

        if (ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101 );

        }


        btn_Book = findViewById( R.id.btn_Book );
        btn_Location=findViewById( R.id.btn_Location );
        mDbHelber = new MDbHelber( this );
        Toolbar toolbar = findViewById( R.id.toolbar );
        //  getApplicationContext().setSupportActionBar( toolbar );

        DrawerLayout drawer = findViewById( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view );
        navigationView.setNavigationItemSelectedListener( this );

        View headerView = navigationView.getHeaderView( 0 );
        imageUser = headerView.findViewById( R.id.imageUser );
        txtName = headerView.findViewById( R.id.NaveTxtName );
        TxtUserEmail = headerView.findViewById( R.id.NaveTxtEmail );
        Glide.with( MainActivityUsers.this ).load( SharedPrefManager.getInstance( MainActivityUsers.this ).getImageOfUsers() ).into( imageUser );
        txtName.setText( SharedPrefManager.getInstance( this ).getNamesOfUsers() );
        TxtUserEmail.setText( SharedPrefManager.getInstance( this ).getEmailOfUsers() );


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes( (WindowManager.LayoutParams) params );
        SP_CARKIND = findViewById( R.id.SP_CARKIND );
        SP_FACE = findViewById( R.id.SP_FACE );

        dbGetSpinnerBackend = new DbGetSpinnerBackend( this );

        carKndSpFromSqlite = dbGetSpinnerBackend.getcarKindSP();
        FaceSpFromSqlite = dbGetSpinnerBackend.getFaceSP();
        // String[] CarKindSpTitle = new String[carKndSpFromSqlite.length + 1];
        // CarKindSpTitle[0] = " نوع السياره";
        // System.arraycopy(carKndSpFromSq// String[] FaceSpTitle = new String[FaceSpFromSqlite.length + 1];
        // FaceSpTitle[0] = "حدد الوجهه";
        //  System.arraycopy(FaceSpFromSqlite, 0, FaceSpTitle, 1, FaceSpFromSqlite.length);


        loadSpinnersFromSqlite();
        btn_Location.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivityUsers.this, MapUserActivity
                        .class);
                startActivity(intent);

            }
        } );
        btn_Book.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(!forClientsModels.isEmpty()){
                    forClientsModels.clear();
                }*/
                try {
                    Car_id = mDbHelber.getCarKindId( selctedCarName );
                    Face_id = mDbHelber.getFaceId( selectedFaceName );
                    if (SP_CARKIND.getOnItemSelectedListener() == null && SP_FACE.getOnItemSelectedListener() == null) {
                        Toast.makeText( MainActivityUsers.this.getApplicationContext(), "برجاء اختيار كل العناصر المطلوبه", Toast.LENGTH_LONG ).show();

                    } else if (SP_FACE.getOnItemSelectedListener() == null) {
                        Toast.makeText( MainActivityUsers.this.getApplicationContext(), "برجاء اختيار فئة الرحله", Toast.LENGTH_LONG ).show();
                    } else if (SP_CARKIND.getOnItemSelectedListener() == null) {
                        Toast.makeText( MainActivityUsers.this.getApplicationContext(), "برجاء اختيار نوع السياره", Toast.LENGTH_LONG ).show();

                    } else {
                        Log.d( TAG, "idDetailsFromSqlite: \n cr_id " + Car_id + "\nface_id " + Face_id );
                        // JSON_DATA_WEB_CALL();

                    }

                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }

            }
        } );


    }


    private void loadSpinnersFromSqlite() {


        final ArrayAdapter<String> spinnerAdapterFaceSp = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, FaceSpFromSqlite ) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView( position, convertView, parent );
                TextView tv = (TextView) view;
                //  tv.setTextColor(  Color.parseColor( "#CC1D1D" )  );

                if (position == 0) {
                    tv.setTextColor( Color.parseColor( "#CC1D1D" ) );
                }
              /* if (position % 2 == 1 ) {

                    tv.setBackgroundColor( Color.parseColor( "#FF00D977" ) );
                } else {
                    tv.setBackgroundColor( Color.parseColor( "#FF76F2BA" ) );
                }*/
                return view;
            }
        };
        spinnerAdapterFaceSp.setDropDownViewResource( R.layout.spinner_dropdown_item );
        SP_FACE.setAdapter( spinnerAdapterFaceSp );

        SP_FACE.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFaceName = (String) parent.getItemAtPosition( position );
                // selectedFaceName=SP_FACE.getOnItemSelectedListener().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        } );

        final ArrayAdapter<String> spinnerAdaptercarKndSp = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, carKndSpFromSqlite ) {
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView( position, convertView, parent );
                TextView tv = (TextView) view;
                // tv.setTextColor(  Color.parseColor( "#CC1D1D" )  );
                if (position == 0) {
                    tv.setTextColor( Color.parseColor( "#CC1D1D" ) );
                }
                /*if (position % 2 == 1) {

                    tv.setBackgroundColor( Color.parseColor( "#FF00D977" ) );

                } else {
                    tv.setBackgroundColor( Color.parseColor( "#FF76F2BA" ) );
                }*/
                return view;
            }
        };
        spinnerAdaptercarKndSp.setDropDownViewResource( R.layout.spinner_dropdown_item );
        SP_CARKIND.setAdapter( spinnerAdaptercarKndSp );

        SP_CARKIND.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selctedCarName = (String) parent.getItemAtPosition( position );

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;

            }
        } );

    }


    private void logoutUser() {

        SharedPrefManager.getInstance( this ).setLoginUser( false );
        mDbHelber.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent( MainActivityUsers.this, Home.class );
        startActivity( intent );
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        if (drawer.isDrawerOpen( GravityCompat.START )) {
            drawer.closeDrawer( GravityCompat.START );
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_signout) {
            logoutUser();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_profile) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawer.closeDrawer( GravityCompat.START );
        return true;
    }


    public String getSh_id(String sh_id) {
        this.sh_id = sh_id;
        return sh_id;
    }

}

