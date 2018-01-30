package com.mashaweer.ibrahim.mashaweer.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ibrahim on 21/01/18.
 */

public class ResponseApiModel {
    @SerializedName("error")
    String error;
    @SerializedName("error_msg")
    String error_msg;

    public String getEerror() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}