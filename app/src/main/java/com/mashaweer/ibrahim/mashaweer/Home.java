package com.mashaweer.ibrahim.mashaweer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.mashaweer.ibrahim.mashaweer.sync.MashaweerSyncData;
import com.mashaweer.ibrahim.mashaweer.sync.MashaweerSyncUtils;

public class Home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_home);
        MashaweerSyncUtils.initialize( this );


        findViewById( R.id.clientss).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this, LoginUserActivity.class);
                startActivity(intent);

            }
        });
        findViewById( R.id.drivresss).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this, LogInDriverActivity.class);
                startActivity(intent);

            }
        });

    }

}
