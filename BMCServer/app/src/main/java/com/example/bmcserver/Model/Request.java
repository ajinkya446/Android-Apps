package com.example.bmcserver.Model;

import java.util.List;

public class Request {
    private String phone;
    private String address;
    private String name;
    private String total;
    private String status;
    private List<Order> item;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Request(String status) {
        this.status = status;
    }

    public Request(String phone, String address, String name, String total, List<Order> item) {
        this.phone = phone;
        this.address = address;
        this.name = name;
        this.total = total;
        this.item = item;
        this.status="0";   //default value.
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getItem() {
        return item;
    }

    public void setItem(List<Order> item) {
        this.item = item;
    }

    public Request() {
    }
}
