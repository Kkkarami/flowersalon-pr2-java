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
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.AccessoryRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.BouquetRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.FlowerRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderItemRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.UserRepositoryImpl;
import com.fedelesh.flowersalon.presentation.MainApplication;
import com.fedelesh.flowersalon.presentation.util.TableUtils;
import com.fedelesh.flowersalon.presentation.viewmodel.LoginViewModel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final FlowerService flowerService = new FlowerServiceImpl(new FlowerRepositoryImpl());
    private final BouquetService bouquetService = new BouquetServiceImpl(new BouquetRepositoryImpl());
    private final AccessoryService accessoryService = new AccessoryServiceImpl(new AccessoryRepositoryImpl());

    private final OrderService orderService = new OrderServiceImpl(
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
    private Button pickUpOrderButton;

    @FXML
    public void initialize() {
        hideOrderButtons();

        createOrderButton.setOnAction(event -> openCreateOrderWindow());

        mainTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            updatePickUpButtonVisibility();
        });

        mainTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                updatePickUpButtonVisibility();
            }
        });

        Platform.runLater(this::showFlowers);
    }

    private void resetTable() {
        mainTableView.getColumns().clear();
        mainTableView.getItems().clear();

        hideOrderButtons();
    }

    private void hideOrderButtons() {
        createOrderButton.setVisible(false);
        createOrderButton.setManaged(false);

        editOrderButton.setVisible(false);
        editOrderButton.setManaged(false);

        deleteOrderButton.setVisible(false);
        deleteOrderButton.setManaged(false);

        pickUpOrderButton.setVisible(false);
        pickUpOrderButton.setManaged(false);
    }

    private void setupTable(TableColumn<?, ?>... columns) {
        resetTable();

        mainTableView.getColumns().addAll(columns);
        mainTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    public void showFlowers() {
        setupTable(
              TableUtils.imageColumn("Фото", "imagePath"),
              TableUtils.column("ID", "flowerId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Колір", "color"),
              TableUtils.column("Ціна", "price"),
              TableUtils.column("Кількість", "stockQuantity")
        );

        mainTableView.setFixedCellSize(70);
        mainTableView.setItems(FXCollections.observableArrayList(flowerService.getAll()));
    }

    @FXML
    public void showBouquets() {
        setupTable(
              TableUtils.imageColumn("Фото", "imagePath"),
              TableUtils.column("ID", "bouquetId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Опис", "description"),
              TableUtils.column("Custom", "custom"),
              TableUtils.column("Дата", "createdAt")
        );

        mainTableView.setFixedCellSize(70);
        mainTableView.setItems(FXCollections.observableArrayList(bouquetService.getAll()));
    }

    @FXML
    public void showAccessories() {
        setupTable(
              TableUtils.imageColumn("Фото", "imagePath"),
              TableUtils.column("ID", "accessoryId"),
              TableUtils.column("Назва", "name"),
              TableUtils.column("Тип", "accessoryType"),
              TableUtils.column("Колір", "color"),
              TableUtils.column("Ціна", "price"),
              TableUtils.column("Кількість", "stockQuantity")
        );

        mainTableView.setFixedCellSize(70);
        mainTableView.setItems(FXCollections.observableArrayList(accessoryService.getAll()));
    }

    @FXML
    public void showUsers() {
        setupTable(
              TableUtils.column("Ім'я", "firstName"),
              TableUtils.column("Прізвище", "lastName"),
              TableUtils.column("Email", "email"),
              TableUtils.column("Телефон", "phone"),
              TableUtils.column("Роль", "role"),
              TableUtils.column("Дата", "createdAt")
        );

        mainTableView.setFixedCellSize(45);
        mainTableView.setItems(FXCollections.observableArrayList(userService.getAll()));
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
              statusColumn(),
              TableUtils.column("Дата", "createdAt")
        );

        showAllowedOrderButtons();

        mainTableView.setFixedCellSize(45);

        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser != null && currentUser.getRole() == Role.CLIENT) {
            mainTableView.setItems(FXCollections.observableArrayList(
                  orderService.getByUserId(currentUser.getUserId())
            ));
        } else {
            mainTableView.setItems(FXCollections.observableArrayList(
                  orderService.getAll()
            ));
        }

        updatePickUpButtonVisibility();
    }

    private void showAllowedOrderButtons() {
        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser == null) {
            return;
        }

        if (currentUser.getRole() == Role.CLIENT) {
            createOrderButton.setVisible(true);
            createOrderButton.setManaged(true);

            editOrderButton.setVisible(true);
            editOrderButton.setManaged(true);

            deleteOrderButton.setVisible(true);
            deleteOrderButton.setManaged(true);

            return;
        }

        if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FLORIST) {
            editOrderButton.setVisible(true);
            editOrderButton.setManaged(true);

            deleteOrderButton.setVisible(true);
            deleteOrderButton.setManaged(true);
        }
    }

    private TableColumn<Order, OrderStatus> statusColumn() {
        TableColumn<Order, OrderStatus> column = new TableColumn<>("Статус");

        column.setCellValueFactory(cellData -> new SimpleObjectProperty<>(
              cellData.getValue().getStatus()
        ));

        column.setCellFactory(value -> new TableCell<>() {

            private final ComboBox<OrderStatus> comboBox = new ComboBox<>(
                  FXCollections.observableArrayList(OrderStatus.values())
            );

            @Override
            protected void updateItem(OrderStatus status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                Order order = getTableRow().getItem();

                comboBox.setValue(order.getStatus());
                comboBox.setDisable(!canCurrentUserChangeStatus());

                comboBox.setOnAction(event -> {
                    OrderStatus selectedStatus = comboBox.getValue();

                    if (selectedStatus == null) {
                        return;
                    }

                    order.setStatus(selectedStatus);
                    orderService.update(order);

                    updatePickUpButtonVisibility();
                    mainTableView.refresh();
                });

                setGraphic(comboBox);
            }
        });

        return column;
    }

    private boolean canCurrentUserChangeStatus() {
        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (currentUser.getRole() == Role.ADMIN) {
            return true;
        }

        return currentUser.getRole() == Role.FLORIST;
    }

    private void updatePickUpButtonVisibility() {
        pickUpOrderButton.setVisible(false);
        pickUpOrderButton.setManaged(false);

        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser == null) {
            return;
        }

        if (currentUser.getRole() != Role.CLIENT) {
            return;
        }

        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order order)) {
            return;
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            return;
        }

        pickUpOrderButton.setVisible(true);
        pickUpOrderButton.setManaged(true);
    }

    @FXML
    private void handlePickUpOrder() {
        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order order)) {
            return;
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            return;
        }

        order.setStatus(OrderStatus.PICKED_UP);
        orderService.update(order);

        showOrders();
    }

    @FXML
    private void openCreateOrderWindow() {
        MainApplication.sceneManager.switchSceneMaximized("/view/create-order-view.fxml", "Create Order");
    }

    @FXML
    private void handleEditOrder() {
        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order selectedOrder)) {
            return;
        }

        if (!canEditOrder(selectedOrder)) {
            return;
        }

        CreateOrderController controller = MainApplication.sceneManager.switchSceneGetControllerMaximized(
              "/view/create-order-view.fxml",
              "Edit Order"
        );

        controller.loadOrder(selectedOrder);
    }

    private boolean canEditOrder(Order order) {
        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser == null || order == null) {
            return false;
        }

        if (currentUser.getRole() == Role.CLIENT) {
            if (order.getStatus() == OrderStatus.NEW) {
                return true;
            }

            return order.getStatus() == OrderStatus.ACCEPTED;
        }

        if (currentUser.getRole() == Role.FLORIST || currentUser.getRole() == Role.ADMIN) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                return false;
            }

            return order.getStatus() != OrderStatus.PICKED_UP;
        }

        return false;
    }

    private boolean canDeleteOrder(Order order) {
        User currentUser = MainApplication.authService.getCurrentUser();

        if (currentUser == null || order == null) {
            return false;
        }

        if (currentUser.getRole() == Role.CLIENT) {
            if (order.getStatus() == OrderStatus.NEW) {
                return true;
            }

            return order.getStatus() == OrderStatus.ACCEPTED;
        }

        if (currentUser.getRole() == Role.FLORIST || currentUser.getRole() == Role.ADMIN) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                return false;
            }

            return order.getStatus() != OrderStatus.PICKED_UP;
        }

        return false;
    }

    @FXML
    private void handleDeleteOrder() {
        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order selectedOrder)) {
            return;
        }

        if (!canDeleteOrder(selectedOrder)) {
            return;
        }

        orderService.delete(selectedOrder.getOrderId());

        showOrders();
    }

    @FXML
    private void handleLogout() {
        new LoginViewModel(MainApplication.authService);
        MainApplication.sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
    }
}