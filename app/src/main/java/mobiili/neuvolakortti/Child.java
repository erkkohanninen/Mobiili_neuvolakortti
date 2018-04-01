package mobiili.neuvolakortti;

/**
 * Created by tanja on 01/04/2018.
 */

public class Child {

    private int id;
    private String name;
    private String dateOfBirth;
    private float weight;
    private float height;
    private float head;

    public Child()
    {
    }

    public Child(String name, String dateOfBirth, float weight,
                 float height, float head)
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

    public void setName(String name){
        this.name = name;
    }

    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void setWeight(float weight){
        this.weight = weight;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public void setHead(float head){
        this.head = head;
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

    public float getWeight(){
        return weight;
    }

    public float getHeight(){
        return height;
    }

    public float getHead(){
        return head;
    }

}
