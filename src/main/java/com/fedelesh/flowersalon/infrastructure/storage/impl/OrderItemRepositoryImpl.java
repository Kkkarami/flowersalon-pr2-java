package com.fedelesh.flowersalon.infrastructure.storage.impl;

import com.fedelesh.flowersalon.domain.entity.OrderItem;
import com.fedelesh.flowersalon.infrastructure.storage.BaseRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class OrderItemRepositoryImpl extends BaseRepository<OrderItem> {

    @Override
    protected String tableName() {
        return "order_items";
    }

    @Override
    protected String idColumn() {
        return "order_item_id";
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO order_items (order_item_id, order_id, item_type, flower_id, bouquet_id, quantity, price_snapshot) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE order_items SET item_type=?, flower_id=?, bouquet_id=?, quantity=?, price_snapshot=? WHERE order_item_id=?";
    }

    @Override
    protected OrderItem map(ResultSet rs) throws SQLException {
        return new OrderItem(
              UUID.fromString(rs.getString("order_item_id")),
              UUID.fromString(rs.getString("order_id")),
              rs.getString("item_type"),
              rs.getString("flower_id") != null ? UUID.fromString(rs.getString("flower_id")) : null,
              rs.getString("bouquet_id") != null ? UUID.fromString(rs.getString("bouquet_id"))
                    : null,
              rs.getInt("quantity"),
              rs.getBigDecimal("price_snapshot")
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, OrderItem o) throws SQLException {
        stmt.setString(1, o.getOrderItemId().toString());
        stmt.setString(2, o.getOrderId().toString());
        stmt.setString(3, o.getItemType());
        stmt.setString(4, o.getFlowerId() != null ? o.getFlowerId().toString() : null);
        stmt.setString(5, o.getBouquetId() != null ? o.getBouquetId().toString() : null);
        stmt.setInt(6, o.getQuantity());
        stmt.setBigDecimal(7, o.getPriceSnapshot());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, OrderItem o) throws SQLException {
        stmt.setString(1, o.getItemType());
        stmt.setString(2, o.getFlowerId() != null ? o.getFlowerId().toString() : null);
        stmt.setString(3, o.getBouquetId() != null ? o.getBouquetId().toString() : null);
        stmt.setInt(4, o.getQuantity());
        stmt.setBigDecimal(5, o.getPriceSnapshot());
        stmt.setString(6, o.getOrderItemId().toString());
    }

    @Override
    protected UUID getId(OrderItem entity) {
        return entity.getOrderItemId();
    }
}