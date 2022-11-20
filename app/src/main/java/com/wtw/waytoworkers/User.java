package com.wtw.waytoworkers;

public class User {
    public String useremail,userid,username,userphone,usergender,usercity,useraddress,userimgurl;

    public User(){

    }

    public User(String useremail,String userid,String username, String userphone, String usergender,
                String usercity, String useraddress, String userimgurl) {
        this.useremail=useremail;
        this.userid= userid;
        this.username = username;
        this.userphone = userphone;
        this.usergender = usergender;
        this.usercity = usercity;
        this.useraddress = useraddress;
        this.userimgurl=userimgurl;
    }
    public String getUseremail() {
        return useremail;
    }

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getUserphone() {
        return userphone;
    }

    public String getUsergender() {
        return usergender;
    }

    public String getUsercity() {
        return usercity;
    }

    public String getUseraddress()  {
        return useraddress;
    }

    public String getUserimgurl(){return userimgurl;}

    //SETTERS
    public void setUseremail(String useremail){this.useremail=useremail;}

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public void setUsergender(String usergender) {
        this.usergender = usergender;
    }

    public void setUsercity(String usercity) {
        this.usercity = usercity;
    }

    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

    public void setUserimgurl(String userimgurl){ this.userimgurl=userimgurl;}
}
