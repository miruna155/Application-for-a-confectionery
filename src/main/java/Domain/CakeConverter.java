package Domain;

public class CakeConverter implements IEntityConverter<Cake> {
    @Override
    public String toString(Cake object) {
        return object.getID() + "," + object.getType() ;
    }

    @Override
    public Cake fromString(String line) {
        String[] tokens = line.split(",");
        return new Cake(Integer.parseInt(tokens[0]), tokens[1]);
    }
}
