package com.fedelesh.flowersalon.presentation.viewmodel;

import com.fedelesh.flowersalon.application.contract.AccessoryService;
import com.fedelesh.flowersalon.application.contract.AuthService;
import com.fedelesh.flowersalon.application.contract.BouquetService;
import com.fedelesh.flowersalon.application.contract.FlowerService;
import com.fedelesh.flowersalon.application.contract.OrderService;
import com.fedelesh.flowersalon.application.impl.AccessoryServiceImpl;
import com.fedelesh.flowersalon.application.impl.BouquetServiceImpl;
import com.fedelesh.flowersalon.application.impl.FlowerServiceImpl;
import com.fedelesh.flowersalon.application.impl.OrderServiceImpl;
import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.AccessoryRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.BouquetRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.FlowerRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderItemRepositoryImpl;
import com.fedelesh.flowersalon.infrastructure.persistence.impl.OrderRepositoryImpl;
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

    private final ObservableList<Flower> flowers =
          FXCollections.observableArrayList();

    private final ObservableList<Bouquet> bouquets =
          FXCollections.observableArrayList();

    private final ObservableList<Accessory> accessories =
          FXCollections.observableArrayList();

    private final ObservableList<OrderLineViewModel> orderItems =
          FXCollections.observableArrayList();

    private final StringProperty totalText =
          new SimpleStringProperty("0");

    public CreateOrderViewModel(AuthService authService) {

        this.authService = authService;

        flowerService = new FlowerServiceImpl(
              new FlowerRepositoryImpl()
        );

        bouquetService = new BouquetServiceImpl(
              new BouquetRepositoryImpl()
        );

        accessoryService = new AccessoryServiceImpl(
              new AccessoryRepositoryImpl()
        );

        orderService = new OrderServiceImpl(
              new OrderRepositoryImpl(),
              new OrderItemRepositoryImpl()
        );

        loadData();
    }

    private void loadData() {

        flowers.setAll(
              flowerService.getAll()
        );

        bouquets.setAll(
              bouquetService.getAll()
        );

        accessories.setAll(
              accessoryService.getAll()
        );
    }

    public String getCurrentUserFirstName() {

        return authService
              .getCurrentUser()
              .getFirstName();
    }

    public String getCurrentUserLastName() {

        return authService
              .getCurrentUser()
              .getLastName();
    }

    public String getCurrentUserPhone() {

        return authService
              .getCurrentUser()
              .getPhone();
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

    public void addFlower(Flower flower) {

        if (flower == null) {
            return;
        }

        OrderLineViewModel item =
              new OrderLineViewModel(
                    "Flower",
                    flower.getName(),
                    1,
                    flower.getPrice()
              );

        item.setFlowerId(
              flower.getFlowerId()
        );

        orderItems.add(item);

        updateTotal();
    }

    public void addBouquet(Bouquet bouquet) {

        if (bouquet == null) {
            return;
        }

        OrderLineViewModel item =
              new OrderLineViewModel(
                    "Bouquet",
                    bouquet.getName(),
                    1,
                    BigDecimal.valueOf(500)
              );

        item.setBouquetId(
              bouquet.getBouquetId()
        );

        orderItems.add(item);

        updateTotal();
    }

    public void addAccessory(Accessory accessory) {

        if (accessory == null) {
            return;
        }

        OrderLineViewModel item =
              new OrderLineViewModel(
                    "Accessory",
                    accessory.getName(),
                    1,
                    accessory.getPrice()
              );

        item.setAccessoryId(
              accessory.getAccessoryId()
        );

        orderItems.add(item);

        updateTotal();
    }

    public void removeItem(OrderLineViewModel item) {

        if (item == null) {
            return;
        }

        orderItems.remove(item);

        updateTotal();
    }

    private void updateTotal() {

        List<OrderItem> items =
              new ArrayList<>();

        for (OrderLineViewModel vm : orderItems) {

            OrderItem item =
                  new OrderItem();

            item.setQuantity(
                  vm.getQuantity()
            );

            item.setPriceSnapshot(
                  vm.getPrice()
            );

            items.add(item);
        }

        BigDecimal total =
              orderService.calculateTotal(items);

        totalText.set(
              total.toString()
        );
    }

    public void createOrder(
          String firstName,
          String lastName,
          String phone,
          String style,
          String preferredColor
    ) {

        Order order = new Order();

        order.setUserId(
              authService
                    .getCurrentUser()
                    .getUserId()
        );

        order.setCustomerFirstName(firstName);

        order.setCustomerLastName(lastName);

        order.setPhone(phone);

        order.setStyle(style);

        order.setPreferredColor(preferredColor);

        order.setBudget(
              new BigDecimal(
                    totalText.get()
              )
        );

        order.setCreatedAt(
              LocalDateTime.now()
        );

        List<OrderItem> items =
              new ArrayList<>();

        for (OrderLineViewModel vm : orderItems) {

            OrderItem item =
                  new OrderItem();

            item.setItemType(
                  vm.getItemType()
            );

            item.setQuantity(
                  vm.getQuantity()
            );

            item.setPriceSnapshot(
                  vm.getPrice()
            );

            item.setFlowerId(
                  vm.getFlowerId()
            );

            item.setBouquetId(
                  vm.getBouquetId()
            );

            item.setAccessoryId(
                  vm.getAccessoryId()
            );

            items.add(item);
        }

        orderService.createOrder(
              order,
              items
        );

        orderItems.clear();

        updateTotal();
    }
    
    public void updateOrder(Order order) {

        orderService.update(order);
    }

}
