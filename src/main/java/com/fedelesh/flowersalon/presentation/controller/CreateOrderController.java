package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.presentation.MainApplication;
import com.fedelesh.flowersalon.presentation.viewmodel.CreateOrderViewModel;
import com.fedelesh.flowersalon.presentation.viewmodel.OrderLineViewModel;
import com.google.inject.Inject;
import java.io.InputStream;
import java.math.BigDecimal;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class CreateOrderController {

  private static final double WORKSPACE_IMAGE_SIZE = 155;

  private final CreateOrderViewModel viewModel;

  private Order editingOrder;

  @FXML private TextField firstNameField;

  @FXML private TextField lastNameField;

  @FXML private TextField phoneField;

  @FXML private TextField styleField;

  @FXML private TextField colorField;

  @FXML private TableView<Flower> flowersTableView;

  @FXML private TableView<Bouquet> bouquetsTableView;

  @FXML private TableView<Accessory> accessoriesTableView;

  @FXML private TableView<OrderLineViewModel> orderItemsTableView;

  @FXML private Pane bouquetWorkspace;

  @FXML private TableColumn<Flower, String> flowerImageColumn;

  @FXML private TableColumn<Flower, String> flowerNameColumn;

  @FXML private TableColumn<Flower, String> flowerColorColumn;

  @FXML private TableColumn<Flower, BigDecimal> flowerPriceColumn;

  @FXML private TableColumn<Bouquet, String> bouquetImageColumn;

  @FXML private TableColumn<Bouquet, String> bouquetNameColumn;

  @FXML private TableColumn<Bouquet, String> bouquetDescriptionColumn;

  @FXML private TableColumn<Bouquet, BigDecimal> bouquetPriceColumn;

  @FXML private TableColumn<Accessory, String> accessoryImageColumn;

  @FXML private TableColumn<Accessory, String> accessoryNameColumn;

  @FXML private TableColumn<Accessory, String> accessoryTypeColumn;

  @FXML private TableColumn<Accessory, BigDecimal> accessoryPriceColumn;

  @FXML private TableColumn<OrderLineViewModel, String> orderTypeColumn;

  @FXML private TableColumn<OrderLineViewModel, String> orderNameColumn;

  @FXML private TableColumn<OrderLineViewModel, Integer> orderQuantityColumn;

  @FXML private TableColumn<OrderLineViewModel, BigDecimal> orderPriceColumn;

  @FXML private TableColumn<OrderLineViewModel, BigDecimal> orderTotalColumn;

  @FXML private Label totalLabel;

  @FXML private Button createOrderButton;

  @Inject
  public CreateOrderController(CreateOrderViewModel viewModel) {
    this.viewModel = viewModel;
  }

  @FXML
  public void initialize() {
    flowerImageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
    flowerImageColumn.setCellFactory(column -> imageCell());

    flowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    flowerColorColumn.setCellValueFactory(new PropertyValueFactory<>("color"));
    flowerPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    bouquetImageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
    bouquetImageColumn.setCellFactory(column -> imageCell());

    bouquetNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    bouquetDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    bouquetPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    accessoryImageColumn.setCellValueFactory(new PropertyValueFactory<>("imagePath"));
    accessoryImageColumn.setCellFactory(column -> imageCell());

    accessoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    accessoryTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accessoryType"));
    accessoryPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    orderTypeColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));
    orderNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

    flowersTableView.setItems(viewModel.getFlowers());
    bouquetsTableView.setItems(viewModel.getBouquets());
    accessoriesTableView.setItems(viewModel.getAccessories());
    orderItemsTableView.setItems(viewModel.getOrderItems());

    flowersTableView.setFixedCellSize(70);
    bouquetsTableView.setFixedCellSize(70);
    accessoriesTableView.setFixedCellSize(70);
    orderItemsTableView.setFixedCellSize(45);

    totalLabel.textProperty().bind(viewModel.totalTextProperty());

    firstNameField.setText(viewModel.getCurrentUserFirstName());
    lastNameField.setText(viewModel.getCurrentUserLastName());
    phoneField.setText(viewModel.getCurrentUserPhone());
  }

  private <T> TableCell<T, String> imageCell() {
    return new TableCell<>() {

      private final ImageView imageView = new ImageView();

      @Override
      protected void updateItem(String path, boolean empty) {
        super.updateItem(path, empty);

        if (empty || path == null || path.isBlank()) {
          setGraphic(null);
          return;
        }

        InputStream inputStream = CreateOrderController.class.getResourceAsStream(path);

        if (inputStream == null) {
          setGraphic(null);
          return;
        }

        Image image = new Image(inputStream, 55, 55, true, true);

        imageView.setImage(image);
        imageView.setFitWidth(55);
        imageView.setFitHeight(55);
        imageView.setPreserveRatio(true);

        setGraphic(imageView);
      }
    };
  }

  private void addImageToWorkspace(OrderLineViewModel item) {
    if (item == null) {
      return;
    }

    String path = item.getImagePath();

    if (path == null || path.isBlank()) {
      return;
    }

    InputStream inputStream = CreateOrderController.class.getResourceAsStream(path);

    if (inputStream == null) {
      return;
    }

    Image image = new Image(inputStream);
    ImageView imageView = new ImageView(image);

    imageView.setFitWidth(WORKSPACE_IMAGE_SIZE);
    imageView.setFitHeight(WORKSPACE_IMAGE_SIZE);
    imageView.setPreserveRatio(true);
    imageView.setPickOnBounds(true);

    double x = item.getWorkspaceX();
    double y = item.getWorkspaceY();

    if (x == 0 && y == 0) {
      double[] position = calculateAutoPosition(bouquetWorkspace.getChildren().size());
      x = position[0];
      y = position[1];

      item.setWorkspaceX(x);
      item.setWorkspaceY(y);
    }

    imageView.setLayoutX(x);
    imageView.setLayoutY(y);

    makeDraggable(imageView, item);

    bouquetWorkspace.getChildren().add(imageView);
    imageView.toFront();
  }

  private double[] calculateAutoPosition(int index) {
    double workspaceWidth = bouquetWorkspace.getWidth();
    double workspaceHeight = bouquetWorkspace.getHeight();

    if (workspaceWidth <= 0) {
      workspaceWidth = bouquetWorkspace.getPrefWidth();
    }

    if (workspaceHeight <= 0) {
      workspaceHeight = bouquetWorkspace.getPrefHeight();
    }

    if (workspaceWidth <= 0) {
      workspaceWidth = 350;
    }

    if (workspaceHeight <= 0) {
      workspaceHeight = 600;
    }

    double centerX = workspaceWidth / 2 - WORKSPACE_IMAGE_SIZE / 2;
    double centerY = workspaceHeight / 2 - WORKSPACE_IMAGE_SIZE / 2;

    double[][] positions = {
      {0, 0},
      {-28, -22},
      {28, -22},
      {-35, 18},
      {35, 18},
      {0, -45},
      {0, 42},
      {-55, -5},
      {55, -5},
      {-50, -45},
      {50, -45},
      {-50, 45},
      {50, 45},
      {-75, 20},
      {75, 20},
      {-75, -25},
      {75, -25}
    };

    int positionIndex = index % positions.length;
    int layer = index / positions.length;

    double extraX = layer * 12;
    double extraY = layer * 18;

    double x = centerX + positions[positionIndex][0] + extraX;
    double y = centerY + positions[positionIndex][1] + extraY;

    return new double[] {x, y};
  }

  private void makeDraggable(ImageView imageView, OrderLineViewModel item) {
    final double[] offsetX = new double[1];
    final double[] offsetY = new double[1];

    imageView.setOnMousePressed(
        event -> {
          offsetX[0] = event.getX();
          offsetY[0] = event.getY();
          imageView.toFront();
        });

    imageView.setOnMouseDragged(
        event -> {
          double x = imageView.getLayoutX() + event.getX() - offsetX[0];
          double y = imageView.getLayoutY() + event.getY() - offsetY[0];

          double maxX = bouquetWorkspace.getWidth() - imageView.getFitWidth();
          double maxY = bouquetWorkspace.getHeight() - imageView.getFitHeight();

          if (maxX < 0) {
            maxX = 0;
          }

          if (maxY < 0) {
            maxY = 0;
          }

          if (x < 0) {
            x = 0;
          }

          if (y < 0) {
            y = 0;
          }

          if (x > maxX) {
            x = maxX;
          }

          if (y > maxY) {
            y = maxY;
          }

          imageView.setLayoutX(x);
          imageView.setLayoutY(y);

          item.setWorkspaceX(x);
          item.setWorkspaceY(y);
        });
  }

  public void loadOrder(Order order) {
    editingOrder = order;

    firstNameField.setText(order.getCustomerFirstName());
    lastNameField.setText(order.getCustomerLastName());
    phoneField.setText(order.getPhone());
    styleField.setText(order.getStyle());
    colorField.setText(order.getPreferredColor());

    createOrderButton.setText("Update Order");

    viewModel.loadOrderItems(order);

    bouquetWorkspace.getChildren().clear();

    for (OrderLineViewModel item : viewModel.getOrderItems()) {
      addImageToWorkspace(item);
    }
  }

  @FXML
  private void handleAddFlower() {
    Flower flower = flowersTableView.getSelectionModel().getSelectedItem();

    if (flower == null) {
      return;
    }

    OrderLineViewModel item = viewModel.addFlower(flower);

    addImageToWorkspace(item);
  }

  @FXML
  private void handleAddBouquet() {
    Bouquet bouquet = bouquetsTableView.getSelectionModel().getSelectedItem();

    if (bouquet == null) {
      return;
    }

    OrderLineViewModel item = viewModel.addBouquet(bouquet);

    addImageToWorkspace(item);
  }

  @FXML
  private void handleAddAccessory() {
    Accessory accessory = accessoriesTableView.getSelectionModel().getSelectedItem();

    if (accessory == null) {
      return;
    }

    OrderLineViewModel item = viewModel.addAccessory(accessory);

    addImageToWorkspace(item);
  }

  @FXML
  private void handleRemoveItem() {
    OrderLineViewModel selectedItem = orderItemsTableView.getSelectionModel().getSelectedItem();

    if (selectedItem == null) {
      return;
    }

    int selectedIndex = orderItemsTableView.getSelectionModel().getSelectedIndex();

    viewModel.removeItem(selectedItem);

    if (selectedIndex >= 0 && selectedIndex < bouquetWorkspace.getChildren().size()) {
      bouquetWorkspace.getChildren().remove(selectedIndex);
    }
  }

  @FXML
  private void handleBack() {
    MainApplication.sceneManager.switchSceneMaximized("/view/main-view.fxml", "Main");
  }

  @FXML
  private void handleCreateOrder() {
    if (editingOrder == null) {
      viewModel.createOrder(
          firstNameField.getText(),
          lastNameField.getText(),
          phoneField.getText(),
          styleField.getText(),
          colorField.getText());
    } else {
      editingOrder.setCustomerFirstName(firstNameField.getText());
      editingOrder.setCustomerLastName(lastNameField.getText());
      editingOrder.setPhone(phoneField.getText());
      editingOrder.setStyle(styleField.getText());
      editingOrder.setPreferredColor(colorField.getText());
      editingOrder.setBudget(new BigDecimal(totalLabel.getText()));

      viewModel.updateOrder(editingOrder);
    }

    handleBack();
  }
}
