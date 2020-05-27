package com.ajinkya.addboat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("boat_registration")
    Call<DefaultResponse> addBoat(
            @Field("owner_name") String oname,
            @Field("owner_address") String oaddr,
            @Field("owner_mobile") String mobile,
            @Field("licence_number") String lno,
            @Field("boat_registration") String brgst,
            @Field("boat_no") String bno,
            @Field("boat_society") String society,
            @Field("boat_name") String bname,
            @Field("insurance_number") String ino,
            @Field("taluka_border") String tborder
    );

}
