package com.wtw.waytoworkers;

public class Post {

    String dpurl,cuname,cuid;
    String posturl,caption;

    public Post(){

    }
    public  Post(String dpurl,String cuname,String cuid,String posturl,String caption){
        this.dpurl=dpurl;
        this.cuname=cuname;
        this.cuid=cuid;
        this.posturl=posturl;
        this.caption=caption;
    }

    //SETTER
    public String getDpurl() {
        return dpurl;
    }

    public void setDpurl(String dpurl) {
        this.dpurl = dpurl;
    }

    public String getCuname() {
        return cuname;
    }

    public void setCuname(String cuname) {
        this.cuname = cuname;
    }

    //GETTER
    public String getCuid() {
        return cuid;
    }

    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    public String getPosturl() {
        return posturl;
    }

    public void setPosturl(String posturl) {
        this.posturl = posturl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
