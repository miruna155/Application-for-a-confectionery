package com.example.Domain.Repository;

import Domain.Cake;
import Domain.Order;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class OrderDbRepository extends MemoryRepository<Order> {

    private String JDBC_URL = "jdbc:sqlite:cofetarie.db";

    private Connection connection;

    public OrderDbRepository() {
        openConnection();
        createTable();
    }

    public void openConnection() {
        SQLiteDataSource ds = new SQLiteDataSource();
        ds.setUrl(JDBC_URL);

        try {
            if (connection == null || connection.isClosed()) {
                connection = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable() {

        try (final java.sql.Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS orders(id int primary key, date date);");
            stmt.execute("CREATE TABLE IF NOT EXISTS order_cakes(id INT AUTO_INCREMENT PRIMARY KEY,id_order int, id_cake int,foreign key(id_order) references orders(id), foreign key(id_cake) references cakes(id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Order order) throws RepositoryException, DuplicateEntityException {
        try (java.sql.PreparedStatement stmt = connection.prepareStatement("INSERT INTO orders values (?,?);")) {
            stmt.setInt(1, order.getID());
            stmt.setDate(2, java.sql.Date.valueOf(order.getDate()));
            stmt.executeUpdate();
            try (java.sql.PreparedStatement stmt2 = connection.prepareStatement("INSERT INTO order_cakes(id_order, id_cake) values (?,?);")) {
                for (int i = 0; i < order.getCakes().size(); i++) {
                    stmt2.setInt(1, order.getID());
                    stmt2.setInt(2, order.getCakes().get(i).getID());
                    stmt2.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(int id, Order order)throws EntityNotFoundException, RepositoryException{
        Order oldEntity = find(id);
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        try (java.sql.PreparedStatement stmt = connection.prepareStatement("UPDATE orders SET date=? WHERE id=?;")) {
            stmt.setDate(1, Date.valueOf(order.getDate()));
            stmt.setInt(2, order.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void remove(int id) throws EntityNotFoundException, RepositoryException{
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        try (java.sql.PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            java.sql.PreparedStatement stmt2 = connection.prepareStatement("DELETE FROM order_cakes WHERE id_order = ?;");
            stmt2.setInt(1, id);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Order find(int id) {
        try {
            // Query to fetch order details
            String orderQuery = "SELECT * FROM orders WHERE id = ?";
            try (PreparedStatement orderStmt = connection.prepareStatement(orderQuery)) {
                orderStmt.setInt(1, id);
                ResultSet orderRs = orderStmt.executeQuery();

                if (orderRs.next()) {
                    LocalDate orderDate = orderRs.getDate("date").toLocalDate();

                    // Query to fetch associated cakes
                    String cakeQuery = "SELECT c.id, c.type FROM order_cakes oc " +
                            "JOIN cakes c ON oc.id_cake = c.id " +
                            "WHERE oc.id_order = ?";
                    try (PreparedStatement cakeStmt = connection.prepareStatement(cakeQuery)) {
                        cakeStmt.setInt(1, id);
                        ResultSet cakeRs = cakeStmt.executeQuery();

                        ArrayList<Cake> cakes = new ArrayList<>();
                        while (cakeRs.next()) {
                            int cakeId = cakeRs.getInt("id");
                            String cakeType = cakeRs.getString("type");
                            Cake cake = new Cake(cakeId, cakeType);
                            cakes.add(cake);
                        }

                        return new Order(id, cakes, orderDate);
                    }
                }
            }
        } catch (SQLException e) {
            // Log the exception using a logging framework
            throw new RuntimeException("Error finding Order with ID " + id, e);
        }

        return null;
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM orders")){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                orders.add(find(id));
            }
            return orders;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM orders")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static List<Cake> generateRandomCakes(List<Cake> allCakes, int numberOfCakes) {
        List<Cake> selectedCakes = new ArrayList<>();
        int totalCakes = allCakes.size();

        // Limit the number of cakes to a maximum of 5
        numberOfCakes = Math.min(numberOfCakes, 5);

        List<Integer> indexes = new ArrayList<>();
        for (int i = 1; i <= totalCakes; i++) {
            indexes.add(i);
        }

        Collections.shuffle(indexes);

        for (int i = 0; i < numberOfCakes; i++) {
            int randomIndex = indexes.get(i);
            Cake randomCake = allCakes.get(randomIndex - 1); // Adjust index to be zero-based
            selectedCakes.add(randomCake);
        }

        return selectedCakes;
    }



    public void insertRandomData(IRepository<Cake> cakeRepository) {
        if (getAll().size() == 0) {
            try {
                Random random = new Random();
                for (int i = 1; i <= 100; i++) {
                    int numberOfCakes = random.nextInt(5) + 1; // +1 pt ca nu vrem sa fie 0 si vrem sa fie maxim 5
                    List<Cake> randomCakes = generateRandomCakes((List<Cake>) cakeRepository.getAll(), numberOfCakes);
                    LocalDate randomDate = LocalDate.now().minusDays(random.nextInt(365));
                    Order order = new Order(i, new ArrayList<>(randomCakes), randomDate);
                    add(order);
                }
            } catch (RepositoryException | DuplicateEntityException e) {
                e.printStackTrace();
            }
        }
    }




}

