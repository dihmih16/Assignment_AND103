package com.example.assignment_and103.model;

import java.util.ArrayList;

public class Response <T>{
    private int status;
    private String messenger;
    private T data;
    private String token;
    private String refreshToken;
    ArrayList<Fruit> data1;


    public Response(int status, String messenger, T data) {
        this.status = status;
        this.messenger = messenger;
        this.data = data;
    }

    public Response() {
    }

    public ArrayList<Fruit> getData1() {
        return data1;
    }

    public void setData1(ArrayList<Fruit> data1) {
        this.data1 = data1;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

