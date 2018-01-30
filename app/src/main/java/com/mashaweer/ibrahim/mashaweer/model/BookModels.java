package com.mashaweer.ibrahim.mashaweer.model;

/**
 * Created by Administrator on 28/06/2017.
 */

public class BookModels {
    String id;
    String face;
    String traveTime;
    String user_id;
    String car_id;
    String username;
    String phone;
    String wherefrom;
    double latitude,longitude,distance;
    public BookModels(){

    }

    public BookModels(String id, String name, String face, String traveTime, String wherefrom, String user_id, String car_id){
        this.id=id;
        this.username=name;
        this.face=face;
        this.traveTime=traveTime;
        this.wherefrom=wherefrom;
        this.user_id=user_id;
        this.car_id=car_id;
    }
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getLatitude() {



        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getWherefrom() {
        return wherefrom;
    }

    public void setWherefrom(String wherefrom) {
        this.wherefrom = wherefrom;
    }

    public String getFace() {
        return face;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getTraveTime() {
        return traveTime;
    }

    public void setTraveTime(String traveTime) {
        this.traveTime = traveTime;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
