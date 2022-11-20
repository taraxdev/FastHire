package com.wtw.waytoworkers;

public class Booked {

    String currentuserid,bookedworkerid,datebooked;

    public Booked(){

    }

    public Booked(String currentuserid,String bookedworkerid,String datebooked){
        this.currentuserid=currentuserid;
        this.bookedworkerid=bookedworkerid;
        this.datebooked=datebooked;
    }

    public String getCurrentuserid() {
        return currentuserid;
    }

    public void setCurrentuserid(String currentuserid) {
        this.currentuserid = currentuserid;
    }

    public String getBookedworkerid() {
        return bookedworkerid;
    }

    public void setBookedworkerid(String bookedworkerid) {
        this.bookedworkerid = bookedworkerid;
    }

    public String getDatebooked() {
        return datebooked;
    }

    public void setDatebooked(String datebooked) {
        this.datebooked = datebooked;
    }
}
