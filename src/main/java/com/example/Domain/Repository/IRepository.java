package com.example.Domain.Repository;

import Domain.Entity;

import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T> {
    public void add(T entity) throws DuplicateEntityException, RepositoryException;

    public void remove(int id) throws EntityNotFoundException, RepositoryException;

    public T find(int id);
    public void update(int id, T newEntity) throws EntityNotFoundException, RepositoryException;

    public Collection<T> getAll() throws RepositoryException;
}
