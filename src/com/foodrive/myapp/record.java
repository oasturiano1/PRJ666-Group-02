package com.foodrive.myapp;

public class record {



    public int id,oprecid;
    public String fname,lname, mail, phone, supervisor, ename, ephone;
    public Double hoursCon;

    public record(){

    }

    public record(int rid, int opid, String finame, String laname, String email, String number, String superv, String cname, String cphone, double hours){
        id = rid;
        oprecid = opid;
        fname = finame;
        lname = laname;
        mail = email;
        phone = number;
        supervisor = superv;
        ename = cname;
        ephone = cphone;
        hoursCon = hours;
    }

}
