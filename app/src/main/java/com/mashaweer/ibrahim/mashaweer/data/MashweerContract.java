package com.mashaweer.ibrahim.mashaweer.data;

import android.provider.BaseColumns;

/**
 * Created by ibrahim on 19/12/17.
 */

public class MashweerContract {
//كلاس حاص ب اسم قاعدة البيانات واسماء الجداول ومحتوياتها TODO calass for database name and tables names and its contents
    public static class MashweerEntry implements BaseColumns{
    public static final String BAS_LOGIN = "http://devsinai.com/mashaweer/";

    public static final String URL_SYNC_FACE = "http://devsinai.com/mashaweer/sync/face.php";
    public static final String URL_SYNC_CARKIND = "http://devsinai.com/mashaweer/sync/carKind.php";
    public static final String UURL_BOOK = "http://devsinai.com/mashaweer/sync/carKind.php";
    public static final String URL_LIST_DEIVER= "http://devsinai.com/mashaweer/DisplayData/ListDesplayForClient.php";




    public static final String TAG = "Learn2Crack";

    public static final String DATABASE_NAME="devsinai_mashaweer";
        //TODO table users
        public static final String TABLE_USERS="users";
        public static final String ID_USERS="id";
        public static final String USERNAME_USERS="name";
        public static final String EMAIL_USERS="email";
        public static final String UID_USER = "uid";
        public static final String TIME_USERS="time";
        public static final String IMAGE_USER="image";
        public static final String PASSWORD_USERS="password";
        public static final String PHONE_USERS="phone";
        public static final String LATITUDE_USERS="latitude";
        public static final String LONGITUDE_USERS="longitude";
        public static final String TOKEN_USERS="token";
        public static final String FK_CARS_ID_USERS="cars_id";
        public static final String FK_FACE_ID_USERS="faces_id";
        public static final String FORGET_PASSWORD_USERS="forgot_pass_identity";
        public static final String LOST_USERS="lost";

        //TODO table drivers
        public static final String TABLE_DRIVER="shofiers";
        public static final String ID_DRIVER="sh_id";
        public static final String UID_DRIVER="disp_id";
        public static final String USERNAME_DRIVER="name";
        public static final String PASSWORD_DRIVER="password";
        public static final String EMAIL_DRIVER="email";
        public static final String CAR_MODEL_DRIVER="model_car";
        public static final String PHONE_DRIVER="phone";
        public static final String DISTANCE_DRIVER="distance";
        public static final String RAT_DRIVER="avg_rating";
        public static final String LATITUDE_DRIVER="latitude";
        public static final String LONGITUDE_DRIVER="longitude";
        public static final String TOKEN_DRIVER="token";
        public static final String FK_USER_ID_DRIVER="user_id";
        public static final String FK_RATE_ID_DRIVER="rate_id";
        public static final String FK_CAR_ID_DRIVER="car_id";
        public static final String FK_BOOKING_ID_DRIVER="booking_id";
        public static final String STATUS_DRIVER="status_driver";

        //TODO table listShow
        public static final String TABLE_BOOKING="listShow";
        public static final String ID_BOOKING="b_id";
        public static final String NAME_BOOKING="name";
        public static final String FACE_BOOKING="face";
        public static final String TRAVEL_TIME_BOOKING="traveTime";
        public static final String WHERE_FROM_BOOKING="wherefrom";
        public static final String FK_CAR_ID_BOOKING="car_id";
        public static final String FK_USER_ID_BOOKING="user_id";
        public static final String FK_FACE_ID_BOOKING="face_id";

        //TODO table car kind
        public static final String TABLE_CAR_KIND="car_kind";
        public static final String ID_CAR_KIND="id_car";
        public static final String TXT_CAR_KIND="kind_car";

        //TODO table Face
        public static final String TABLE_FACE="Face";
        public static final String ID_FACE="id_face";
        public static final String WHERE_FACE="wherface";

        //TODO table rate
        public static final String TABLE_RATE="rate";
        public static final String ID_RATE="id";
        public static final String DRIVER_RATE="driv_rating";
        public static final String FK_USER_ID_RATE="user_id";
        public static final String FK_DRIVER_ID_RATE="sh_id";
    }
}
