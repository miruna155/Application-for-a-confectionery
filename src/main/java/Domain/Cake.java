package Domain;

public class Cake extends Entity {

    private static final long serialVersionUID = 1000L;
    private String type;

    public Cake(int ID, String type){
        super(ID);
        this.type = type;
    }

    public Cake(int id){
        super(id);
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    @Override
    public String toString(){
        return "Cake{" +
                "ID= "+ID+
                ", type= "+ type+
                "}";
    }
}
