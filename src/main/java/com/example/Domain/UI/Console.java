package com.example.Domain.UI;

import Domain.Cake;
import Domain.Order;
import com.example.Domain.Repository.DuplicateEntityException;
import com.example.Domain.Repository.EntityNotFoundException;
import com.example.Domain.Repository.RepositoryException;
import com.example.Domain.Service.CakeService;
import com.example.Domain.Service.OrderService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class Console {
    CakeService cakeService;
    OrderService orderService;

    public Console(CakeService cakeService, OrderService orderService){
        this.cakeService = cakeService;
        this.orderService = orderService;
    }

    private void addCake(Scanner scanner){
        try {
            System.out.println("Give the id: ");
            int id = scanner.nextInt();
            System.out.println("Give the type: ");
            String type = scanner.next();
            this.cakeService.add(id, type);
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        } catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void deleteCake(Scanner scanner){
        try{
            System.out.println("Give the id of the cake you want to remove: ");int id = scanner.nextInt();
            cakeService.delete(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (RepositoryException e){
        System.out.println(e.toString());
    }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void updateCake(Scanner scanner){
        try{
            System.out.println("Give the id of the cake you want to update: ");
            int id= scanner.nextInt();
            System.out.println("Give the new type of the cake: ");
            String type = scanner.next();
            cakeService.update(id, type);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
    private void addOrder(Scanner scanner) {

        try {
            System.out.println("Give the id of the order: ");
            int id = scanner.nextInt();
            System.out.println("The cakes available are: ");
            showCakes();
            System.out.println("How many cakes do you want to order? ");
            int nr = scanner.nextInt();
            if(nr<=0)
                throw new IllegalArgumentException("The order must have at least one cake!");
            ArrayList<Cake> cakes = new ArrayList<>();
            for (int i = 0; i < nr; i++) {
                System.out.println("Give the id of the cake you want to order: ");
                int id_cake = scanner.nextInt();
                Cake cake = cakeService.find(id_cake);
                if(cake==null)
                    throw new EntityNotFoundException("This cake is not available!");
                cakes.add(cake);
            }
            System.out.println("Give the date of the order(yyyy-MM-dd): ");
            String dateString = scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            orderService.add(id, cakes, date);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        }
        catch (DateTimeParseException e) {
            System.out.println(e.toString());;
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void deleteOrder(Scanner scanner){
        try{
            System.out.println("Give the id of the order you want to delete: ");
            int id= scanner.nextInt();
            orderService.remove(id);
        }
        catch (EntityNotFoundException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    private void updateOrder(Scanner scanner){
        try{
            System.out.println("Give the id of the order you want to update: ");
            int id= scanner.nextInt();
            System.out.println("Give the new date of the order(yyyy-MM-dd): ");
            String dateString = scanner.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            orderService.update(id, date);
        } catch (DateTimeParseException e) {
            System.out.println(e.toString());;
        } catch (EntityNotFoundException e) {
            System.out.println(e.toString());
        }catch (RepositoryException e){
                System.out.println(e.toString());
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void showCakes(){
        try{
            Collection<Cake> cakes = cakeService.getAll();
            if(cakes.size()==0)
                System.out.println("There are no cakes");
            for(Cake cake:cakes)
                System.out.println(cake);
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public void showOrders(){
        try{
            Collection<Order> orders = orderService.getAll();
            if(orders.size()==0)
                System.out.println("There are no orders");
            for(Order order:orders)
                System.out.println(order);
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void addCakesDefault(){
        try{
            cakeService.add(1, "chocolate");
            cakeService.add(2, "vanilla");
            cakeService.add(3, "cheesecake");
            cakeService.add(4, "dark_chocolate");
            cakeService.add(5, "lemon");
        }
        catch (DuplicateEntityException e) {
            System.out.println(e.toString());
        }
         catch (RepositoryException e){
        System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void nrCakesPerDay(){
        try{
            Collection<Order> orders = orderService.getAll();
            Map<LocalDate, Long> cakesPerDay = orders.stream()
                    .collect(Collectors.groupingBy(
                            Order::getDate,
                            Collectors.summingLong(order -> order.getCakes().size())
                    ));
            cakesPerDay.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " cakes"));
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void nrCakesPerMonth(){
        try{
            Collection<Order> orders = orderService.getAll();
            Map<String, Long> cakesPerMonth = orders.stream()
                    .collect(Collectors.groupingBy(
                            order -> order.getDate().getMonth().toString(),
                            Collectors.summingLong(order -> order.getCakes().size())
                    ));
            cakesPerMonth.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " cakes"));
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void mostOrderedCakes(){
        try{
            Collection<Order> orders = orderService.getAll();
            Map<String, Long> cakesCount = orders.stream()
                    .flatMap(order -> order.getCakes().stream())
                    .collect(Collectors.groupingBy(
                            Cake::getType,
                            Collectors.counting()
                    ));
            cakesCount.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> {
                        String cakeType = entry.getKey();
                        long totalOrders = entry.getValue();
                        System.out.println("Cake: " + cakeType + ", Total number of orders: " + totalOrders);
                    });
        }
        catch (RepositoryException e){
            System.out.println(e.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }

    public void runMenu(){
        //addCakesDefault();
        while(true){
            printMenu();
            String option;
            Scanner scanner = new Scanner(System.in);
            option = scanner.next();
            switch (option){
                case "1":{
                    addCake(scanner);
                    break;
                }
                case "2":{
                    deleteCake(scanner);
                    break;
                }
                case "3":{
                    updateCake(scanner);
                    break;
                }
                case "4": {
                    addOrder(scanner);
                    break;
                }
                case "5": {
                    deleteOrder(scanner);
                    break;
                }
                case "6": {
                    updateOrder(scanner);
                    break;
                }
                case "7": {
                    nrCakesPerDay();
                    break;
                }
                case "8": {
                    nrCakesPerMonth();
                    break;
                }
                case "9": {
                    mostOrderedCakes();
                    break;
                }
                case "c":{
                    showCakes();
                    break;
                }
                case "o":{
                    showOrders();
                    break;
                }
                case "x":{
                    return;
                }
                default:{
                    System.out.println("Wrong option. Retry: ");
                }
            }
        }
    }

    private void printMenu(){
        System.out.println("1. Add cake");
        System.out.println("2. Remove a cake");
        System.out.println("3. Update a cake");
        System.out.println("4. Add an order: ");
        System.out.println("5. Remove an order: ");
        System.out.println("6. Update the date of an order: ");
        System.out.println("7. Number of cakes ordered every day: ");
        System.out.println("8. Number of cakes ordered per month: ");
        System.out.println("9.Most ordered cakes: ");
        System.out.println("c. Show all cakes");
        System.out.println("o. Show all orders: " );
        System.out.println("x. EXIT");
    }
}
