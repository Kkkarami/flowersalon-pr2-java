package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.Order;
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
        return new Order(
              UUID.fromString(rs.getString("order_id")),
              rs.getString("user_id") != null ? UUID.fromString(rs.getString("user_id")) : null,
              rs.getString("customer_first_name"),
              rs.getString("customer_last_name"),
              rs.getString("phone"),
              rs.getBigDecimal("budget"),
              rs.getString("style"),
              rs.getString("preferred_color"),
              rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Order o) throws SQLException {
        stmt.setString(1, o.getOrderId().toString());
        stmt.setString(2, o.getUserId() != null ? o.getUserId().toString() : null);
        stmt.setString(3, o.getCustomerFirstName());
        stmt.setString(4, o.getCustomerLastName());
        stmt.setString(5, o.getPhone());
        stmt.setBigDecimal(6, o.getBudget());
        stmt.setString(7, o.getStyle());
        stmt.setString(8, o.getPreferredColor());
        stmt.setTimestamp(9, Timestamp.valueOf(o.getCreatedAt()));
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Order o) throws SQLException {
        stmt.setString(1, o.getCustomerFirstName());
        stmt.setString(2, o.getCustomerLastName());
        stmt.setString(3, o.getPhone());
        stmt.setBigDecimal(4, o.getBudget());
        stmt.setString(5, o.getStyle());
        stmt.setString(6, o.getPreferredColor());
        stmt.setString(7, o.getOrderId().toString());
    }

    @Override
    protected UUID getId(Order entity) {
        return entity.getOrderId();
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO Orders (order_id, user_id, customer_first_name, customer_last_name, phone, budget, style, preferred_color, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE Orders SET customer_first_name=?, customer_last_name=?, phone=?, budget=?, style=?, preferred_color=? WHERE order_id=?";
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