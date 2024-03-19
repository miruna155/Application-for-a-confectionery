package com.example.Domain.Tests;

import Domain.Cake;
import Domain.Order;
import com.example.Domain.Repository.*;
import com.example.Domain.Service.OrderService;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestOrderService {
    private IRepository<Order> repo = new MemoryRepository<>();
    private OrderService service = new OrderService(repo);
    ArrayList<Cake> cakes = new ArrayList<>();
    Cake cake = new Cake(1, "chocolate");
    Cake cake2 = new Cake(2, "vanilla");
    LocalDate date = LocalDate.of(2023, 11, 15);
    @Test
    void testAddOrder() {
        try {
            cakes.add(cake);
            cakes.add(cake2);
            service.add(1, cakes, date);
            assert service.getAll().size() == 1;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }
    }

    @Test
    void testDeleteOrder() {
        try {
            cakes.add(cake);
            cakes.add(cake2);
            service.add(1, cakes, date);
            assert service.getAll().size() == 1;
            service.remove(1);
            assert service.getAll().size() == 0;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            assert false;
        }
    }

    @Test
    void testFindOrder() {
        try {
            cakes.add(cake);
            cakes.add(cake2);
            service.add(1, cakes, date);
            assert service.find(4) == null;
            Order order = service.find(1);
            assert order.getID() ==1;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }
    }

    @Test
    void testUpdateOrder(){
        try {
            cakes.add(cake);
            cakes.add(cake2);
            service.add(1, cakes, date);
            LocalDate new_date = LocalDate.of(2023, 11, 18);
            service.update(1, new_date);
            Order order = service.find(1);
            assert order.getDate().equals(new_date);
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void testAll(){
        testAddOrder();
        testDeleteOrder();
        testFindOrder();
        testUpdateOrder();
        System.out.println("com.example.Domain.Tests Order com.example.Domain.Service passed");
    }

}
