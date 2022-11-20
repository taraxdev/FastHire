package com.wtw.waytoworkers;

import android.os.Parcel;
import android.os.Parcelable;

public class Worker implements Parcelable {
    public String workeremail,workerid,workerdept,workername,workerphone,workergender,workercity,
            workeraddress,workerage,workerrate,workercpd,workerimageurl,workeravailable;
    float workerrating;
    public Worker(){

    }

    public Worker(String workeremail,String workerid, String workerdept, String workername, String workerphone,
                  String workergender, String workercity, String workeraddress, String workerage,String workerrate,
                  String workercpd,String workerimageurl,String workeravailable,float workerrating) {


        this.workeremail=workeremail;
        this.workerid = workerid;
        this.workerdept = workerdept;
        this.workername = workername;
        this.workerphone = workerphone;
        this.workergender = workergender;
        this.workercity = workercity;
        this.workeraddress = workeraddress;
        this.workerage=workerage;
        this.workerrate=workerrate;
        this.workercpd=workercpd;
        this.workerimageurl=workerimageurl;
        this.workeravailable=workeravailable;
        this.workerrating=workerrating;
    }

    protected Worker(Parcel in) {
        workeremail=in.readString();
        workerid = in.readString();
        workerdept = in.readString();
        workername = in.readString();
        workerphone = in.readString();
        workergender = in.readString();
        workercity = in.readString();
        workeraddress = in.readString();
        workerage=in.readString();
        workerrate=in.readString();
        workercpd=in.readString();
        workerimageurl=in.readString();
        workeravailable=in.readString();
        workerrating=in.readFloat();
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };

    public float getWorkerrating() {
        return workerrating;
    }

    public void setWorkerrating(float workerrating) {
        this.workerrating = workerrating;
    }

    //GETTERS
    public String getWorkeremail() {
        return workeremail;
    }

    public String getWorkerid() {
        return workerid;
    }

    public String getWorkerdept() {
        return workerdept;
    }

    public String getWorkername() {
        return workername;
    }

    public String getWorkerphone() {
        return workerphone;
    }

    public String getWorkergender() {
        return workergender;
    }

    public String getWorkercity() {
        return workercity;
    }

    public String getWorkerage() {
        return workerage;
    }

    public String getWorkerrate() {
        return workerrate;
    }

    public String getWorkeraddress() {
        return workeraddress;
    }

    public String getWorkercpd() {
        return workercpd;
    }

    public String getWorkerimageurl(){ return  workerimageurl;}

    public String getWorkeravailable(){ return  workeravailable;}

    //SETTERS
    public void setWorkeremail(String workeremail) {
        this.workeremail = workeremail;
    }

    public void setWorkerid(String workerid) {
        this.workerid = workerid;
    }

    public void setWorkerdept(String workerdept) {
        this.workerdept = workerdept;
    }

    public void setWorkername(String workername) {
        this.workername = workername;
    }

    public void setWorkerphone(String workerphone) {
        this.workerphone = workerphone;
    }

    public void setWorkergender(String workergender) {
        this.workergender = workergender;
    }

    public void setWorkercity(String workercity) {
        this.workercity = workercity;
    }

    public void setWorkeraddress(String workeraddress) {
        this.workeraddress = workeraddress;
    }

    public void setWorkerage(String workerage) {
        this.workerage = workerage;
    }

    public void setWorkerrate(String workerrate) {
        this.workerrate = workerrate;
    }

    public void setWorkercpd(String workercpd) {
        this.workercpd = workercpd;
    }

    public void setWorkerimageurl(String workerimageurl){ this.workerimageurl=workerimageurl;}

    public void setWorkeravailable(String workeravailable){ this.workeravailable=workeravailable;}


    @Override
    public int describeContents() {

         return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workeremail);
        dest.writeString(workerid);
        dest.writeString(workerdept);
        dest.writeString(workername);
        dest.writeString(workerphone);
        dest.writeString(workergender);
        dest.writeString(workercity);
        dest.writeString(workeraddress);
        dest.writeString(workerage);
        dest.writeString(workerrate);
        dest.writeString(workercpd);
        dest.writeString(workerimageurl);
        dest.writeString(workeravailable);
    }
}
