package com.example.Domain;

import Domain.*;
import com.example.Domain.GUI.HelloApplication;
import com.example.Domain.Repository.*;
import com.example.Domain.Service.CakeService;
import com.example.Domain.Service.OrderService;
import com.example.Domain.Tests.TestCakeService;
import com.example.Domain.Tests.TestDomain;
import com.example.Domain.Tests.TestOrderService;
import com.example.Domain.Tests.TestRepository;
import com.example.Domain.UI.Console;

import java.util.Objects;

public class MainClass {
    public static void main(String[] args){
        /*CakeDbRepository cakeDbRepository = new CakeDbRepository();
        cakeDbRepository.openConnection();
        cakeDbRepository.closeConnection();

        OrderDbRepository orderDbRepository = new OrderDbRepository();
        orderDbRepository.openConnection();
        orderDbRepository.closeConnection();*/

        IEntityConverter<Cake> cakeConverter = new CakeConverter();
        IEntityConverter<Order> orderConverter = new OrderConverter();
        IRepository<Cake> repositoryCake= null;
        IRepository<Order> repositoryOrder = null;


        Settings setari = Settings.getInstance();
        if(Objects.equals(setari.getRepoType(), "database")){
            repositoryCake = new CakeDbRepository();
            repositoryOrder = new OrderDbRepository();
            ((CakeDbRepository) repositoryCake).openConnection();
            ((OrderDbRepository) repositoryOrder).openConnection();
            ((OrderDbRepository) repositoryOrder).insertRandomData(repositoryCake);

        }
        if (Objects.equals(setari.getRepoType(), "memory")) {
             repositoryCake = new MemoryRepository<>();
             repositoryOrder = new MemoryRepository<>();
        }
        if (Objects.equals(setari.getRepoType(), "text")){
            repositoryCake = new TextFileRepository<>(setari.getRepoCake(), cakeConverter);
            repositoryOrder = new TextFileRepository<>(setari.getRepoOrder(), orderConverter);
        }
        if (Objects.equals(setari.getRepoType(), "binary")){
            repositoryCake = new BinaryFileRepository<>(setari.getRepoCake());
            repositoryOrder = new BinaryFileRepository<>(setari.getRepoOrder());
        }

        TestDomain testDomain = new TestDomain();
        testDomain.testAll();
        TestRepository testRepository = new TestRepository();
        testRepository.testAll();
        TestCakeService testCakeService = new TestCakeService();
        testCakeService.testAll();
        TestOrderService testOrderService = new TestOrderService();
        testOrderService.testAll();
        System.out.println("\n");


        //repositoryCake = new BinaryFileRepository<>("cakes.bin");
        //repositoryOrder = new BinaryFileRepository<>("orders.bin");


        //IRepository<Cake> cakeFileRepository = new TextFileRepository<>("cakes.txt", cakeConverter);
        //IRepository<Order> orderFileRepository = new TextFileRepository<>("orders.txt", orderConverter);
        //IRepository<Cake> repositoryCake = new MemoryRepository<>();
        //CakeService cakeService = new CakeService(repositoryCake);
        //IRepository<Order> repositoryOrder = new MemoryRepository<>();
        //OrderService orderService = new OrderService(repositoryOrder);


        CakeService cakeService = new CakeService(repositoryCake);
        OrderService orderService = new OrderService(repositoryOrder);
        Console console = new Console(cakeService,orderService );


       // console.runMenu();

        HelloApplication.main(args);
    }
}
