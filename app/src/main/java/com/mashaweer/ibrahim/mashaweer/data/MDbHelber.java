package com.mashaweer.ibrahim.mashaweer.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.util.HashMap;

import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.CAR_MODEL_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.DATABASE_NAME;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.DISTANCE_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.DRIVER_RATE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.EMAIL_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.EMAIL_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FACE_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_BOOKING_ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_CARS_ID_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_CAR_ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_CAR_ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_DRIVER_ID_RATE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_FACE_ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_FACE_ID_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_RATE_ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_USER_ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_USER_ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FK_USER_ID_RATE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.FORGET_PASSWORD_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_CAR_KIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_FACE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_RATE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.ID_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.IMAGE_USER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.LATITUDE_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.LATITUDE_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.LONGITUDE_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.LONGITUDE_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.LOST_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.NAME_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.PASSWORD_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.PASSWORD_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.PHONE_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.PHONE_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.RAT_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_CAR_KIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_FACE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_RATE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TABLE_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TIME_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TOKEN_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TOKEN_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TRAVEL_TIME_BOOKING;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.TXT_CAR_KIND;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.UID_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.UID_USER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.USERNAME_DRIVER;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.USERNAME_USERS;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.WHERE_FACE;
import static com.mashaweer.ibrahim.mashaweer.data.MashweerContract.MashweerEntry.WHERE_FROM_BOOKING;


/**
 * Created by ibrahim on 19/12/17.
 */

public class MDbHelber extends SQLiteOpenHelper {

    private static final int SCHEMA = 1;
    private static final String TAG = MDbHelber.class.getSimpleName();

