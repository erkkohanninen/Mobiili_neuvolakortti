package mobiili.neuvolakortti;

/**
 * Created by tanja on 01/04/2018.
 */

public class Child {

    private int id;
    private String name;
    private String dateOfBirth;
    private String weight;
    private String height;
    private String head;

    public Child()
    {
    }

    public Child(String name, String dateOfBirth, String weight,
                 String height, String head)
    {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
        this.head = head;
    }

    public Child(String name, int id)
    {
        this.name = name;
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {

        return id;
    }

    public String getName(){
        return name;
    }

    public String getDateOfBirth(){
        return dateOfBirth;
    }

    public String getWeight(){
        return weight;
    }

    public String getHeight(){
        return height;
    }

    public String getHead(){
        return head;
    }

}
