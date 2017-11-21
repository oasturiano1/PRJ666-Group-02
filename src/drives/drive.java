package drives;

import java.util.Date;

public class drive {

    public int id;
    public String start, end;

    public drive(){

    }

    public void setDrive(String s, String e){
        start = s;
        end = e;
    }

    public void setDrive(int i, String s, String e){
        id = i;
        start = s;
        end = e;
    }


}
