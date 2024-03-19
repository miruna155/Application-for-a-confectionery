package com.example.Domain.Tests;

import Domain.Cake;
import Domain.CakeConverter;
import com.example.Domain.Repository.*;
import org.junit.Test;

public class TestRepository {
    @Test
    void testAdd() {

        try {
            IRepository<Cake> repo = new MemoryRepository<>();
            Cake cake1 = new Cake(1, "chocolate");
            Cake cake2 = new Cake(2, "vanilla");
            repo.add(cake1);
            repo.add(cake2);
            assert repo.getAll().size() == 2;
            repo.add(cake1);
        } catch (DuplicateEntityException e) {
            assert true;
        } catch (RepositoryException e) {
            assert false;
        }

    }

    @Test
    void testRemove() {
        IRepository<Cake> repo = new MemoryRepository<>();
        Cake cake1 = new Cake(1, "chocolate");
        Cake cake2 = new Cake(2, "vanilla");
        try {
            repo.add(cake1);
            repo.add(cake2);
            repo.remove(1);
            assert repo.getAll().size() == 1;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }

        try {
            repo.remove(3);
        } catch (EntityNotFoundException e) {
            assert true;
        } catch (RepositoryException e) {
            assert false;
        }

    }

    @Test
    void testFind() {
        try {
            IRepository<Cake> repo = new MemoryRepository<>();
            Cake cake1 = new Cake(1, "chocolate");
            Cake cake2 = new Cake(2, "vanilla");
            repo.add(cake1);
            repo.add(cake2);
            assert repo.find(2).equals(cake2);
            assert repo.find(4) == null;
        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        }
    }

    @Test
    void testUpdate() {
        try {
            IRepository<Cake> repo = new MemoryRepository<>();
            Cake cake1 = new Cake(1, "chocolate");
            Cake cake2 = new Cake(1, "vanilla");
            repo.add(cake1);
            repo.update(1, cake2);
            Cake cake = repo.find(1);
            assert cake.getType().equals("vanilla");

        } catch (DuplicateEntityException e) {
            assert false;
        } catch (RepositoryException e) {
            assert false;
        } catch (EntityNotFoundException e) {
            assert false;
        }
    }
    @Test
    void testTextRepo(){
        try{
            CakeConverter converter = new CakeConverter();
            IRepository<Cake> repo = new TextFileRepository<>("test.txt", converter);
            assert repo.getAll().size()==1;
        } catch (RepositoryException e) {
            assert false;
    }
    }


        public void testAll(){
        testAdd();
        testUpdate();
        testFind();
        testRemove();
        testTextRepo();
        System.out.println("testRepo passed");
    }
}