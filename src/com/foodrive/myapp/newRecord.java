package com.foodrive.myapp;

public class newRecord {
    public int operationRecordId, driveId, volunteerId;
    public double  hoursContributed;
    public String operationDayDate, driveStartDate;

    public newRecord(){

    }

    public newRecord(int ori, String dsd, int di, int vi, double hours, String date){
        operationRecordId = ori;
        driveStartDate = dsd;
        driveId = di;
        volunteerId = vi;
        hoursContributed = hours;
        operationDayDate = date;
    }
}
