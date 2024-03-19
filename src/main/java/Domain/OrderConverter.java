package Domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderConverter implements IEntityConverter<Order> {
    @Override
    public String toString(Order order) {
        StringBuilder result = new StringBuilder();
        result.append(order.getID()).append(";");

        for (Cake cake : order.getCakes()) {
            result.append(cake.getID()).append(",").append(cake.getType()).append(",");
        }
        result.deleteCharAt(result.length() - 1); //stergem ultima virgula

        result.append(";").append(order.getDate());

        return result.toString();
    }

    @Override
    public Order fromString(String line) {
        String[] parts = line.split(";");

        if (parts.length >= 3) {
            int orderId = Integer.parseInt(parts[0]);
            ArrayList<Cake> cakes = new ArrayList<>();

            String[] cakeInfo = parts[1].split(",");
            for (int i = 0; i < cakeInfo.length; i += 2) {
                int cakeId = Integer.parseInt(cakeInfo[i]);
                String cakeType = cakeInfo[i + 1];
                cakes.add(new Cake(cakeId, cakeType));
            }

            LocalDate date = LocalDate.parse(parts[2]);

            return new Order(orderId, cakes, date);
        } else {
            throw new IllegalArgumentException("Invalid input format for Order: " + line);
        }

    }
}
