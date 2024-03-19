package com.example.Domain.Repository;

import Domain.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MemoryRepository<T extends Entity> implements IRepository<T>{

    List<T> entities = new ArrayList<T>();
    @Override
    public void add(T entity) throws DuplicateEntityException, RepositoryException {
        if (entity == null)
            throw new IllegalArgumentException("Entity can not be null!");
        if(find(entity.getID())!= null)
            throw new DuplicateEntityException("Entity already exists!");
        entities.add(entity);
    }



    @Override
//    public void remove(int id) throws EntityNotFoundException {
//        if(find(id)==null)
//            throw new EntityNotFoundException("Entity doesn t exist!");
//        for (T entity: entities)
//        {
//            if(entity.getID()==id)
//                entities.remove(entity);
//        }
//    }
    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        Iterator<T> iterator = entities.iterator();
        while (iterator.hasNext()) {
            T entity = iterator.next();
            if (entity.getID() == id) {
                iterator.remove();
                break;
            }
        }
    }


    @Override
    public T find(int id) {
        for (T entity: entities)
        {
            if(entity.getID()==id)
                return entity;
        }
        return null;
    }


    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException {
        T oldEntity = find(id);
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        int index = entities.indexOf(oldEntity);
        entities.set(index, newEntity);
    }

    @Override
    public Collection getAll() throws RepositoryException {
        return new ArrayList<T>(entities);
    }

    @Override
    public Iterator iterator() {
        return new ArrayList<T>(entities).iterator();
    }
}
