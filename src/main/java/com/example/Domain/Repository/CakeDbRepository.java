package com.example.Domain.Repository;

import Domain.Cake;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CakeDbRepository extends MemoryRepository<Cake> {
    private String JDBC_URL = "jdbc:sqlite:cofetarie.db";

    private Connection connection;


    public CakeDbRepository() {
        openConnection();
        createTable();
        insertRandomData();
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
        try (final Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS cakes(id int primary key, type varchar(400));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Cake cake) throws RepositoryException, DuplicateEntityException {
        super.add(cake);
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO cakes values (?,?);")) {
            stmt.setInt(1, cake.getID());
            stmt.setString(2, cake.getType());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Cake> getAll() {
        ArrayList<Cake> cakes = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * from cakes;")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cake c = new Cake(rs.getInt(1), rs.getString(2));
                cakes.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cakes;
    }

    public void remove(int id) throws EntityNotFoundException, RepositoryException{
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM cakes WHERE id = ?;")) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Cake cake) throws EntityNotFoundException, RepositoryException{
        Cake oldEntity = find(id);
        if (find(id) == null) {
            throw new EntityNotFoundException("Entity doesn't exist!");
        }
        try (PreparedStatement stmt = connection.prepareStatement("UPDATE cakes SET type = ? WHERE id = ?;")) {
            stmt.setString(1, cake.getType());
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cake find(int id) {
        String sql = "SELECT * FROM cakes WHERE id = ?";

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int cakeId = resultSet.getInt("id");
                    String type = resultSet.getString("type");
                    return new Cake(cakeId, type);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratarea erorilor în mod adecvat în producție
        }

        return null;
    }


    private static final List<String> cakeTypes = List.of("Chocolate", "Vanilla", "Strawberry", "Lemon", "Red_Velvet", "Caramel", "Carrot", "Cheesecake", "Coconut", "Funfetti", "Pumpkin", "Raspberry", "Sponge", "Tiramisu", "Tres_Leches", "Banana", "Apple", "Pineapple", "Mango", "Peach", "Pear", "Plum", "Blueberry", "Cherry", "Cranberry", "Grape", "Kiwi", "Orange", "Pomegranate", "Watermelon", "Cinnamon", "Coffee", "Peanut_Butter", "Almond", "Hazelnut", "Pistachio", "Walnut", "Mint", "Coconut", "Oreo", "M&M", "Kit_Kat", "Snickers", "Twix", "Reese's", "Peanut_Butter_Cups", "Chocolate_Chips", "Chocolate_Sprinkles", "Rainbow_Sprinkles", "Marshmallows",
            "Caramel_Sauce", "Chocolate_Sauce", "Whipped_Cream", "Frosting", "Cream_Cheese", "Ganache", "Mascarpone", "Custard", "Lemon_Curd", "Jam", "Jelly", "Fruit", "Fruit_Puree", "Fruit_Sauce", "Fruit_Compote", "Fruit_Curd", "Fruit_Filling", "Fruit_Jam", "Fruit_Jelly", "Fruit_Ganache", "Fruit_Mousse", "Fruit_Cream", "Fruit_Cream_Cheese", "Fruit_Cream_Curd", "Fruit_Cream_Filling", "Fruit_Cream_Jam", "Fruit_Cream_Jelly", "Fruit_Cream_Ganache", "Fruit_Cream_Mousse", "Fruit_Cream_Puree", "Fruit_Cream_Sauce", "Fruit_Cream_Compote", "Fruit_Cream_Cake", "Fruit_Cream_Cheesecake", "Fruit_Cream_Curd_Cake", "Fruit_Cream_Curd_Cheesecake");
    public void insertRandomData(){
        if(getAll().size() == 0)
        {
            Random random = new Random();
            for (int i = 1; i <= 100; i++) {
                String type = cakeTypes.get(random.nextInt(cakeTypes.size()));
                int randomIdCake = i;
                try {
                    add(new Cake(randomIdCake, type));
                } catch (RepositoryException e) {
                    throw new RuntimeException(e);
                } catch (DuplicateEntityException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void clear(){
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM cakes;")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}







