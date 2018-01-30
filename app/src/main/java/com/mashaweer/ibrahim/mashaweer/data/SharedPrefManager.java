package com.mashaweer.ibrahim.mashaweer.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ibrahim on 30/12/17.
 */

public class SharedPrefManager {


    SharedPreferences pref;
    SharedPreferences.Editor editor;

    private static final String SHARED_PREF_NAME = "save_contents";

    public static final String USER_ID_KEY = "user_id";
    public static final String NAME_USERS_KEY = "name_users";
    public static final String EMAIL_USERS_KEY = "email_users";
    public static final String PHONE_USERS_KEY = "phone_users";
    public static final String IMAGE_USERS_KEY = "image_users";
    public static final String LATITUDE_USER_KEY = "latitude_user";
    public static final String LONGITUDE_USER_KEY = "longitude_user";


    public static final String DRIVER_ID_KEY = "driver_id";
   public static final String NAME_DRIVER_KEY = "name_driver";
    public static final String EMAIL_DRIVER_KEY = "email_driver";
    public static final String IMAGE_DRIVER_KEY = "image_driver";
    public static final String PHONE_DRIVER_KEY = "phone_driver";
    public static final String LATITUDE_DRIVER_KEY = "latitude_driver";
    public static final String LONGITUDE_DRIVER_KEY = "longitude_driver";

    public static final String FACE_ID_KEY = "face_id_key";
    public static final String CAR_ID_KEY = "car_id_key";

    private static final String KEY_IS_USER_LOGGEDIN = "isUserLoggedIn";
    private static final String KEY_IS_DRIVER_LOGGEDIN = "isDriverLoggedIn";




    private static final String TAG_TOKEN = "tagtoken";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    public SharedPrefManager(Context context) {
        mCtx = context;
        pref=mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }


//TODO==========================USERS SharedPreferences ======================================================
   public  boolean saveUserId( String userId) {
       editor = pref.edit();
       editor.putString( USER_ID_KEY, userId );
       editor.apply();
       editor.apply();
       return true;
   }

    public String getUserId() {

        return pref.getString( USER_ID_KEY, null );

    }

    public  boolean saveNamesOfUsers( String name) {
        editor = pref.edit();
        editor.putString( NAME_USERS_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getNamesOfUsers() {

        return pref.getString( NAME_USERS_KEY, null );

    }

    public  boolean saveEmailOfUsers( String email) {
        editor = pref.edit();
        editor.putString( EMAIL_USERS_KEY, email );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getEmailOfUsers() {

        return pref.getString( EMAIL_USERS_KEY, null );

    }
    public  boolean saveImagefUsers( String image) {
        editor = pref.edit();
        editor.putString( IMAGE_USERS_KEY, image );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getImageOfUsers() {

        return pref.getString( IMAGE_USERS_KEY, null );

    }
    public  boolean savePhonefUsers( String phone) {
        editor = pref.edit();
        editor.putString( PHONE_USERS_KEY, phone );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getPhoneOfUsers() {

        return pref.getString( PHONE_USERS_KEY, null );

    }
    public String getLatitudeOfUsers() {

        return pref.getString( LATITUDE_USER_KEY, null );

    }
    public  boolean saveLatitudeUsers( String lat) {
        editor = pref.edit();
        editor.putString( LATITUDE_USER_KEY, lat );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getLongitudeOfUsers() {

        return pref.getString( LONGITUDE_USER_KEY, null );

    }
    public  boolean saveLongitudeUsers( String longitude) {
        editor = pref.edit();
        editor.putString( LONGITUDE_USER_KEY, longitude );
        editor.apply();
        editor.apply();
        return true;
    }
    //TODO==========================DRIVER SharedPreferences======================================================
    public  boolean saveDriverId( String userId) {
        editor = pref.edit();
        editor.putString( USER_ID_KEY, userId );
        editor.apply();
        editor.apply();
        return true;
    }

    public String getDriverId() {

        return pref.getString( DRIVER_ID_KEY, null );

    }

    public  boolean saveNamesOfDrivers( String name) {
        editor = pref.edit();
        editor.putString( NAME_DRIVER_KEY, name );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getNamesOfDriver() {

        return pref.getString( NAME_DRIVER_KEY, null );

    }

    public  boolean saveEmailOfDriver( String email) {
        editor = pref.edit();
        editor.putString( EMAIL_DRIVER_KEY, email );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getEmailOfDriver() {

        return pref.getString( EMAIL_DRIVER_KEY, null );

    }
    public  boolean saveImagefDriver( String image) {
        editor = pref.edit();
        editor.putString( IMAGE_DRIVER_KEY, image );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getImageOfDriver() {

        return pref.getString( IMAGE_DRIVER_KEY, null );

    }
    public  boolean savePhonefDriver( String phone) {
        editor = pref.edit();
        editor.putString( PHONE_DRIVER_KEY, phone );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getPhoneOfDriver() {

        return pref.getString( PHONE_DRIVER_KEY, null );

    }
    public String getLatitudeOfDrivers() {

        return pref.getString( LATITUDE_DRIVER_KEY, null );

    }
    public  boolean saveLatitudeDrivers( String lat) {
        editor = pref.edit();
        editor.putString( LATITUDE_DRIVER_KEY, lat );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getLongitudeOfDrivers() {

        return pref.getString( LONGITUDE_DRIVER_KEY, null );

    }
    public  boolean saveLongitudeDrivers( String longitude) {
        editor = pref.edit();
        editor.putString( LONGITUDE_DRIVER_KEY, longitude );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getFaceId() {

        return pref.getString( FACE_ID_KEY, null );

    }
    public  boolean saveFaceId( String id) {
        editor = pref.edit();
        editor.putString( FACE_ID_KEY, id );
        editor.apply();
        editor.apply();
        return true;
    }
    public String getCarId() {

        return pref.getString( CAR_ID_KEY, null );

    }
    public  boolean saveCarId( String id) {
        editor = pref.edit();
        editor.putString( CAR_ID_KEY, id );
        editor.apply();
        editor.apply();
        return true;
    }
//TODO============================LOGIN SharedPreferences=======================================================

    public void setLoginUser(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean( KEY_IS_USER_LOGGEDIN, isLoggedIn );
        editor.apply();
        editor.commit();

    }


    public boolean isUserLoggedIn() {
        return pref.getBoolean( KEY_IS_USER_LOGGEDIN, false );

    }

    public void setLoginDriver(boolean isLoggedIn) {
        editor = pref.edit();
        editor.putBoolean( KEY_IS_DRIVER_LOGGEDIN, isLoggedIn );
        editor.apply();
        editor.commit();

    }

    public boolean isDriverLoggedIn() {
        return pref.getBoolean( KEY_IS_DRIVER_LOGGEDIN, false );
    }

    public boolean saveDeviceToken(String token){
        editor = pref.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }
    //fetch the device token
    public String getDeviceToken(){
        pref = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  pref.getString(TAG_TOKEN, null);
    }
}