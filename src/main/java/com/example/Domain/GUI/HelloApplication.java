package com.example.Domain.GUI;

import Domain.Cake;
import Domain.Order;
import com.example.Domain.Repository.*;
import com.example.Domain.Service.CakeService;
import com.example.Domain.Service.OrderService;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.TextArea;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HelloApplication extends Application {
    TextField textFieldIdCake = new TextField();
    TextField textFieldType = new TextField();

    TextField textFieldIdOrder = new TextField();
    TextField textFieldDate = new TextField();

    //TextField textFieldCakes = new TextField();

    ObservableList<Cake> cakesOrdered = FXCollections.observableArrayList();
    ListView<Cake> cakesOrderedListView = new ListView<>(cakesOrdered);

    @Override
    public void start(Stage stage) throws IOException, RepositoryException {



        IRepository<Cake> cakeRepository = new CakeDbRepository();
        IRepository<Order> orderRepository = new OrderDbRepository();


        CakeService cakeService = new CakeService(cakeRepository);
        OrderService orderService = new OrderService(orderRepository);

        VBox mainVerticalBox = new VBox();
        mainVerticalBox.setPadding(new Insets(10));;
        mainVerticalBox.setSpacing(5);
        Scene scene = new Scene(mainVerticalBox, 800, 600);


        ObservableList<Cake> cakes = FXCollections.observableArrayList(cakeService.getAll());
        ListView<Cake> cakeListView = new ListView<>(cakes);
        cakeListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Cake cake = cakeListView.getSelectionModel().getSelectedItem();
                textFieldIdCake.setText(Integer.toString(cake.getID()));
                textFieldType.setText(cake.getType());
                }
            }
        );
        cakeListView.setPadding(new Insets(10));
        mainVerticalBox.getChildren().add(cakeListView);



        ObservableList<Order> orders = FXCollections.observableArrayList(orderService.getAll());
        ListView<Order> orderListView = new ListView<>(orders);
        orderListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Order order = orderListView.getSelectionModel().getSelectedItem();
                textFieldIdOrder.setText(Integer.toString(order.getID()));
                textFieldDate.setText(order.getDate().toString());
                cakesOrdered.setAll(order.getCakes());
            }
        });
        orderListView.setPadding(new Insets(10));
        mainVerticalBox.getChildren().add(orderListView);



        GridPane gridPaneCake = new GridPane();
        Label labelIdCake = new Label("Id_Cake: ");
        labelIdCake.setPadding(new Insets(10,0,10,0));

        Label labelType = new Label("Type: ");
        labelType.setPadding(new Insets(10,0,10,0));




        gridPaneCake.add(labelIdCake, 0, 0);
        gridPaneCake.add(labelType, 0, 1);
        gridPaneCake.add(textFieldIdCake, 1, 0);
        gridPaneCake.add(textFieldType, 1, 1);

        HBox buttonsCakeHorizontal = new HBox();
        buttonsCakeHorizontal.setSpacing(10);

        Button buttonAddCake = new Button("Add Cake");
        buttonAddCake.setPadding(new Insets(5));
        buttonsCakeHorizontal.getChildren().add(buttonAddCake);

        Button buttonUpdateCake = new Button("Update Cake");
        buttonUpdateCake.setPadding(new Insets(5));
        buttonsCakeHorizontal.getChildren().add(buttonUpdateCake);

        Button buttonDeleteCake = new Button("Delete Cake");
        buttonDeleteCake.setPadding(new Insets(5));

        buttonsCakeHorizontal.getChildren().add(buttonDeleteCake);



        mainVerticalBox.getChildren().add(gridPaneCake);
        mainVerticalBox.getChildren().add(buttonsCakeHorizontal);


        buttonAddCake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdCake.getText());
                    String type = textFieldType.getText();
                    cakeService.add(id, type);
                    cakes.add(cakeService.find(id));


                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonUpdateCake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdCake.getText());
                    String type = textFieldType.getText();
                    cakeService.update(id, type);
                    cakes.setAll(cakeService.getAll());
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonDeleteCake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdCake.getText());
                    cakeService.delete(id);
                    cakes.setAll(cakeService.getAll());
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        GridPane gridPaneOrder = new GridPane();
        Label labelIdOrder = new Label("Id_Order: ");
        labelIdOrder.setPadding(new Insets(10,0,10,0));

        Label labelDate = new Label("Date: ");
        labelDate.setPadding(new Insets(10,0,10,0));

        Label labelCakes = new Label("Cakes: ");
        labelCakes.setPadding(new Insets(10,0,10,0));

        gridPaneOrder.add(labelIdOrder, 0, 0);
        gridPaneOrder.add(labelDate, 0, 1);
        gridPaneOrder.add(labelCakes, 0, 2);
        gridPaneOrder.add(textFieldIdOrder, 1, 0);
        gridPaneOrder.add(textFieldDate, 1, 1);



        gridPaneOrder.add(cakesOrderedListView, 1, 2);

