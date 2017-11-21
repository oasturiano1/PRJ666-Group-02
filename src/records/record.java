package records;

import java.util.Date;

public class record {

    public int operationRecordId, driveId, volunteerId, hoursContributed;
    public String operationDayDate, driveStartDate;

    public record(){

    }

    public record(int ori, String dsd, int di, int vi, int hours, String date){
        operationRecordId = ori;
        driveStartDate = dsd;
        driveId = di;
        volunteerId = vi;
        hoursContributed = hours;
        operationDayDate = date;
    }

}
