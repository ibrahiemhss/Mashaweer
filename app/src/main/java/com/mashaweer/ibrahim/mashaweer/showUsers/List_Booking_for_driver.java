package com.mashaweer.ibrahim.mashaweer.showUsers;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashaweer.ibrahim.mashaweer.Network.CheckInternetConnection;
import com.mashaweer.ibrahim.mashaweer.R;
import com.mashaweer.ibrahim.mashaweer.adapters.BookAdapter;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;
import com.mashaweer.ibrahim.mashaweer.model.BookModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FACE_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_CAR_ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_USER_ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.NAME_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TRAVEL_TIME_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.WHERE_FROM_BOOKING;


public class List_Booking_for_driver extends AppCompatActivity {
    RecyclerView RS_BookingSh;
    private BookAdapter adapter;
    private RequestQueue mRequestQueue;
    private List<BookModels> bookModelses1 ;
    private ProgressDialog pd;
    RecyclerView.LayoutManager recyclerViewlayoutManager;
    RecyclerView.Adapter  recyclerViewadapter;
    private RequestQueue requestQueue;
    String urlListBokings = "http://devsinai.com/mashaweer/DisplayData/ListDisplayFoorShofiers.php";

    ProgressBar progressBar;

    TextView whergevr,carstype;
    String carId,Face_id;

    MDbHelber mDbHelber;
    CheckInternetConnection checkInternetConnection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_list__booking);

        checkInternetConnection=new CheckInternetConnection();

        mDbHelber=new MDbHelber( this );
        bookModelses1=new ArrayList<>();
        RS_BookingSh = (RecyclerView) findViewById( R.id.show_BookingSh);
        RS_BookingSh.setHasFixedSize(true);
        recyclerViewlayoutManager = new LinearLayoutManager(this);
        RS_BookingSh.setLayoutManager(recyclerViewlayoutManager);
        recyclerViewadapter = new BookAdapter(bookModelses1,this);
        RS_BookingSh.setAdapter(recyclerViewadapter);
        recyclerViewadapter.notifyDataSetChanged();


        progressBar = (ProgressBar)findViewById( R.id.progressBar1);
        JSON_DATA_WEB_CALL();

        // carId= PreferenceUtil.getDriverCarId( this );
       // String driverId= PreferenceUtil.getDriverId( this );
       // String driverName= PreferenceUtil.getDriverName( this );
      //  Toast.makeText(List_Booking_for_driver.this,"driverId="+driverId+"| driverName="+driverName+"| car id="+carId,Toast.LENGTH_LONG).show();

        displayOfline();


        if (checkInternetConnection.CheckInternet( this )==true){
            JSON_DATA_WEB_CALL();

        }
        else if(checkInternetConnection.CheckInternet( this )==false) {
            Toast.makeText( this,"لايوجد اتصال بالانترنت لكن بمكنك الاطلاع علي القائمه السابقه ",Toast.LENGTH_LONG ).show();
            displayOfline();
        }

    }


        public void JSON_DATA_WEB_CALL() {

            StringRequest stringRequest=new StringRequest( Request.Method.POST, urlListBokings,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            try {
                                JSONArray jsonArray=new JSONArray(response);
                              //  progressBar.setVisibility(View.GONE);

                                JSON_PARSE_DATA_AFTER_WEBCALL(jsonArray);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {



                }
            }
            )
            {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<String, String>();
                params.put("car_id", carId);
               // params.put("face_id", Face_id);


                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(stringRequest);

    }
    private void displayOfline(){
        bookModelses1.clear();

        Cursor cursor = mDbHelber.getBookingList(carId );
        while (cursor.moveToNext()) {
                BookModels bookModels2;
            bookModels2 = new BookModels(
                    cursor.getString( cursor.getColumnIndex( ID_BOOKING )),
                    cursor.getString( cursor.getColumnIndex( NAME_BOOKING ) ),
                    cursor.getString( cursor.getColumnIndex( FACE_BOOKING ) ),
                    cursor.getString( cursor.getColumnIndex( TRAVEL_TIME_BOOKING ) ),
                    cursor.getString( cursor.getColumnIndex( WHERE_FROM_BOOKING ) ),
                    cursor.getString( cursor.getColumnIndex( FK_USER_ID_DRIVER ) ),
                    cursor.getString( cursor.getColumnIndex( FK_CAR_ID_BOOKING ))
            );


            bookModelses1.add( bookModels2 );

        }



        recyclerViewadapter = new BookAdapter(bookModelses1, this);

        RS_BookingSh.setAdapter(recyclerViewadapter);
        recyclerViewadapter.notifyDataSetChanged();
        Log.d( String.valueOf( bookModelses1 ),"listBooking" );


    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            BookModels bookModels2 = new BookModels();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                String id=json.getString( "b_id" );
                String name=json.getString("name");
                String face=json.getString("face");
                String traveTime=json.getString("traveTime");
                String wherFrom=json.getString("wherefrom");
                String uer_id1=json.getString("user_id");
                String car_id=json.getString( "car_id" );

                mDbHelber.addBookingList(id, name,face,traveTime,wherFrom,uer_id1,car_id );

                bookModels2.setUsername(name);//
                bookModels2.setFace(face);//
                bookModels2.setTraveTime(traveTime);//

                bookModels2.setWherefrom(wherFrom);//
             //   bookModels2.setPhone(json.getString("phone"));
                bookModels2.setId(uer_id1);//

                // bookModels2.setUser_id(json.getString("user_id"));

                //  bookModels2.setCar_id(json.getString("car_id"));

            } catch (JSONException e) {

                e.printStackTrace();
            }
            bookModelses1.add(bookModels2);
        }

        recyclerViewadapter = new BookAdapter(bookModelses1,this);

        RS_BookingSh.setAdapter(recyclerViewadapter);




    }
}