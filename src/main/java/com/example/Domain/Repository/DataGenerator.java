package com.example.Domain.Repository;

import Domain.Cake;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private static final List<String> cakeTypes = List.of("Chocolate", "Vanilla", "Strawberry", "Lemon", "Red_Velvet", "Caramel", "Carrot", "Cheesecake", "Coconut", "Funfetti", "Pumpkin", "Raspberry", "Sponge", "Tiramisu", "Tres_Leches", "Banana", "Apple", "Pineapple", "Mango", "Peach", "Pear", "Plum", "Blueberry", "Cherry", "Cranberry", "Grape", "Kiwi", "Orange", "Pomegranate", "Watermelon", "Cinnamon", "Coffee", "Peanut_Butter", "Almond", "Hazelnut", "Pistachio", "Walnut", "Mint", "Coconut", "Oreo", "M&M", "Kit_Kat", "Snickers", "Twix", "Reese's", "Peanut_Butter_Cups", "Chocolate_Chips", "Chocolate_Sprinkles", "Rainbow_Sprinkles", "Marshmallows", "Caramel_Sauce", "Chocolate_Sauce", "Whipped_Cream", "Frosting", "Cream_Cheese", "Ganache", "Mascarpone", "Custard", "Lemon_Curd", "Jam", "Jelly", "Fruit", "Fruit_Puree", "Fruit_Sauce", "Fruit_Compote", "Fruit_Curd", "Fruit_Filling", "Fruit_Jam", "Fruit_Jelly", "Fruit_Ganache", "Fruit_Mousse", "Fruit_Cream", "Fruit_Cream_Cheese", "Fruit_Cream_Curd", "Fruit_Cream_Filling", "Fruit_Cream_Jam", "Fruit_Cream_Jelly", "Fruit_Cream_Ganache", "Fruit_Cream_Mousse", "Fruit_Cream_Puree", "Fruit_Cream_Sauce", "Fruit_Cream_Compote", "Fruit_Cream_Cake", "Fruit_Cream_Cheesecake", "Fruit_Cream_Curd_Cake", "Fruit_Cream_Curd_Cheesecake", "Fruit_Cream_Curd_Compote_Cake",
            "Fruit_Cream_Curd_Compote_Cheesecake", "Fruit_Cream_Curd_Compote_Cake_Cheesecake");
    private static final Random random = new Random();

    public static String generateRandomCakeType() {
        return cakeTypes.get(random.nextInt(cakeTypes.size()));
    }

    public static List<Cake> generateRandomCakes(List<Cake> allCakes, int numberOfCakes) {
        List<Cake> selectedCakes = new ArrayList<>();
        int totalCakes = allCakes.size();

        if (totalCakes == 0) {
            throw new IllegalArgumentException("List of cakes is empty.");
        }

        for (int i = 0; i < numberOfCakes; i++) {
            Cake randomCake = allCakes.get(random.nextInt(totalCakes));
            selectedCakes.add(randomCake);
        }

        return selectedCakes;
    }
}

