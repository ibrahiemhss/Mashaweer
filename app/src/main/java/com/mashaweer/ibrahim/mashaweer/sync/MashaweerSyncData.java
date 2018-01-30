package com.mashaweer.ibrahim.mashaweer.sync;

import android.content.Context;

/**
 * Created by ibrahim on 29/12/17.
 */
//TODO 2 @GetSpinnersContents الكلاس اللي يستدعي ميثود الفولي
public class MashaweerSyncData {

    static GetSpinnersContents getSpinnersContents;

    Context context;

    public MashaweerSyncData(Context context) {
        this.context = context;
        getSpinnersContents = new GetSpinnersContents( context );


    }

    synchronized public static void syncSpinner(Context context) {
        try {
            getSpinnersContents = new GetSpinnersContents( context );
            getSpinnersContents.getCarKind( context );
            // getSpinnersContents.getDoctor( this );
            getSpinnersContents.getFace( context );

        } catch (Exception e) {
            /* Server probably invalid */
            e.printStackTrace();
        }
    }
}