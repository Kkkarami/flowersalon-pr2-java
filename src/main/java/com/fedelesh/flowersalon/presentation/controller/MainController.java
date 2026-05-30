package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.application.contract.UserService;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.presentation.MainApplication;
import com.fedelesh.flowersalon.presentation.util.TableUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.fedelesh.flowersalon.infrastructure.payment.LiqPayService;
import java.awt.Desktop;
import java.net.URI;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainController {

  private final AuthService authService;
  private final UserService userService;
  private final FlowerService flowerService;
  private final BouquetService bouquetService;
  private final AccessoryService accessoryService;
  private final OrderService orderService;
  private final LiqPayService liqPayService = new LiqPayService();

  @FXML private Button flowersTabButton;

  @FXML private Button bouquetsTabButton;

  @FXML private Button accessoriesTabButton;

  @FXML private Button usersTabButton;

  @FXML private Button ordersTabButton;

  @FXML private TableView mainTableView;

  @FXML private Button createOrderButton;

  @FXML private Button editOrderButton;

  @FXML private Button deleteOrderButton;

  @FXML private Button pickUpOrderButton;

  @Inject
  public MainController(
      AuthService authService,
      UserService userService,
      FlowerService flowerService,
      BouquetService bouquetService,
      AccessoryService accessoryService,
      OrderService orderService) {

    this.authService = authService;
    this.userService = userService;
    this.flowerService = flowerService;
    this.bouquetService = bouquetService;
    this.accessoryService = accessoryService;
    this.orderService = orderService;
  }

  @FXML
  public void initialize() {
    hideOrderButtons();
    configureTabVisibility();

    createOrderButton.setOnAction(event -> openCreateOrderWindow());

    mainTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldValue, newValue) -> {
              updatePickUpButtonVisibility();
            });

    mainTableView.setOnMouseClicked(
        event -> {
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

  private void setActiveTab(Button activeButton) {
    clearActiveTab(flowersTabButton);
    clearActiveTab(bouquetsTabButton);
    clearActiveTab(accessoriesTabButton);
    clearActiveTab(usersTabButton);
    clearActiveTab(ordersTabButton);

    if (activeButton != null) {
      activeButton.getStyleClass().remove("secondary-button");

      if (!activeButton.getStyleClass().contains("active-tab-button")) {
        activeButton.getStyleClass().add("active-tab-button");
      }
    }
  }

  private void clearActiveTab(Button button) {
    if (button == null) {
      return;
    }

    button.getStyleClass().remove("active-tab-button");

    if (!button.getStyleClass().contains("secondary-button")) {
      button.getStyleClass().add("secondary-button");
    }
  }

  @FXML
  public void showFlowers() {
    setActiveTab(flowersTabButton);
    setupTable(
        TableUtils.imageColumn("Фото", "imagePath"),
        TableUtils.column("Назва", "name"),
        TableUtils.column("Колір", "color"),
        TableUtils.column("Ціна", "price"),
        TableUtils.column("Кількість", "stockQuantity"));

    mainTableView.setFixedCellSize(70);
    mainTableView.setItems(FXCollections.observableArrayList(flowerService.getAll()));
  }

  @FXML
  public void showBouquets() {
    setActiveTab(bouquetsTabButton);
    setupTable(
        TableUtils.imageColumn("Фото", "imagePath"),
        TableUtils.column("Назва", "name"),
        TableUtils.column("Опис", "description"),
        TableUtils.column("Ціна", "price"),
        TableUtils.column("Custom", "custom"),
        TableUtils.column("Дата", "createdAt"));

    mainTableView.setFixedCellSize(70);
    mainTableView.setItems(FXCollections.observableArrayList(bouquetService.getAll()));
  }

  @FXML
  public void showAccessories() {
    setActiveTab(accessoriesTabButton);
    setupTable(
        TableUtils.imageColumn("Фото", "imagePath"),
        TableUtils.column("Назва", "name"),
        TableUtils.column("Тип", "accessoryType"),
        TableUtils.column("Колір", "color"),
        TableUtils.column("Ціна", "price"),
        TableUtils.column("Кількість", "stockQuantity"));

    mainTableView.setFixedCellSize(70);
    mainTableView.setItems(FXCollections.observableArrayList(accessoryService.getAll()));
  }

  @FXML
  public void showUsers() {
    setActiveTab(usersTabButton);
    setupTable(
        TableUtils.column("Ім'я", "firstName"),
        TableUtils.column("Прізвище", "lastName"),
        TableUtils.column("Email", "email"),
        TableUtils.column("Телефон", "phone"),
        roleColumn(),
        TableUtils.column("Дата", "createdAt"));

    mainTableView.setFixedCellSize(45);
    mainTableView.setItems(FXCollections.observableArrayList(userService.getAll()));
  }

  private TableColumn<User, Role> roleColumn() {
    TableColumn<User, Role> column = new TableColumn<>("Роль");

    column.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getRole()));

    column.setCellFactory(
        value ->
            new TableCell<>() {

              private final ComboBox<Role> comboBox =
                  new ComboBox<>(FXCollections.observableArrayList(Role.CLIENT, Role.FLORIST));

              @Override
              protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                  setGraphic(null);
                  return;
                }

                User targetUser = getTableRow().getItem();
                User currentUser = authService.getCurrentUser();

                comboBox.setValue(targetUser.getRole());

                boolean disabled = true;

                if (currentUser != null && currentUser.getRole() == Role.ADMIN) {
                  if (targetUser.getRole() != Role.ADMIN) {
                    disabled = false;
                  }
                }

                comboBox.setDisable(disabled);

                comboBox.setOnAction(
                    event -> {
                      Role selectedRole = comboBox.getValue();

                      if (selectedRole == null) {
                        return;
                      }

                      userService.changeRole(
                          targetUser, selectedRole, authService.getCurrentUser());

                      mainTableView.refresh();
                    });

                setGraphic(comboBox);
              }
            });

    return column;
  }

  @FXML
  public void showOrders() {
    setActiveTab(ordersTabButton);
    setupTable(
        TableUtils.column("ID", "orderId"),
        TableUtils.column("Ім'я", "customerFirstName"),
        TableUtils.column("Прізвище", "customerLastName"),
        TableUtils.column("Телефон", "phone"),
        TableUtils.column("Бюджет", "budget"),
        TableUtils.column("Стиль", "style"),
        TableUtils.column("Колір", "preferredColor"),
        statusColumn(),
        TableUtils.column("Дата", "createdAt"));

    showAllowedOrderButtons();

    mainTableView.setFixedCellSize(45);

    User currentUser = authService.getCurrentUser();

    if (currentUser != null && currentUser.getRole() == Role.CLIENT) {
      mainTableView.setItems(
          FXCollections.observableArrayList(orderService.getByUserId(currentUser.getUserId())));
    } else {
      mainTableView.setItems(FXCollections.observableArrayList(orderService.getAll()));
    }

    updatePickUpButtonVisibility();
  }

  private void showAllowedOrderButtons() {
    User currentUser = authService.getCurrentUser();

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

    column.setCellValueFactory(
        cellData -> new SimpleObjectProperty<>(cellData.getValue().getStatus()));

    column.setCellFactory(
        value ->
            new TableCell<>() {

              private final ComboBox<OrderStatus> comboBox =
                  new ComboBox<>(FXCollections.observableArrayList(OrderStatus.values()));

              @Override
              protected void updateItem(OrderStatus status, boolean empty) {
                super.updateItem(status, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                  setGraphic(null);
                  return;
                }

                Order order = getTableRow().getItem();

                comboBox.setValue(order.getStatus());
                comboBox.setDisable(!canCurrentUserChangeStatus(order));

                comboBox.setOnAction(
                    event -> {
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

    private boolean canCurrentUserChangeStatus(Order order) {
        User currentUser = authService.getCurrentUser();

        if (currentUser == null) {
            return false;
        }

        if (order == null) {
            return false;
        }

        if (order.getStatus() == OrderStatus.PICKED_UP) {
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

    User currentUser = authService.getCurrentUser();

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
            showWarning("Замовлення не вибрано");
            return;
        }

        if (order.getStatus() != OrderStatus.COMPLETED) {
            showWarning("Оплатити можна тільки виконане замовлення");
            return;
        }

        try {
            String url = liqPayService.generatePaymentUrl(
                  order.getBudget(),
                  "Оплата замовлення квітів",
                  order.getOrderId().toString()
            );

            Desktop.getDesktop().browse(new URI(url));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Оплата LiqPay");
            alert.setHeaderText(null);
            alert.setContentText("Після оплати натисніть OK, щоб підтвердити отримання замовлення.");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                order.setStatus(OrderStatus.PICKED_UP);
                orderService.update(order);
                showOrders();
            }

        } catch (Exception e) {
            showWarning("Не вдалося відкрити оплату: " + e.getMessage());
        }
    }

  @FXML
  private void openCreateOrderWindow() {
    MainApplication.sceneManager.switchSceneMaximized(
        "/view/create-order-view.fxml", "Create Order");
  }

    @FXML
    private void handleEditOrder() {
        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order selectedOrder)) {
            showWarning("Замовлення не вибрано");
            return;
        }

        if (!canEditOrder(selectedOrder)) {
            showWarning("Це замовлення не можна оновити");
            return;
        }

        CreateOrderController controller = MainApplication.sceneManager.switchSceneGetControllerMaximized(
              "/view/create-order-view.fxml",
              "Редагування замовлення"
        );

        controller.loadOrder(selectedOrder);
    }

  private boolean canEditOrder(Order order) {
    User currentUser = authService.getCurrentUser();

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
    User currentUser = authService.getCurrentUser();

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

    private void configureTabVisibility() {
        User currentUser = authService.getCurrentUser();

        boolean usersVisible = false;

        if (currentUser != null) {
            if (currentUser.getRole() == Role.ADMIN || currentUser.getRole() == Role.FLORIST) {
                usersVisible = true;
            }
        }

        usersTabButton.setVisible(usersVisible);
        usersTabButton.setManaged(usersVisible);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Попередження");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleDeleteOrder() {
        Object selected = mainTableView.getSelectionModel().getSelectedItem();

        if (!(selected instanceof Order selectedOrder)) {
            showWarning("Замовлення не вибрано");
            return;
        }

        if (!canDeleteOrder(selectedOrder)) {
            showWarning("Це замовлення не можна видалити");
            return;
        }

        orderService.delete(selectedOrder.getOrderId());

        showOrders();
    }

  @FXML
  private void handleLogout() {
    authService.logout();

    MainApplication.sceneManager.switchSceneMaximized("/view/login-view.fxml", "Login");
  }
}
