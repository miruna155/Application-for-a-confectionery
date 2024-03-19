package Domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Order extends Entity {
    private ArrayList<Cake> cakes;
    private LocalDate date;
    private static final long serialVersionUID = 1000L;
    public Order(int id){
        super(id);
    }

    public Order(int id, ArrayList<Cake> cakes, LocalDate date){
        super(id);
        this.cakes = cakes;
        this.date = date;
    }

    public ArrayList<Cake> getCakes(){
        return this.cakes;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public void setCakes(ArrayList<Cake> cakes){
        this.cakes = cakes;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    @Override
    public String toString(){
        return "Order{" +
                "ID= "+ID+
                ", list of cakes= "+ cakes+
                ", date= " + date+
                "}";
    }
}
