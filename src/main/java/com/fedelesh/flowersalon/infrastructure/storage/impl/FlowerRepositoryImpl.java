package com.fedelesh.flowersalon.infrastructure.storage.impl;

import com.fedelesh.flowersalon.domain.entity.Flower;
import com.fedelesh.flowersalon.infrastructure.storage.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.storage.contract.FlowerRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlowerRepositoryImpl extends BaseRepository<Flower> implements FlowerRepository {

    @Override
    protected String tableName() {
        return "Flowers";
    }

    @Override
    protected String idColumn() {
        return "flower_id";
    }

    @Override
    protected Flower map(ResultSet rs) throws SQLException {
        return new Flower(
              UUID.fromString(rs.getString("flower_id")),
              rs.getString("name"),
              rs.getString("color"),
              rs.getBigDecimal("price"),
              rs.getInt("stock_quantity"),
              rs.getString("created_by") != null ? UUID.fromString(rs.getString("created_by"))
                    : null
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Flower f) throws SQLException {
        stmt.setString(1, f.getFlowerId().toString());
        stmt.setString(2, f.getName());
        stmt.setString(3, f.getColor());
        stmt.setBigDecimal(4, f.getPrice());
        stmt.setInt(5, f.getStockQuantity());
        stmt.setString(6, f.getCreatedBy() != null ? f.getCreatedBy().toString() : null);
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Flower f) throws SQLException {
        stmt.setString(1, f.getName());
        stmt.setString(2, f.getColor());
        stmt.setBigDecimal(3, f.getPrice());
        stmt.setInt(4, f.getStockQuantity());
        stmt.setString(5, f.getFlowerId().toString());
    }

    @Override
    protected UUID getId(Flower entity) {
        return entity.getFlowerId();
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO Flowers (flower_id, name, color, price, stock_quantity, created_by) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE Flowers SET name=?, color=?, price=?, stock_quantity=? WHERE flower_id=?";
    }

    @Override
    public List<Flower> findByColor(String color) {

        List<Flower> flowers = new ArrayList<>();

        String sql = "SELECT * FROM Flowers WHERE color = ?";

        try (Connection conn = pool.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, color);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    flowers.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку квітів по кольору", e);
        }

        return flowers;
    }
}