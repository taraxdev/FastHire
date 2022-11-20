package com.wtw.waytoworkers;

public class Rating {

    String itemid,workerratingid;
     float numberofrating,sumofrating,averagerating;

    public Rating(){

    }

    public  Rating(String itemid,String workerratingid,float numberofrating,float sumofrating,float averagerating){
        this.itemid=itemid;
        this.workerratingid=workerratingid;
        this.numberofrating=numberofrating;
        this.sumofrating=sumofrating;
        this.averagerating=averagerating;
    }


    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getWorkerratingid() {
        return workerratingid;
    }

    public void setWorkerratingid(String workerratingid) {
        this.workerratingid = workerratingid;
    }

    public float getNumberofrating() {
        return numberofrating;
    }

    public void setNumberofrating(float numberofrating) {
        this.numberofrating = numberofrating;
    }

    public float getSumofrating() {
        return sumofrating;
    }

    public void setSumofrating(float sumofrating) {
        this.sumofrating = sumofrating;
    }

    public float getAveragerating() {
        return averagerating;
    }

    public void setAveragerating(float averagerating) {
        this.averagerating = averagerating;
    }
}
