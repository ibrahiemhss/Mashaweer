package com.mashaweer.ibrahim.mashaweer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mashaweer.ibrahim.mashaweer.R;

import java.util.ArrayList;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_FACE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_CAR_KIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_FACE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TXT_CAR_KIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.WHERE_FACE;

/**
 * Created by ibrahim on 26/12/17.
 */

public class DbGetSpinnerBackend extends DbObject {

    private static final String TAG = DbGetSpinnerBackend.class.getSimpleName();
    Context con;
    ArrayList<String> spinnerContentgetFaceSP;
    ArrayList<String> spinnerContentgetKarKindSP;

    String language;

    public DbGetSpinnerBackend(Context context) {
        super( context );
        this.con = context;
    }

    public ArrayList<String> getFaceSP() {

        String[] allSpinner;
        Cursor cr=this.getDbConnection().query(TABLE_FACE, new String[] {WHERE_FACE}, null, null, null, null, null);
        ArrayList<String> temparrayList = new ArrayList<String>();
        temparrayList.add(con.getResources().getString( R.string.face_spinner));
        // tv.setTextColor(  Color.parseColor( "#CC1D1D" )  );

        if (cr.moveToFirst())
        {
            do
            {
                temparrayList.add(cr.getString(cr.getColumnIndex(WHERE_FACE)));
            }
            while (cr.moveToNext());
        }
        return temparrayList;


    }

    public void addFaceSp(String id, String name) {

        SQLiteDatabase db = this.writeDbConnection();
        ContentValues values = new ContentValues();

        values.put( ID_FACE, id );
        values.put( WHERE_FACE, name );

        long query = db.insertWithOnConflict( TABLE_FACE, null, values, SQLiteDatabase.CONFLICT_REPLACE );
        db.insertWithOnConflict( TABLE_FACE, null, values, SQLiteDatabase.CONFLICT_REPLACE );

        Log.d( TAG, "add TABLE_FACE list inserted into sqlite: " + query );

        db.close();
    }


    public ArrayList<String> getcarKindSP() {

        Cursor cr = this.getDbConnection().query( TABLE_CAR_KIND, new String[]{TXT_CAR_KIND}, null, null, null, null, null );
        ArrayList<String> temparrayList = new ArrayList<String>();
        temparrayList.add(con.getResources().getString( R.string.car_spinner));
        if (cr.moveToFirst()) {
            do {
                temparrayList.add( cr.getString( cr.getColumnIndex( TXT_CAR_KIND ) ) );
            }
            while (cr.moveToNext());
        }
        return temparrayList;


    }


}