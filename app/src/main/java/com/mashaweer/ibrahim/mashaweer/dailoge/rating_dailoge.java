package com.mashaweer.ibrahim.mashaweer.dailoge;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.mashaweer.ibrahim.mashaweer.volleyRequets.Mysingletone;
import com.mashaweer.ibrahim.mashaweer.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ibrahim on 10/08/17.
 */

public class rating_dailoge extends Dialog {

    private static String TAG = rating_dailoge.class.getSimpleName();
    private RatingBar ratingBar;
    String ratingString,RATINGVALUE;;
    private TextView txtRatingValue;
    private Button btnSubmit;
    private List<ListRate> listRates ;
    Handler handler;

    private String id_user, rating, sssshhhh;
    private String url = "http://devsinai.com/mashaweer/ratingbar/rate.php";

    Context c;

    public rating_dailoge(Context a, String i) {
        super( a );
        this.c = a;
        this.sssshhhh = i;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.rating_dailoge );
        ratingBar = (RatingBar) findViewById( R.id.ratingBar );
        txtRatingValue = (TextView) findViewById( R.id.txtRatingValue );

        //id_user= PreferenceUtil.getClientId( this.c );

        addListenerOnButton();
        addListenerOnRatingBar();

    }



    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById( R.id.ratingBar );
        txtRatingValue = (TextView) findViewById( R.id.txtRatingValue );

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(final RatingBar ratingBar, float rating,
                                        boolean fromUser) {


                ratingBar.setRating( rating );

                txtRatingValue.setText( String.valueOf( rating ) );
            }

        } );
    }



    public void addListenerOnButton() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        //if click on me, then display the current rating value.
        btnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText( rating_dailoge.this.c,
                        String.valueOf( ratingBar.getRating() ),
                        Toast.LENGTH_SHORT ).show();

                rating = String.valueOf( ratingBar.getRating() );
             Toast.makeText( rating_dailoge.this.c, "rating=" + rating +"||RATING_FROM SERV="+ratingString +"    " + "||user_id" + id_user + "  ||driver_id " + sssshhhh, Toast.LENGTH_LONG ).show();

                StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                                JSONArray jsonArray = null;
                                try {
                                    jsonArray = new JSONArray( response );
                                    JSONObject jsonObject = jsonArray.getJSONObject( 0 );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText( rating_dailoge.this.c, "Error", Toast.LENGTH_LONG ).show();
                        VolleyLog.e( "Error: ", error.getMessage() );
                        error.printStackTrace();

                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put( "user_id", id_user );
                        params.put( "driv_rating", rating );
                        params.put( "sh_id", sssshhhh );

                        return params;
                    }
                };
                Mysingletone.getInstance( rating_dailoge.this.c ).addToRequestQueue( stringRequest );


            }
        } );


    }



    }
