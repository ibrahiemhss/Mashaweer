package com.mashaweer.ibrahim.mashaweer.Network;

import com.mashaweer.ibrahim.mashaweer.model.ResponseApiModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;


/**
 * Created by ibrahim on 19/01/18.
 */

public interface BaseApiService {
    @FormUrlEncoded
    @POST("logInUser/loginUser.php")
    Call<ResponseBody> loginRequest(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("token") String token);
    @FormUrlEncoded
    @POST("logInDriver/loginDriver.php")
    Call<ResponseBody> loginRequestDriver(@Field("email") String email,
                                    @Field("password") String password,
                                    @Field("token") String token);
    @FormUrlEncoded
    @POST("logInUser/registerUser.php")
    Call<ResponseBody> registerRequest(@Field("name") String name,
                                       @Field("email") String email,
                                       @Field("password") String password,
                                       @Field("phone") String phone);

    @Multipart
    @POST("logInUser/registerUserWithImage.php")
    Call<ResponseApiModel> uploadImage (@PartMap() Map<String, RequestBody> partMap,
                                        @Part MultipartBody.Part image);
    @FormUrlEncoded

    @POST("ResetPassword/")
    Call<ResponseBody> resetPassword(@Field("email") String email);

}
