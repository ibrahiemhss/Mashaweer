package com.mashaweer.ibrahim.mashaweer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.adapters.DriverAdapter;
import com.mashaweer.ibrahim.mashaweer.model.DriverModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.URL_LIST_DEIVER;

public class ShowForUseActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    String Car_id, Face_id,sh_id;
    private MDbHelber mDbHelber;

    private List<DriverModel> forClientsModels;
    private RecyclerView.LayoutManager recyclerViewlayoutManager;
    private RecyclerView.Adapter recyclerViewadapter;
    private RecyclerView Desplay_Shofires_For_Clients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_show_for_user );
        progressBar=findViewById( R.id.progressListDriver ) ;
        progressBar=new ProgressBar( this );
        progressBar.setVisibility( View.VISIBLE );
        mDbHelber=new MDbHelber( this );
        forClientsModels = new ArrayList<>();
        Desplay_Shofires_For_Clients = (RecyclerView) findViewById( R.id.Desplay_Shofires_For_Clients);
        Desplay_Shofires_For_Clients.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        Desplay_Shofires_For_Clients.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new DriverAdapter(forClientsModels, this);
        Desplay_Shofires_For_Clients.setAdapter(recyclerViewadapter);
        JSON_DATA_WEB_CALL();

    }


    public void JSON_DATA_WEB_CALL() {
        progressBar=new ProgressBar( this );
        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL_LIST_DEIVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //  String lat = String.valueOf(latitude);
                        //  String lang = String.valueOf(latitude);


                        try {
                            JSONArray jsonArray = new JSONArray(response);


                            JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                            // Toast.makeText(getActivity().getApplicationContext(), lat + "      " + lang, Toast.LENGTH_LONG).show();
                            progressBar.setVisibility( View.GONE );
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
                params.put("latitude", String.valueOf(1234678));
                params.put("longitude", String.valueOf(1234455));
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
                String phone=null;
                String distance=null;
                // json.getString( "distance" );
                String car_id=json.getString( "car_id" );

                forClientsModel2.setNAME(name);
                forClientsModel2.setSH_ID(sh_id);
                forClientsModel2.setMODEL_CAR(model_car);
                //  forClientsModel2.setPHONE(phone);
                // forClientsModel2.setDistance(distance);




                String rating=json.getString( "avg_rating" );


                mDbHelber.addDiverforList( name,sh_id,model_car,phone,rating ,car_id);



/*

                if(rating.contains( "null" )){
                    try {
                        forClientsModel2.setRating( Float.parseFloat( rating ) );

                    }catch (NumberFormatException e){
                        e.printStackTrace();
                    }
                }else {
                    forClientsModel2.setRating( Float.parseFloat( rating ) );

                }
*/


                //  bookModels2.setCar_id(json.getString("car_id"));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            forClientsModels.add(forClientsModel2);
        }

        recyclerViewadapter = new DriverAdapter(forClientsModels, this);

        Desplay_Shofires_For_Clients.setAdapter(recyclerViewadapter);


    }




}
