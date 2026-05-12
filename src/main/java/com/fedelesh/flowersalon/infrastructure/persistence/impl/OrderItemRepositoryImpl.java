package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.OrderItemRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderItemRepositoryImpl extends BaseRepository<OrderItem>
    implements OrderItemRepository {

  @Override
  protected String tableName() {
    return "Order_Items";
  }

  @Override
  protected String idColumn() {
    return "order_item_id";
  }

  @Override
  protected String insertSql() {
    return "INSERT INTO order_items (order_item_id, order_id, item_type, flower_id, bouquet_id, accessory_id, quantity, price_snapshot) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  }

  @Override
  protected String updateSql() {
    return "UPDATE order_items SET item_type=?, flower_id=?, bouquet_id=?, accessory_id=?, quantity=?, price_snapshot=? WHERE order_item_id=?";
  }

  @Override
  protected OrderItem map(ResultSet rs) throws SQLException {
    return new OrderItem(
        UUID.fromString(rs.getString("order_item_id")),
        UUID.fromString(rs.getString("order_id")),
        rs.getString("item_type"),
        rs.getString("flower_id") != null ? UUID.fromString(rs.getString("flower_id")) : null,
        rs.getString("bouquet_id") != null ? UUID.fromString(rs.getString("bouquet_id")) : null,
        rs.getString("accessory_id") != null ? UUID.fromString(rs.getString("accessory_id")) : null,
        rs.getInt("quantity"),
        rs.getBigDecimal("price_snapshot"));
  }

  @Override
  protected void setInsertParams(PreparedStatement stmt, OrderItem o) throws SQLException {
    stmt.setString(1, o.getOrderItemId().toString());
    stmt.setString(2, o.getOrderId().toString());
    stmt.setString(3, o.getItemType());
    stmt.setString(4, o.getFlowerId() != null ? o.getFlowerId().toString() : null);
    stmt.setString(5, o.getBouquetId() != null ? o.getBouquetId().toString() : null);
    stmt.setString(6, o.getAccessoryId() != null ? o.getAccessoryId().toString() : null);
    stmt.setInt(7, o.getQuantity());
    stmt.setBigDecimal(8, o.getPriceSnapshot());
  }

  @Override
  protected void setUpdateParams(PreparedStatement stmt, OrderItem o) throws SQLException {
    stmt.setString(1, o.getItemType());
    stmt.setString(2, o.getFlowerId() != null ? o.getFlowerId().toString() : null);
    stmt.setString(3, o.getBouquetId() != null ? o.getBouquetId().toString() : null);
    stmt.setString(4, o.getAccessoryId() != null ? o.getAccessoryId().toString() : null);
    stmt.setInt(5, o.getQuantity());
    stmt.setBigDecimal(6, o.getPriceSnapshot());
    stmt.setString(7, o.getOrderItemId().toString());
  }

  @Override
  protected UUID getId(OrderItem entity) {
    return entity.getOrderItemId();
  }

  @Override
  public List<OrderItem> findByOrderId(UUID orderId) {

    List<OrderItem> items = new ArrayList<>();

    String sql = "SELECT * FROM Order_Items WHERE order_id = ?";

    try (Connection conn = pool.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, orderId.toString());

      try (ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
          items.add(map(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Помилка при пошуку елементів замовлення", e);
    }

    return items;
  }
}
