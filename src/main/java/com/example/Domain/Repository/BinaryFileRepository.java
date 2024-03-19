package com.example.Domain.Repository;

import Domain.Entity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class BinaryFileRepository <T extends Entity> extends MemoryRepository<T> {
    private final String fileName;

    public BinaryFileRepository(String fileName)
    {
        this.fileName = fileName;
    }

    @Override
    public void add(T entity) throws DuplicateEntityException
    {
        List<T> entities = loadEntities();
        if (entity == null)
        {
            throw new IllegalArgumentException("Entity can not be null");
        }

        if (find(entity.getID()) != null)
        {
            throw new DuplicateEntityException("Entity already exists");
        }

        entities.add(entity);
        saveEntities(entities);
    }

    @Override
    public void remove(int id)
    {
        List<T> entities = loadEntities();
        boolean removed = entities.removeIf(e -> e.getID() == id);
        if (!removed) {
            throw new IllegalArgumentException("Entity does not exist");
        }
        saveEntities(entities);
    }



    @Override
    public T find(int id)
    {
        List<T> entities = loadEntities();
        return entities.stream().filter(e -> e.getID() == id).findFirst().orElse(null);
    }

    @Override
    public Collection<T> getAll()
    {
        return new ArrayList<T>(loadEntities());
    }

    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException {
        List<T> entities = loadEntities();
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        int index=-1;
        for(T entity:entities)
            if(entity.getID()==id){
                index=entities.indexOf(entity);
            }
        //int index = entities.indexOf(find(id));
        entities.set(index, newEntity);
        saveEntities(entities);
    }

    private void saveEntities(List<T> entities)
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            outputStream.writeObject(entities);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<T> loadEntities()
    {
        List<T> entities = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName)))
        {
            entities = (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Repo starting a new file");
        }
        return entities;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ArrayList<T>(loadEntities()).iterator();
    }
}
