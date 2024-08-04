package com.example.assignment_and103.model;

import java.util.ArrayList;

public class Responses<T> {
    private int status;
    private String messenger;
    private ArrayList<Fruit> data;

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

    public ArrayList<Fruit> getData() {
        return data;
    }

    public void setData(ArrayList<Fruit> data) {
        this.data = data;
    }
}
