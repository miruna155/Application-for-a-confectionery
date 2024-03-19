package Domain;

import Domain.Entity;

public interface IEntityConverter<T extends Entity>{
    String toString(T object);

    T fromString(String line);
}
