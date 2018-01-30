package com.mashaweer.ibrahim.mashaweer.model;

/**
 * Created by Administrator on 08/07/2017.
 */

public class DriverModel {

    String SH_ID;
   // String rate;
    String NAME;
  //  String PHONE;
    String MODEL_CAR;
  //  float rating;
   // String latitude,longitude,distance;


    public String getSH_ID() {
        return SH_ID;
    }

    public void setSH_ID(String SH_ID) {
        this.SH_ID = SH_ID;
    }

    public DriverModel(){

}
    public DriverModel(String NAME, String MODEL_CAR, String SH_ID) {

    this.NAME=NAME;
    this.MODEL_CAR=MODEL_CAR;
    this.SH_ID=SH_ID;

    }



    /* public DriverModel(String NAME, String PHONE, String MODEL_CAR, double latitude, double longitude, double distance) {

                this.NAME = NAME;
                this.PHONE = PHONE;
                this.MODEL_CAR = MODEL_CAR;
                this.latitude = latitude;
                this.longitude = longitude;
                this.distance = distance;
            }
        */
    public String getMODEL_CAR() {
        return MODEL_CAR;
    }

    public void setMODEL_CAR(String MODEL_CAR) {
        this.MODEL_CAR = MODEL_CAR;
    }


    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }


}
