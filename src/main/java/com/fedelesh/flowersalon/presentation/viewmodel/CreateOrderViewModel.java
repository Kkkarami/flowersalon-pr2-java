package com.fedelesh.flowersalon.presentation.viewmodel;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import com.google.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateOrderViewModel {

  private final FlowerService flowerService;
  private final BouquetService bouquetService;
  private final AccessoryService accessoryService;
  private final OrderService orderService;
  private final AuthService authService;

  private final ObservableList<Flower> flowers = FXCollections.observableArrayList();
  private final ObservableList<Bouquet> bouquets = FXCollections.observableArrayList();
  private final ObservableList<Accessory> accessories = FXCollections.observableArrayList();
  private final ObservableList<OrderLineViewModel> orderItems = FXCollections.observableArrayList();

  private final StringProperty totalText = new SimpleStringProperty("0");

  @Inject
  public CreateOrderViewModel(
      AuthService authService,
      FlowerService flowerService,
      BouquetService bouquetService,
      AccessoryService accessoryService,
      OrderService orderService) {

    this.authService = authService;
    this.flowerService = flowerService;
    this.bouquetService = bouquetService;
    this.accessoryService = accessoryService;
    this.orderService = orderService;

    loadData();
  }

  private void loadData() {
    flowers.setAll(flowerService.getAll());
    bouquets.setAll(bouquetService.getAll());
    accessories.setAll(accessoryService.getAll());
  }

  public ObservableList<Flower> getFlowers() {
    return flowers;
  }

  public ObservableList<Bouquet> getBouquets() {
    return bouquets;
  }

  public ObservableList<Accessory> getAccessories() {
    return accessories;
  }

  public ObservableList<OrderLineViewModel> getOrderItems() {
    return orderItems;
  }

  public StringProperty totalTextProperty() {
    return totalText;
  }

  public String getCurrentUserFirstName() {
    if (authService == null) {
      return "";
    }

    if (authService.getCurrentUser() == null) {
      return "";
    }

    return authService.getCurrentUser().getFirstName();
  }

  public String getCurrentUserLastName() {
    if (authService == null) {
      return "";
    }

    if (authService.getCurrentUser() == null) {
      return "";
    }

    return authService.getCurrentUser().getLastName();
  }

  public String getCurrentUserPhone() {
    if (authService == null) {
      return "";
    }

    if (authService.getCurrentUser() == null) {
      return "";
    }

    return authService.getCurrentUser().getPhone();
  }

  public OrderLineViewModel addFlower(Flower flower) {
    if (flower == null) {
      return null;
    }

    OrderLineViewModel item =
        new OrderLineViewModel(
            "Flower", flower.getName(), 1, flower.getPrice(), flower.getImagePath());

    item.setFlowerId(flower.getFlowerId());

    orderItems.add(item);
    updateTotal();

    return item;
  }

  public OrderLineViewModel addBouquet(Bouquet bouquet) {
    if (bouquet == null) {
      return null;
    }

    OrderLineViewModel item =
        new OrderLineViewModel(
            "Bouquet", bouquet.getName(), 1, bouquet.getPrice(), bouquet.getImagePath());

    item.setBouquetId(bouquet.getBouquetId());

    orderItems.add(item);
    updateTotal();

    return item;
  }

  public OrderLineViewModel addAccessory(Accessory accessory) {
    if (accessory == null) {
      return null;
    }

    OrderLineViewModel item =
        new OrderLineViewModel(
            "Accessory", accessory.getName(), 1, accessory.getPrice(), accessory.getImagePath());

    item.setAccessoryId(accessory.getAccessoryId());

    orderItems.add(item);
    updateTotal();

    return item;
  }

  public void removeItem(OrderLineViewModel item) {
    if (item == null) {
      return;
    }

    orderItems.remove(item);
    updateTotal();
  }

  private void updateTotal() {
    BigDecimal total = BigDecimal.ZERO;

    for (OrderLineViewModel item : orderItems) {
      total = total.add(item.getTotal());
    }

    totalText.set(total.toString());
  }

  public void loadOrderItems(Order order) {
    orderItems.clear();

    if (order == null) {
      updateTotal();
      return;
    }

    List<OrderItem> items = orderService.getItemsByOrderId(order.getOrderId());

    for (OrderItem item : items) {
      String name = "";
      String imagePath = "";

      if (item.getFlowerId() != null) {
        Flower flower = flowerService.getById(item.getFlowerId());
        name = flower.getName();
        imagePath = flower.getImagePath();
      }

      if (item.getBouquetId() != null) {
        Bouquet bouquet = bouquetService.getById(item.getBouquetId());
        name = bouquet.getName();
        imagePath = bouquet.getImagePath();
      }

      if (item.getAccessoryId() != null) {
        Accessory accessory = accessoryService.getById(item.getAccessoryId());
        name = accessory.getName();
        imagePath = accessory.getImagePath();
      }

      OrderLineViewModel vm =
          new OrderLineViewModel(
              item.getItemType(), name, item.getQuantity(), item.getPriceSnapshot(), imagePath);

      vm.setFlowerId(item.getFlowerId());
      vm.setBouquetId(item.getBouquetId());
      vm.setAccessoryId(item.getAccessoryId());
      vm.setWorkspaceX(item.getWorkspaceX());
      vm.setWorkspaceY(item.getWorkspaceY());

      orderItems.add(vm);
    }

    updateTotal();
  }

  public void createOrder(
      String firstName, String lastName, String phone, String style, String preferredColor) {

    if (authService == null) {
      return;
    }

    if (authService.getCurrentUser() == null) {
      return;
    }

    Order order = new Order();

    order.setUserId(authService.getCurrentUser().getUserId());
    order.setCustomerFirstName(firstName);
    order.setCustomerLastName(lastName);
    order.setPhone(phone);
    order.setStyle(style);
    order.setPreferredColor(preferredColor);
    order.setBudget(new BigDecimal(totalText.get()));
    order.setStatus(OrderStatus.NEW);
    order.setCreatedAt(LocalDateTime.now());

    List<OrderItem> items = buildOrderItems();

    orderService.createOrder(order, items);

    orderItems.clear();
    updateTotal();
  }

  public void updateOrder(Order order) {
    if (order == null) {
      return;
    }

    order.setBudget(new BigDecimal(totalText.get()));

    orderService.update(order);
  }

  private List<OrderItem> buildOrderItems() {
    List<OrderItem> items = new ArrayList<>();

    for (OrderLineViewModel vm : orderItems) {
      OrderItem item = new OrderItem();

      item.setItemType(vm.getItemType());
      item.setQuantity(vm.getQuantity());
      item.setPriceSnapshot(vm.getPrice());
      item.setFlowerId(vm.getFlowerId());
      item.setBouquetId(vm.getBouquetId());
      item.setAccessoryId(vm.getAccessoryId());
      item.setWorkspaceX(vm.getWorkspaceX());
      item.setWorkspaceY(vm.getWorkspaceY());

      items.add(item);
    }

    return items;
  }
}
