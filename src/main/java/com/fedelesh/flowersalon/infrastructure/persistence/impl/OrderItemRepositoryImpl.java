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

public class OrderItemRepositoryImpl extends BaseRepository<OrderItem> implements OrderItemRepository {

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
        return """
                INSERT INTO Order_Items (
                    order_item_id,
                    order_id,
                    item_type,
                    flower_id,
                    bouquet_id,
                    accessory_id,
                    quantity,
                    price_snapshot,
                    workspace_x,
                    workspace_y
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
    }

    @Override
    protected String updateSql() {
        return """
                UPDATE Order_Items
                SET item_type=?,
                    flower_id=?,
                    bouquet_id=?,
                    accessory_id=?,
                    quantity=?,
                    price_snapshot=?,
                    workspace_x=?,
                    workspace_y=?
                WHERE order_item_id=?
                """;
    }

    @Override
    protected OrderItem map(ResultSet rs) throws SQLException {
        UUID flowerId = null;
        UUID bouquetId = null;
        UUID accessoryId = null;

        if (rs.getString("flower_id") != null) {
            flowerId = UUID.fromString(rs.getString("flower_id"));
        }

        if (rs.getString("bouquet_id") != null) {
            bouquetId = UUID.fromString(rs.getString("bouquet_id"));
        }

        if (rs.getString("accessory_id") != null) {
            accessoryId = UUID.fromString(rs.getString("accessory_id"));
        }

        return new OrderItem(
              UUID.fromString(rs.getString("order_item_id")),
              UUID.fromString(rs.getString("order_id")),
              rs.getString("item_type"),
              flowerId,
              bouquetId,
              accessoryId,
              rs.getInt("quantity"),
              rs.getBigDecimal("price_snapshot"),
              rs.getDouble("workspace_x"),
              rs.getDouble("workspace_y")
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, OrderItem item) throws SQLException {
        stmt.setString(1, item.getOrderItemId().toString());
        stmt.setString(2, item.getOrderId().toString());
        stmt.setString(3, item.getItemType());

        if (item.getFlowerId() != null) {
            stmt.setString(4, item.getFlowerId().toString());
        } else {
            stmt.setString(4, null);
        }

        if (item.getBouquetId() != null) {
            stmt.setString(5, item.getBouquetId().toString());
        } else {
            stmt.setString(5, null);
        }

        if (item.getAccessoryId() != null) {
            stmt.setString(6, item.getAccessoryId().toString());
        } else {
            stmt.setString(6, null);
        }

        stmt.setInt(7, item.getQuantity());
        stmt.setBigDecimal(8, item.getPriceSnapshot());
        stmt.setDouble(9, item.getWorkspaceX());
        stmt.setDouble(10, item.getWorkspaceY());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, OrderItem item) throws SQLException {
        stmt.setString(1, item.getItemType());

        if (item.getFlowerId() != null) {
            stmt.setString(2, item.getFlowerId().toString());
        } else {
            stmt.setString(2, null);
        }

        if (item.getBouquetId() != null) {
            stmt.setString(3, item.getBouquetId().toString());
        } else {
            stmt.setString(3, null);
        }

        if (item.getAccessoryId() != null) {
            stmt.setString(4, item.getAccessoryId().toString());
        } else {
            stmt.setString(4, null);
        }

        stmt.setInt(5, item.getQuantity());
        stmt.setBigDecimal(6, item.getPriceSnapshot());
        stmt.setDouble(7, item.getWorkspaceX());
        stmt.setDouble(8, item.getWorkspaceY());
        stmt.setString(9, item.getOrderItemId().toString());
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