    public MDbHelber(Context context) {
        super( context,DATABASE_NAME,null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO creating table of users
        final String CREATE_TB_USER=
                "CREATE TABLE "+TABLE_USERS+"("+
                        ID_USERS+" INTEGER PRIMARY KEY , " +
                        USERNAME_USERS+" VARCHAR(60) UNIQUE, "+
                        EMAIL_USERS+" VARCHAR(100) UNIQUE, "+
                        UID_USER + " INTEGER UNIQUE,"+
                        TIME_USERS+" VARCHAR(100) NOT NULL, "+
                        PASSWORD_USERS+" VARCHAR(60) , "+
                        IMAGE_USER + " TEXT ,"+
                        PHONE_USERS+" INTEGER , "+
                        LATITUDE_USERS+" DECIMAL(11,8), "+
                        LONGITUDE_USERS+" DECIMAL(11,8), "+
                        TOKEN_USERS+" VARCHAR(500), "+
                        FK_CARS_ID_USERS+" INTEGER, "+
                        FK_FACE_ID_USERS+" INTEGER, "+
                        FORGET_PASSWORD_USERS+" VARCHAR(23), "+
                        LOST_USERS+" VARCHAR(1000), "+
                        "FOREIGN KEY("+FK_CARS_ID_USERS+") REFERENCES "+TABLE_CAR_KIND+" ("+ID_CAR_KIND+"), "+
                        "FOREIGN KEY("+FK_FACE_ID_USERS+") REFERENCES "+TABLE_FACE+" ("+ID_FACE+")"+")";

        //TODO creating table of driver
        final String CREATE_TB_DRIVER=
                "CREATE TABLE "+TABLE_DRIVER+"("+
                        ID_DRIVER+" INTEGER PRIMARY KEY , "+
                        USERNAME_DRIVER+" VARCHAR UNIQUE , "+//1
                        UID_DRIVER+" INTEGER UNIQUE, "+//2
                        EMAIL_DRIVER+" VARCHAR UNIQUE, "+//4
                        PASSWORD_DRIVER+" VARCHAR(60) , "+//5
                        CAR_MODEL_DRIVER+" VARCHAR(20) , "+//6
                        PHONE_DRIVER+" INTEGER , "+//7
                        DISTANCE_DRIVER+" TEXT  NULL, "+//8
                        RAT_DRIVER+" INTEGER , "+//9
                        LATITUDE_DRIVER+" DECIMAL(11,8), "+
                        LONGITUDE_DRIVER+" DECIMAL(11,8), "+
                        TOKEN_DRIVER+" VARCHAR(500), "+
                        FK_USER_ID_DRIVER+" INTEGER, "+
                        FK_RATE_ID_DRIVER+" INTEGER, "+
                        FK_CAR_ID_DRIVER+" INTEGER, "+
                        FK_BOOKING_ID_DRIVER+" INTEGER, "+
                        "FOREIGN KEY("+FK_USER_ID_DRIVER+") REFERENCES "+TABLE_USERS+" ("+ID_USERS+"), "+
                        "FOREIGN KEY("+FK_RATE_ID_DRIVER+") REFERENCES "+TABLE_RATE+" ("+ID_RATE+"), "+
                        "FOREIGN KEY("+FK_CAR_ID_DRIVER+") REFERENCES "+TABLE_CAR_KIND+" ("+ID_CAR_KIND+"), "+
                        "FOREIGN KEY("+FK_BOOKING_ID_DRIVER+") REFERENCES "+TABLE_BOOKING+" ("+ID_BOOKING+")"+")";

        //TODO creating table of listShow
        final String CREATE_TB_BOOKING=
                "CREATE TABLE "+TABLE_BOOKING+"("+
                        ID_BOOKING+" INTEGER PRIMARY KEY , "+
                        NAME_BOOKING+" VARCHAR(60) NOT NULL, "+
                        FACE_BOOKING+" VARCHAR(100) NOT NULL, "+
                        TRAVEL_TIME_BOOKING+" VARCHAR(60) NOT NULL, "+
                        WHERE_FROM_BOOKING+" VARCHAR(60) NOT NULL, "+
                        FK_CAR_ID_BOOKING+" INTEGER, "+
                        FK_USER_ID_BOOKING+" INTEGER, "+
                        FK_FACE_ID_BOOKING+" INTEGER, "+
                        "FOREIGN KEY("+FK_CAR_ID_BOOKING+") REFERENCES "+TABLE_CAR_KIND+" ("+ID_CAR_KIND+"), "+
                        "FOREIGN KEY("+FK_USER_ID_BOOKING+") REFERENCES "+TABLE_BOOKING+" ("+ID_BOOKING+"), "+
                        "FOREIGN KEY("+FK_FACE_ID_BOOKING+") REFERENCES "+TABLE_FACE+" ("+ID_FACE+")"+")";
        //TODO creating table of Car kind
        final String CREATE_TB_CAR_KIND=
                "CREATE TABLE "+TABLE_CAR_KIND+"("+
                        ID_CAR_KIND+" INTEGER PRIMARY KEY , "+
                        TXT_CAR_KIND+" VARCHAR UNIQUE);";

        //TODO creating table of Face
        final String CREATE_TB_FACE=
                "CREATE TABLE "+TABLE_FACE+"("+
                        ID_FACE+" INTEGER PRIMARY KEY , "+
                        WHERE_FACE+" VARCHAR UNIQUE);";

        //TODO creating table of Face
        final String CREATE_TB_RATE=
                "CREATE TABLE "+TABLE_RATE+"("+
                        ID_RATE+" INTEGER PRIMARY KEY , "+
                        DRIVER_RATE+" FLOAT, "+
                        FK_USER_ID_RATE+" INTEGER, "+
                        FK_DRIVER_ID_RATE+" INTEGER, "+
                        "FOREIGN KEY("+FK_USER_ID_RATE+") REFERENCES "+TABLE_USERS+" ("+ID_USERS+"), "+
                        "FOREIGN KEY("+FK_DRIVER_ID_RATE+") REFERENCES "+TABLE_DRIVER+" ("+ID_DRIVER+")"+")";


        sqLiteDatabase.execSQL(CREATE_TB_USER);
        sqLiteDatabase.execSQL(CREATE_TB_DRIVER);
        sqLiteDatabase.execSQL(CREATE_TB_BOOKING);
        sqLiteDatabase.execSQL(CREATE_TB_CAR_KIND);
        sqLiteDatabase.execSQL(CREATE_TB_FACE);
        sqLiteDatabase.execSQL(CREATE_TB_RATE);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        throw new UnsupportedOperationException("This shouldn't happen yet!");

    }

    //TODO work with TABLE_USER data====================================================
    public void addUser(String uid,String username, String email,  String imageURl, String phone,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( UID_USER, uid);
        values.put(USERNAME_USERS, username);
        values.put(EMAIL_USERS, email);
        values.put( IMAGE_USER,  imageURl );
        values.put( PHONE_USERS,  phone );
        values.put(TIME_USERS, created_at);
        db.insert(TABLE_USERS, null, values);

        Log.d(TAG, "Value In Sqlite: " +username+"  "+email+"  "+created_at);

        // Inserting Row
       // long id = db.insert(TABLE_USERS, null, values);
       db. insertWithOnConflict(TABLE_USERS, null, values,SQLiteDatabase.CONFLICT_REPLACE );
        db.close(); // Closing database connection

       // Log.d(TAG, "New user inserted into sqlite: " + errorr);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("time", cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USERS, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }


    //TODO work with TABLE_DRIVER data====================================================


    public void addDriver(String username,String uid,String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        SQLiteStatement insStmt = null;

        if (insStmt == null) { //compile it once
            String query = "INSERT into "+TABLE_DRIVER+"("+USERNAME_DRIVER+", "+UID_DRIVER+", "+EMAIL_DRIVER+") VALUES (?,?,?)";
            insStmt = db.compileStatement(query);
        }

        String [] input={username, uid, email};
        //bind the arguments
        insStmt.bindAllArgsAsStrings(input);
        //execute
        insStmt.execute();
        Log.d(TAG, "get new  driver inserted into sqlite: " + insStmt);
        db.close();

    }

    public void addDiverforList(String name,String uid,String model_car, String phone,String avg_rating ,String car_id) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


            values.put( USERNAME_DRIVER, name );
            values.put( UID_DRIVER, uid );
            values.put( CAR_MODEL_DRIVER, model_car );
            values.put( RAT_DRIVER, avg_rating );
            values.put( FK_CAR_ID_DRIVER, car_id );

        long query = db.insertWithOnConflict( TABLE_DRIVER, null, values, SQLiteDatabase.CONFLICT_REPLACE );
        db.insertWithOnConflict( TABLE_DRIVER, null, values, SQLiteDatabase.CONFLICT_REPLACE );

        Log.d( TAG, "add DRIVER list inserted into sqlite: " + query );


        db.close();
        }


    public Cursor getDriverListForClient(String car_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_DRIVER + " WHERE " + FK_CAR_ID_DRIVER + " = "+car_id+";";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public void deleteDriver() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_DRIVER, null, null);
        db.close();

        Log.d(TAG, "Deleted all Driver info from sqlite");
    }
    //TODO work with TABLE_BOOKING data====================================================
    public void addBookingList(String id,String name,String face,String time,String where ,String user_id,String car_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_BOOKING, id);
        values.put(NAME_BOOKING, name);
        values.put( FACE_BOOKING, face);
        values.put(TRAVEL_TIME_BOOKING, time);
        values.put(WHERE_FROM_BOOKING, where);
        values.put( FK_USER_ID_DRIVER, user_id);
        values.put(FK_CAR_ID_BOOKING, car_id);
      //  db.insert(TABLE_USERS, null, values);

