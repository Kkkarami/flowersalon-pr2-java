package com.fedelesh.flowersalon.presentation.controller;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.presentation.viewmodel.CreateOrderViewModel;
import com.fedelesh.flowersalon.presentation.viewmodel.OrderLineViewModel;
import java.math.BigDecimal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CreateOrderController {

    private final CreateOrderViewModel viewModel =
          new CreateOrderViewModel(
                LoginController.authService
          );
    private Order editingOrder;


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField styleField;

    @FXML
    private TextField colorField;

    @FXML
    private TableView<Flower> flowersTableView;

    @FXML
    private TableView<Bouquet> bouquetsTableView;

    @FXML
    private TableView<Accessory> accessoriesTableView;

    @FXML
    private TableView<OrderLineViewModel> orderItemsTableView;

    @FXML
    private TableColumn<Flower, String> flowerNameColumn;

    @FXML
    private TableColumn<Flower, String> flowerColorColumn;

    @FXML
    private TableColumn<Flower, BigDecimal> flowerPriceColumn;

    @FXML
    private TableColumn<Bouquet, String> bouquetNameColumn;

    @FXML
    private TableColumn<Bouquet, String> bouquetDescriptionColumn;

    @FXML
    private TableColumn<Accessory, String> accessoryNameColumn;

    @FXML
    private TableColumn<Accessory, String> accessoryTypeColumn;

    @FXML
    private TableColumn<Accessory, BigDecimal> accessoryPriceColumn;

    @FXML
    private TableColumn<OrderLineViewModel, String> orderTypeColumn;

    @FXML
    private TableColumn<OrderLineViewModel, String> orderNameColumn;

    @FXML
    private TableColumn<OrderLineViewModel, Integer> orderQuantityColumn;

    @FXML
    private TableColumn<OrderLineViewModel, BigDecimal> orderPriceColumn;

    @FXML
    private TableColumn<OrderLineViewModel, BigDecimal> orderTotalColumn;

    @FXML
    private Label totalLabel;

    @FXML
    public void initialize() {

        flowerNameColumn.setCellValueFactory(
              new PropertyValueFactory<>("name")
        );

        flowerColorColumn.setCellValueFactory(
              new PropertyValueFactory<>("color")
        );

        flowerPriceColumn.setCellValueFactory(
              new PropertyValueFactory<>("price")
        );

        bouquetNameColumn.setCellValueFactory(
              new PropertyValueFactory<>("name")
        );

        bouquetDescriptionColumn.setCellValueFactory(
              new PropertyValueFactory<>("description")
        );

        accessoryNameColumn.setCellValueFactory(
              new PropertyValueFactory<>("name")
        );

        accessoryTypeColumn.setCellValueFactory(
              new PropertyValueFactory<>("accessoryType")
        );

        accessoryPriceColumn.setCellValueFactory(
              new PropertyValueFactory<>("price")
        );

        orderTypeColumn.setCellValueFactory(
              new PropertyValueFactory<>("itemType")
        );

        orderNameColumn.setCellValueFactory(
              new PropertyValueFactory<>("name")
        );

        orderQuantityColumn.setCellValueFactory(
              new PropertyValueFactory<>("quantity")
        );

        orderPriceColumn.setCellValueFactory(
              new PropertyValueFactory<>("price")
        );

        orderTotalColumn.setCellValueFactory(
              new PropertyValueFactory<>("total")
        );

        flowersTableView.setItems(
              viewModel.getFlowers()
        );

        bouquetsTableView.setItems(
              viewModel.getBouquets()
        );

        accessoriesTableView.setItems(
              viewModel.getAccessories()
        );

        orderItemsTableView.setItems(
              viewModel.getOrderItems()
        );

        totalLabel.textProperty().bind(
              viewModel.totalTextProperty()
        );

        firstNameField.setText(
              viewModel.getCurrentUserFirstName()
        );

        lastNameField.setText(
              viewModel.getCurrentUserLastName()
        );

        phoneField.setText(
              viewModel.getCurrentUserPhone()
        );
    }

    public void loadOrder(Order order) {

        editingOrder = order;

        firstNameField.setText(
              order.getCustomerFirstName()
        );

        lastNameField.setText(
              order.getCustomerLastName()
        );

        phoneField.setText(
              order.getPhone()
        );

        styleField.setText(
              order.getStyle()
        );

        colorField.setText(
              order.getPreferredColor()
        );
    }

    @FXML
    private void handleAddFlower() {

        viewModel.addFlower(
              flowersTableView
                    .getSelectionModel()
                    .getSelectedItem()
        );
    }

    @FXML
    private void handleAddBouquet() {

        viewModel.addBouquet(
              bouquetsTableView
                    .getSelectionModel()
                    .getSelectedItem()
        );
    }

    @FXML
    private void handleAddAccessory() {

        viewModel.addAccessory(
              accessoriesTableView
                    .getSelectionModel()
                    .getSelectedItem()
        );
    }

    @FXML
    private void handleRemoveItem() {

        viewModel.removeItem(
              orderItemsTableView
                    .getSelectionModel()
                    .getSelectedItem()
        );
    }

    @FXML
    private void handleBack() {

        try {

            FXMLLoader loader = new FXMLLoader(
                  getClass().getResource(
                        "/view/main-view.fxml"
                  )
            );

            Parent root = loader.load();

            Stage currentStage =
                  (Stage) orderItemsTableView
                        .getScene()
                        .getWindow();

            currentStage
                  .getScene()
                  .setRoot(root);

            currentStage.setTitle("Main");

            currentStage.setMaximized(true);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @FXML
    private void handleCreateOrder() {
        if (editingOrder == null) {

            viewModel.createOrder(

                  firstNameField.getText(),

                  lastNameField.getText(),

                  phoneField.getText(),

                  styleField.getText(),

                  colorField.getText()
            );

        } else {

            editingOrder.setCustomerFirstName(
                  firstNameField.getText()
            );

            editingOrder.setCustomerLastName(
                  lastNameField.getText()
            );

            editingOrder.setPhone(
                  phoneField.getText()
            );

            editingOrder.setStyle(
                  styleField.getText()
            );

            editingOrder.setPreferredColor(
                  colorField.getText()
            );

            editingOrder.setBudget(
                  new BigDecimal(
                        totalLabel.getText()
                  )
            );

            viewModel.updateOrder(editingOrder);
        }

        handleBack();
    }
}
