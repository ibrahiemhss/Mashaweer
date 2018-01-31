package com.mashaweer.ibrahim.mashaweer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mashaweer.ibrahim.mashaweer.data.DbGetSpinnerBackend;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;
import com.mashaweer.ibrahim.mashaweer.model.BookModels;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ibrahim on 18/01/18.
 */

public class MainActivityDrivers extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivityUsers.class.getSimpleName();

    private MDbHelber mDbHelber;
    private ProgressBar progressBar;
    private TextView txtName,TxtEmail;
    private CircleImageView imageDriver;
    private DbGetSpinnerBackend dbGetSpinnerBackend;
    private List<BookModels> bookModels;
    private RecyclerView.LayoutManager recyclerViewlayoutManager;
    private RecyclerView.Adapter recyclerViewadapter;
    private RecyclerView Desplay_Shofires_For_Clients;
    private MyDrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main_drivers );


        mDbHelber = new MDbHelber( this );
        android.support.v7.widget.Toolbar toolbar = findViewById( R.id.toolbar_driver );
        setSupportActionBar( toolbar );

        DrawerLayout drawer = findViewById( R.id.drawer_layout_driver );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.addDrawerListener( toggle );
        toggle.syncState();

        NavigationView navigationView = findViewById( R.id.nav_view_driver );
        navigationView.setNavigationItemSelectedListener( this );
        View headerView = navigationView.getHeaderView( 0 );
        imageDriver = headerView.findViewById( R.id.imageUser );
        txtName = headerView.findViewById( R.id.NaveTxtName );
        TxtEmail = headerView.findViewById( R.id.NaveTxtEmail );
            Glide.with( MainActivityDrivers.this ).load( SharedPrefManager.getInstance( MainActivityDrivers.this ).getImageOfDriver() ).into( imageDriver );
         txtName.setText( SharedPrefManager.getInstance( this ).getNamesOfDriver());
         TxtEmail.setText(SharedPrefManager.getInstance( this ).getEmailOfDriver() );

        progressBar = findViewById( R.id.progressListDriver );
        progressBar = new ProgressBar( this );

        bookModels = new ArrayList<>();
      //  Desplay_Shofires_For_Clients = (RecyclerView) findViewById( R.id.RV_driver );
       // Desplay_Shofires_For_Clients.setHasFixedSize( true );
      //  recyclerViewlayoutManager = new LinearLayoutManager( this );
       // Desplay_Shofires_For_Clients.setLayoutManager( recyclerViewlayoutManager );
       // recyclerViewadapter = new DriverAdapter( bookModels, this );
      //  Desplay_Shofires_For_Clients.setAdapter( recyclerViewadapter );


        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes( (WindowManager.LayoutParams) params );


        }



            private void logoutUser() {

                SharedPrefManager.getInstance( MainActivityDrivers.this ).setLoginDriver( false );
                mDbHelber.deleteUsers();

                // Launching the login activity
                Intent intent = new Intent( MainActivityDrivers.this, Home.class );
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


}