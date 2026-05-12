package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.application.contract.UserService;
import com.fedelesh.flowersalon.application.impl.AccessoryServiceImpl;
import com.fedelesh.flowersalon.application.impl.BouquetServiceImpl;
import com.fedelesh.flowersalon.application.impl.FlowerServiceImpl;
import com.fedelesh.flowersalon.application.impl.OrderServiceImpl;
import com.fedelesh.flowersalon.application.impl.UserServiceImpl;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.AccessoryRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.BouquetRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.FlowerRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderItemRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderRepositoryImpl;
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

    private final UserService userService =
          new UserServiceImpl(
                new UserRepositoryImpl()
          );

    private final FlowerService flowerService =
          new FlowerServiceImpl(
                new FlowerRepositoryImpl()
          );

    private final BouquetService bouquetService =
          new BouquetServiceImpl(
                new BouquetRepositoryImpl()
          );

    private final AccessoryService accessoryService =
          new AccessoryServiceImpl(
                new AccessoryRepositoryImpl()
          );

    private final OrderService orderService =
          new OrderServiceImpl(
                new OrderRepositoryImpl(),
                new OrderItemRepositoryImpl()
          );

    @FXML
    private TableView mainTableView;

    @FXML
    private Button createOrderButton;

    @FXML
    private Button editOrderButton;

    @FXML
    private Button deleteOrderButton;


    @FXML
    public void initialize() {

        createOrderButton.setVisible(false);
        createOrderButton.setManaged(false);
        editOrderButton.setVisible(false);
        editOrderButton.setManaged(false);
        deleteOrderButton.setVisible(false);
        deleteOrderButton.setManaged(false);

        createOrderButton.setOnAction(
              e -> openCreateOrderWindow()
        );

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
                    flowerService.getAll()
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
                    bouquetService.getAll()
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
                    accessoryService.getAll()
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
                    userService.getAll()
              )
        );
    }

    @FXML
    public void showOrders() {

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

        createOrderButton.setVisible(true);
        createOrderButton.setManaged(true);
        editOrderButton.setVisible(true);
        editOrderButton.setManaged(true);
        deleteOrderButton.setVisible(true);
        deleteOrderButton.setManaged(true);

        mainTableView.setItems(
              FXCollections.observableArrayList(
                    orderService.getAll()
              )
        );
    }

    @FXML
    private void openCreateOrderWindow() {

        try {

            FXMLLoader loader = new FXMLLoader(
                  getClass().getResource("/view/create-order-view.fxml")
            );

            Parent root = loader.load();

            Stage currentStage =
                  (Stage) mainTableView.getScene().getWindow();

            currentStage.getScene().setRoot(root);

            currentStage.setTitle("Create Order");

            currentStage.setMaximized(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditOrder() {

        Order selectedOrder =
              (Order) mainTableView
                    .getSelectionModel()
                    .getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(
                  getClass().getResource(
                        "/view/create-order-view.fxml"
                  )
            );

            Parent root = loader.load();

            CreateOrderController controller =
                  loader.getController();

            controller.loadOrder(selectedOrder);

            Stage currentStage =
                  (Stage) mainTableView
                        .getScene()
                        .getWindow();

            currentStage
                  .getScene()
                  .setRoot(root);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteOrder() {

        Order selectedOrder =
              (Order) mainTableView
                    .getSelectionModel()
                    .getSelectedItem();

        if (selectedOrder == null) {
            return;
        }

        orderService.delete(
              selectedOrder.getOrderId()
        );

        showOrders();
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