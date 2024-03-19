package com.example.Domain.Service;

import Domain.Cake;
import Domain.Order;
import com.example.Domain.Repository.DuplicateEntityException;
import com.example.Domain.Repository.EntityNotFoundException;
import com.example.Domain.Repository.IRepository;
import com.example.Domain.Repository.RepositoryException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class OrderService {
    IRepository<Order> repository;

    public OrderService(IRepository repository){
        this.repository = repository;
    }

    public void add(int id, ArrayList<Cake> cakes, LocalDate date) throws DuplicateEntityException, RepositoryException {
        this.repository.add(new Order(id, cakes, date));
    }

    public void remove(int id) throws EntityNotFoundException, RepositoryException {
        this.repository.remove(id);
    }

    public Order find(int id){return repository.find(id);}

    public void update(int id, LocalDate date) throws EntityNotFoundException, RepositoryException {
        if(find(id)==null)
            throw new EntityNotFoundException("Entity doesn t exist!");
        //find(id).setDate(date);
        ArrayList<Cake> cakes = find(id).getCakes();
        repository.update(id,new Order(id,cakes,date));
    }

    public Collection<Order> getAll() throws RepositoryException {
        return repository.getAll();
    }
}
