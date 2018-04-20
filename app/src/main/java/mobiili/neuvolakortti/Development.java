package mobiili.neuvolakortti;

/**
 * Created by tanja on 13/04/2018.
 */

public class Development {

    private String id;
    private String name;
    private String date;

    public Development(){}
    public Development(String name, String date, String id){
        this.name = name;
        this.date = date;
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setId(String id) { this.id = id; }

    public String getName(){
        return name;
    }

    public String getDate(){return date;}

    public String getId(){ return id;}
}
