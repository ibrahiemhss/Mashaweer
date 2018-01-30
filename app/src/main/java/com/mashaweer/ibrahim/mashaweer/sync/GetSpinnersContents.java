package com.mashaweer.ibrahim.mashaweer.sync;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mashaweer.ibrahim.mashaweer.data.MDbHelber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.URL_SYNC_CARKIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.URL_SYNC_FACE;

/**
 * Created by ibrahim on 26/12/17.
 */
//TODO 1 كلاس الميثود استدعاء بالفولي
public class GetSpinnersContents {
    private static final String TAG = GetSpinnersContents.class.getSimpleName();

    MDbHelber mDbHelber;
    RequestQueue requestQueue;
    Context context;

    public GetSpinnersContents(Context context) {
        this.context = context;
        mDbHelber = new MDbHelber( context );

    }

    public void getFace(Context context) {

        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL_SYNC_FACE ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            PARSE_STATES( jsonArray );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );
    }

    public void PARSE_STATES(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject( i );
                String id = json.getString( "id" );
                String name = json.getString( "wherface" );

                mDbHelber.addFaceSpinner( id, name );
                Log.d( TAG, "value from face server : " + id + "  " + name );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //===================================getAreas=================================================
    public void getCarKind(Context context) {
        mDbHelber = new MDbHelber( context );

        StringRequest stringRequest = new StringRequest( Request.Method.POST, URL_SYNC_CARKIND ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray( response );
                            PARSE_AREA( jsonArray );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );
    }

    public void PARSE_AREA(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject( i );
                String id = json.getString( "id_car" );
                String name = json.getString( "kind_car" );
                mDbHelber.addCarKindSpinner( id, name );
                Log.d( TAG, "value from carkind server : " + id + "  " + name );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



}
