package com.example.Domain.Tests;

import Domain.Cake;
import Domain.CakeConverter;
import Domain.Order;
import Domain.OrderConverter;
import org.junit.Test;


import java.time.LocalDate;
import java.util.ArrayList;

public class TestDomain {


    @Test
    public void testCake(){
        Cake cake = new Cake(1, "chocolate");
        assert cake.getID() == 1;
        assert "chocolate".equals(cake.getType());
        cake.setType("vanilla");
        assert "vanilla".equals(cake.getType());

    }

    @Test
    public void testOrder(){
        ArrayList<Cake> cakes = new ArrayList<>();
        Cake cake = new Cake(1, "chocolate");
        Cake cake2 = new Cake(2, "vanilla");
        cakes.add(cake);
        cakes.add(cake2);
        LocalDate date = LocalDate.of(2023,11,15);
        Order order = new Order(1, cakes, date);

        assert order.getCakes().size() == 2;
        assert order.getID() ==1;
        assert order.getDate().equals(LocalDate.of(2023,11,15));
    }

    @Test
    public void testCakeConverter(){
        Cake cake = new Cake(1, "chocolate");
        CakeConverter cakeConverter = new CakeConverter();

        String result = cakeConverter.toString(cake);
        assert result.equals("1,chocolate");

        String cakeString2 = "2,vanilla";
        Cake cake2 = cakeConverter.fromString(cakeString2);
        assert cake2.getID()==2;
        assert cake2.getType().equals("vanilla");
    }

    @Test
    public void testOrderConverter(){
        Cake cake1 = new Cake(1, "chocolate");
        Cake cake2 = new Cake(2, "vanilla");
        ArrayList<Cake> cakes = new ArrayList<>();
        cakes.add(cake1);
        cakes.add(cake2);
        LocalDate date = LocalDate.of(2023,11,15);
        Order order = new Order(1, cakes, date);

        OrderConverter orderconverter = new OrderConverter();
        String result = orderconverter.toString(order);
        assert result.equals("1;1,chocolate,2,vanilla;2023-11-15");

        String orderString = "1;1,chocolate;2023-11-15";
        Order order2 = orderconverter.fromString(orderString);
        assert order2.getCakes().size() == 1;
        assert order2.getID() ==1;
        assert order2.getDate().equals(LocalDate.of(2023,11,15));

    }

    public void testAll(){
        testCake();
        testOrder();
        testCakeConverter();
        testOrderConverter();
        System.out.println("testDomain passed");
    }

}
