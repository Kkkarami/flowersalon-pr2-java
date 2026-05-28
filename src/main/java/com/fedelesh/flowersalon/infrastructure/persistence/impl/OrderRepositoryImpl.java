package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.Order;
import com.fedelesh.flowersalon.domain.enums.OrderStatus;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderRepositoryImpl extends BaseRepository<Order> implements OrderRepository {

  @Override
  protected String tableName() {
    return "Orders";
  }

  @Override
  protected String idColumn() {
    return "order_id";
  }

  @Override
  protected Order map(ResultSet rs) throws SQLException {
    UUID userId = null;

    if (rs.getString("user_id") != null) {
      userId = UUID.fromString(rs.getString("user_id"));
    }

    OrderStatus status = OrderStatus.NEW;

    if (rs.getString("status") != null) {
      status = OrderStatus.valueOf(rs.getString("status"));
    }

    return new Order(
        UUID.fromString(rs.getString("order_id")),
        userId,
        rs.getString("customer_first_name"),
        rs.getString("customer_last_name"),
        rs.getString("phone"),
        rs.getBigDecimal("budget"),
        rs.getString("style"),
        rs.getString("preferred_color"),
        status,
        rs.getTimestamp("created_at").toLocalDateTime());
  }

  @Override
  protected void setInsertParams(PreparedStatement stmt, Order order) throws SQLException {
    stmt.setString(1, order.getOrderId().toString());

    if (order.getUserId() != null) {
      stmt.setString(2, order.getUserId().toString());
    } else {
      stmt.setString(2, null);
    }

    stmt.setString(3, order.getCustomerFirstName());
    stmt.setString(4, order.getCustomerLastName());
    stmt.setString(5, order.getPhone());
    stmt.setBigDecimal(6, order.getBudget());
    stmt.setString(7, order.getStyle());
    stmt.setString(8, order.getPreferredColor());

    if (order.getStatus() != null) {
      stmt.setString(9, order.getStatus().name());
    } else {
      stmt.setString(9, OrderStatus.NEW.name());
    }

    stmt.setTimestamp(10, Timestamp.valueOf(order.getCreatedAt()));
  }

  @Override
  protected void setUpdateParams(PreparedStatement stmt, Order order) throws SQLException {
    stmt.setString(1, order.getCustomerFirstName());
    stmt.setString(2, order.getCustomerLastName());
    stmt.setString(3, order.getPhone());
    stmt.setBigDecimal(4, order.getBudget());
    stmt.setString(5, order.getStyle());
    stmt.setString(6, order.getPreferredColor());

    if (order.getStatus() != null) {
      stmt.setString(7, order.getStatus().name());
    } else {
      stmt.setString(7, OrderStatus.NEW.name());
    }

    stmt.setString(8, order.getOrderId().toString());
  }

  @Override
  protected UUID getId(Order entity) {
    return entity.getOrderId();
  }

  @Override
  protected String insertSql() {
    return """
                INSERT INTO Orders (
                    order_id,
                    user_id,
                    customer_first_name,
                    customer_last_name,
                    phone,
                    budget,
                    style,
                    preferred_color,
                    status,
                    created_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
  }

  @Override
  protected String updateSql() {
    return """
                UPDATE Orders
                SET customer_first_name=?,
                    customer_last_name=?,
                    phone=?,
                    budget=?,
                    style=?,
                    preferred_color=?,
                    status=?
                WHERE order_id=?
                """;
  }

  @Override
  public List<Order> findByUserId(UUID userId) {
    List<Order> orders = new ArrayList<>();

    String sql = "SELECT * FROM Orders WHERE user_id = ?";

    try (Connection conn = pool.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, userId.toString());

      try (ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
          orders.add(map(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Помилка при пошуку замовлень користувача", e);
    }

    return orders;
  }
}
