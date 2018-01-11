package com.foodrive.myapp;

public class drive {

    public int id;
    public String start, name;

    public drive(){

    }

    public drive(String i, String s, String n){
        id = Integer.parseInt(i);
        start = s;
        name = n;
        //end = e;
    }

    public void setDrive(String s){
        start = s;
        //end = e;
    }

    public void setDrive(int i, String s, String n){
        id = i;
        start = s;
        name = n;
        //end = e;
    }



}
