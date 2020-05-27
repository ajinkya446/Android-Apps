package com.ajinkya.majigold.Model;

public class Billing {
    private int b_id;
    private String c_id;
    private String date;
    private String order_name;
    private String bill_status;
    private String remainingAmt;
    private String repairingAmt;
    private String total_ammount;
    private String weight;
    private String cname;
    private String caddr;
    private String contact;

    public Billing(int b_id, String c_id, String date, String order_name, String bill_status, String remainingAmt,
                   String repairingAmt, String total_ammount, String weight, String cname, String caddr, String contact) {
        this.b_id = b_id;
        this.c_id = c_id;
        this.date = date;
        this.order_name = order_name;
        this.bill_status = bill_status;
        this.remainingAmt = remainingAmt;
        this.repairingAmt = repairingAmt;
        this.total_ammount = total_ammount;
        this.weight = weight;
        this.cname = cname;
        this.caddr = caddr;
        this.contact = contact;
    }

    public int getB_id() {
        return b_id;
    }

    public void setB_id(int b_id) {
        this.b_id = b_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public String getBill_status() {
        return bill_status;
    }

    public void setBill_status(String bill_status) {
        this.bill_status = bill_status;
    }

    public String getRemainingAmt() {
        return remainingAmt;
    }

    public void setRemainingAmt(String remainingAmt) {
        this.remainingAmt = remainingAmt;
    }

    public String getRepairingAmt() {
        return repairingAmt;
    }

    public void setRepairingAmt(String repairingAmt) {
        this.repairingAmt = repairingAmt;
    }

    public String getTotal_ammount() {
        return total_ammount;
    }

    public void setTotal_ammount(String total_ammount) {
        this.total_ammount = total_ammount;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCaddr() {
        return caddr;
    }

    public void setCaddr(String caddr) {
        this.caddr = caddr;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
