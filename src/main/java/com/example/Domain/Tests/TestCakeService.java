package com.example.Domain.Tests;

import Domain.Cake;
import com.example.Domain.Repository.*;
import com.example.Domain.Service.CakeService;
import org.junit.Test;


public class TestCakeService {
    private IRepository<Cake> repo = new MemoryRepository<>();
    private CakeService service = new CakeService(repo);


    @Test
    void testAddCake(){
        try {
            service.add(1, "vanilla");
            assert service.getAll().size() ==1;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }
    }


    @Test
    void testDeleteCake(){
        try {
            service.add(1, "vanilla");
            service.add(2, "fruits");
            service.delete(1);
            assert service.getAll().size()==1;
            service.delete(5);
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            assert true;
        }
    }

    @Test
    void testFindCake(){
        try {
            service.add(1, "vanilla");
            assert service.find(5) == null;
            Cake cake = service.find(1);
            assert cake.getID() == 1;
            assert cake.getType().equals("vanilla");
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }
    }

    @Test
    void testUpdateCake(){
        try {
            service.add(1, "vanilla");
            service.update(1, "fruits");
            Cake cake = service.find(1);
            assert cake.getType().equals("fruits");
            service.update(2, "vanilla");
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            assert true;
        }
    }

    public void testAll(){
        testAddCake();
        testFindCake();
        testDeleteCake();
        testUpdateCake();
        System.out.println("tests Cake com.example.Domain.Service passed");
    }


}
