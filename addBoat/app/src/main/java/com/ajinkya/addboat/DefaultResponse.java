package com.ajinkya.addboat;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DefaultResponse {


    private String authentication;

    public DefaultResponse(String authentication) {
        this.authentication = authentication;
    }

    public String getAuthentication() {
        return authentication;
    }
}
