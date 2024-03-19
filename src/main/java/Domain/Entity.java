package Domain;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    private static final long serialVersionUID = 1000L;
    protected int ID;

    public Entity(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }
}