//        cakesOrderedListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                Cake cake = cakesOrderedListView.getSelectionModel().getSelectedItem();
//                textFieldIdCake.setText(Integer.toString(cake.getID()));
//                textFieldType.setText(cake.getType());
//            }
//        });



        HBox buttonsOrderHorizontal = new HBox();
        buttonsOrderHorizontal.setPadding(new Insets(5));
        buttonsOrderHorizontal.setSpacing(10);

        Button buttonAddCakeToOrder = new Button("Add Cake to order");
        buttonAddCakeToOrder.setPadding(new Insets(5));

        Button buttonAddOrder = new Button("Add Order");
        buttonAddOrder.setPadding(new Insets(5));

        Button buttonUpdateOrder = new Button("Update Order");
        buttonUpdateOrder.setPadding(new Insets(5));

        Button buttonDeleteOrder = new Button("Delete Order");
        buttonDeleteOrder.setPadding(new Insets(5));



        buttonsOrderHorizontal.getChildren().add(buttonAddCakeToOrder);
        buttonsOrderHorizontal.getChildren().add(buttonAddOrder);
        buttonsOrderHorizontal.getChildren().add(buttonUpdateOrder);
        buttonsOrderHorizontal.getChildren().add(buttonDeleteOrder);


        mainVerticalBox.getChildren().add(gridPaneOrder);
        mainVerticalBox.getChildren().add(buttonsOrderHorizontal);

        buttonAddCakeToOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdCake.getText());
                    Cake cake = cakeService.find(id);
                    cakesOrdered.add(cake);
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonAddOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdOrder.getText());
                    LocalDate date = LocalDate.parse(textFieldDate.getText());
                    ArrayList<Cake> cakesO = new ArrayList<>();

                    cakesOrdered.stream().forEach(cake -> cakesO.add(cake));
                    if (cakesO.size() == 0)
                        throw new Exception("The order must contain cakes!");
                    orderService.add(id, cakesO, date);
                    orders.add(orderService.find(id));
                    cakesOrdered.clear();

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonUpdateOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdOrder.getText());
                    LocalDate date = LocalDate.parse(textFieldDate.getText());
                    orderService.update(id,  date);
                    orders.setAll(orderService.getAll());

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        buttonDeleteOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(textFieldIdOrder.getText());
                    orderService.remove(id);
                    orders.setAll(orderService.getAll());

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        ListView resultArea = new ListView();
        resultArea.setPadding(new Insets(10));

        Button nrCakesPerDay = new Button("Number of cakes ordered per day");
        nrCakesPerDay.setPadding(new Insets(5));
        nrCakesPerDay.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mainVerticalBox.getChildren().remove(resultArea);
                    resultArea.getItems().clear();
                    Collection<Order> orders = orderService.getAll();
                    Map<LocalDate, Long> cakesPerDay = orders.stream()
                            .collect(Collectors.groupingBy(
                                    Order::getDate,
                                    Collectors.summingLong(order -> order.getCakes().size())
                            ));
                    cakesPerDay.entrySet().stream()
                            .sorted(Map.Entry.<LocalDate, Long>comparingByValue().reversed())
                            .forEachOrdered(date -> resultArea.getItems().add(date.getKey() + " " + date.getValue()));
                    mainVerticalBox.getChildren().add(resultArea);
                    //cakesPerDay.forEach((date, nrCakes) -> resultArea.getItems().add(date + " " + nrCakes));
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        Button nrCakesPerMonth = new Button("Number of cakes ordered per month");
        nrCakesPerMonth.setPadding(new Insets(5));
        nrCakesPerMonth.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mainVerticalBox.getChildren().remove(resultArea);
                    resultArea.getItems().clear();
                    Collection<Order> orders = orderService.getAll();
                    Map<Integer, Long> cakesPerMonth = orders.stream()
                            .collect(Collectors.groupingBy(
                                    order -> order.getDate().getMonthValue(),
                                    Collectors.summingLong(order -> order.getCakes().size())
                            ));
                    cakesPerMonth.entrySet().stream()
                            .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                            .forEachOrdered(month -> resultArea.getItems().add(month.getKey() + " " + month.getValue()));
                    mainVerticalBox.getChildren().add(resultArea);
                    //cakesPerMonth.forEach((month, nrCakes) -> resultArea.getItems().add(month.toString() + " " + nrCakes));
                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });

        Button mostOrderedCake = new Button("Most ordered cakes");
        mostOrderedCake.setPadding(new Insets(5));
        mostOrderedCake.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    mainVerticalBox.getChildren().remove(resultArea);
                    resultArea.getItems().clear();
                    Collection<Order> orders = orderService.getAll();
                    Map<String, Long> cakesCount = orders.stream()
                            .flatMap(order -> order.getCakes().stream())
                            .collect(Collectors.groupingBy(
                                    Cake::getType,
                                    Collectors.counting()
                            ));
                    cakesCount.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                            .forEachOrdered(cake -> resultArea.getItems().add(cake.getKey() + " " + cake.getValue()));
                    mainVerticalBox.getChildren().add(resultArea);

                }catch (Exception e)
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(e.getMessage());
                    alert.show();

                }
            }
        });





        GridPane gridPaneReports = new GridPane();
        gridPaneReports.setHgap(10);
        gridPaneReports.setPadding(new Insets(5));
        gridPaneReports.add(nrCakesPerDay, 0, 0);
        gridPaneReports.add(nrCakesPerMonth,1 , 0);
        gridPaneReports.add(mostOrderedCake, 2, 0);

        //mainVerticalBox.getChildren().add(resultArea);
        mainVerticalBox.getChildren().add(gridPaneReports);















        stage.setTitle("Cake Shop");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}