        long query =  db.insertWithOnConflict(TABLE_BOOKING, null, values, SQLiteDatabase.CONFLICT_REPLACE);
          //  insStmt = db.compileStatement(query);

            db.insertWithOnConflict(TABLE_BOOKING, null, values, SQLiteDatabase.CONFLICT_REPLACE);


        //bind the arguments

        //execute
       // insStmt.execute();
        Log.d(TAG, "add booking list inserted into sqlite: " + query);
        db.close();
    }
    public Cursor getBookingList(String car_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM `listShow` where car_id="+car_id+"";

        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public void addCarKindSpinner(String id, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

            values.put( ID_CAR_KIND, id );
            values.put( TXT_CAR_KIND, name );

        long query = db.insertWithOnConflict( TABLE_CAR_KIND, null, values, SQLiteDatabase.CONFLICT_REPLACE );
        db.insertWithOnConflict( TABLE_CAR_KIND, null, values, SQLiteDatabase.CONFLICT_REPLACE );

        Log.d( TAG, "add TABLE_CAR_KIND list inserted into sqlite: " + query );

        db.close();
    }
    public void addFaceSpinner(String id, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


            values.put( ID_FACE, id );
            values.put( WHERE_FACE, name );

        long query = db.insertWithOnConflict( TABLE_FACE, null, values, SQLiteDatabase.CONFLICT_REPLACE );
        db.insertWithOnConflict( TABLE_FACE, null, values, SQLiteDatabase.CONFLICT_REPLACE );

        Log.d( TAG, "add TABLE_FACE list inserted into sqlite: " + query );

        db.close();
    }

   /* public String getCarKindId(String Dept) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        String val = null;
        if (c != null && c.moveToFirst()) {

                c = db.query( TABLE_CAR_KIND, new String[]{ID_CAR_KIND + " as id", TXT_CAR_KIND},
                        TXT_CAR_KIND + "=?", new String[]{Dept}, null, null, null );


            c.moveToFirst();

            val = c.getString( c.getColumnIndex( ID_CAR_KIND ) );
        }

        return val;

    }*/
    public String getFaceId(String heading)throws SQLException
    {


        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println("ddbpos="+heading);
        long recc=0;
        String rec=null;

        Cursor mCursor = db.rawQuery(
                "SELECT "+ID_FACE+"  FROM   "+TABLE_FACE+" WHERE "+WHERE_FACE+"='"+heading+"'" , null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
            recc=mCursor.getLong(0);
            rec=String.valueOf(recc);
        }
        return rec;

    }
    public String getCarKindId(String  heading) throws SQLException
    {
        SQLiteDatabase db = this.getReadableDatabase();

        System.out.println("ddbpos="+heading);
        long recc=0;
        String rec=null;

        Cursor mCursor = db.rawQuery(
                "SELECT "+ID_CAR_KIND+"  FROM   "+TABLE_CAR_KIND+" WHERE "+TXT_CAR_KIND+"='"+heading+"'" , null);
        if (mCursor != null)
        {
            mCursor.moveToFirst();
            recc=mCursor.getLong(0);
            rec=String.valueOf(recc);
        }
        return rec;
    }
}