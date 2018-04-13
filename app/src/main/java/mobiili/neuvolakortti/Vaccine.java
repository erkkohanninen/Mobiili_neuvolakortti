package mobiili.neuvolakortti;

/**
 * Created by tanja on 13/04/2018.
 */

public class Vaccine {

    private String name;
    private String date;

    public Vaccine(){}
    public Vaccine(String name, String date){
        this.name = name;
        this.date = date;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getName(){
        return name;
    }

    public String getDate(){
        return date;
    }
}
