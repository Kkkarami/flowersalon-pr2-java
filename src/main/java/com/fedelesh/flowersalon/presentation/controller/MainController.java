package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.AccessoryRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.BouquetRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.FlowerRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.UserRepositoryImpl;
import com.fedelesh.flowersalon.presentation.util.TableUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class MainController {

    private final UserRepositoryImpl userRepository =
          new UserRepositoryImpl();
    private final FlowerRepositoryImpl flowerRepository =
          new FlowerRepositoryImpl();
    private final BouquetRepositoryImpl bouquetRepository =
          new BouquetRepositoryImpl();
    private final AccessoryRepositoryImpl accessoryRepository =
          new AccessoryRepositoryImpl();
    private final OrderService orderService = null;
    @FXML
    private TableView mainTableView;
    @FXML
    private Button createOrderButton;

    @FXML
    public void initialize() {

        createOrderButton.setVisible(false);
        createOrderButton.setManaged(false);

        createOrderButton.setOnAction(e -> {
            System.out.println("OPEN CREATE ORDER WINDOW");
        });

        Platform.runLater(this::showFlowers);
    }

    private void resetTable() {

        mainTableView.getColumns().clear();
        mainTableView.getItems().clear();

        createOrderButton.setVisible(false);
        createOrderButton.setManaged(false);
    }

    private void setupTable(TableColumn<?, ?>... columns) {

        resetTable();

        mainTableView.getColumns().addAll(columns);

        mainTableView.setColumnResizePolicy(
              TableView.CONSTRAINED_RESIZE_POLICY
        );
    }

    @FXML
    public void showFlowers() {

        setupTable(

              TableUtils.column("ID", "flowerId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Колір", "color"),
              TableUtils.column("Ціна", "price"),
              TableUtils.column("Кількість", "stockQuantity")
        );

        mainTableView.setItems(
              FXCollections.observableArrayList(
                    flowerRepository.findAll()
              )
        );
    }

    @FXML
    public void showBouquets() {

        setupTable(

              TableUtils.column("ID", "bouquetId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Опис", "description"),
              TableUtils.column("Custom", "custom"),
              TableUtils.column("Дата", "createdAt")
        );

        mainTableView.setItems(
              FXCollections.observableArrayList(
                    bouquetRepository.findAll()
              )
        );
    }

    @FXML
    public void showAccessories() {

        setupTable(

              TableUtils.column("ID", "accessoryId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Тип", "accessoryType"),
              TableUtils.column("Колір", "color"),
              TableUtils.column("Ціна", "price"),
              TableUtils.column("Кількість", "stockQuantity")
        );

        mainTableView.setItems(
              FXCollections.observableArrayList(
                    accessoryRepository.findAll()
              )
        );
    }

    @FXML
    public void showUsers() {

        setupTable(

              TableUtils.column("Ім'я", "firstName"),
              TableUtils.column("Прізвище", "lastName"),
              TableUtils.column("Email", "email"),
              TableUtils.column("Телефон", "phone"),
              TableUtils.column("Дата створення", "createdAt")
        );

        mainTableView.setItems(
              FXCollections.observableArrayList(
                    userRepository.findAll()
              )
        );
    }

    @FXML
    public void showOrders() {

        resetTable();

        createOrderButton.setVisible(true);
        createOrderButton.setManaged(true);

        setupTable(

              TableUtils.column("ID", "orderId"),
              TableUtils.column("Ім'я", "customerFirstName"),
              TableUtils.column("Прізвище", "customerLastName"),
              TableUtils.column("Телефон", "phone"),
              TableUtils.column("Бюджет", "budget"),
              TableUtils.column("Стиль", "style"),
              TableUtils.column("Колір", "preferredColor"),
              TableUtils.column("Дата", "createdAt")
        );

        if (orderService != null) {

            mainTableView.setItems(
                  FXCollections.observableArrayList(
                        orderService.getAll()
                  )
            );
        }
    }

    @FXML
    private void handleLogout() {

        try {

            FXMLLoader loader = new FXMLLoader(
                  getClass().getResource("/view/login-view.fxml")
            );

            Parent root = loader.load();

            Stage loginStage = new Stage();

            loginStage.setTitle("Login");

            loginStage.setScene(new Scene(root));

            loginStage.show();

            Stage currentStage =
                  (Stage) mainTableView.getScene().getWindow();

            currentStage.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}