package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.enums.AccessoryType;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.AccessoryRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccessoryRepositoryImpl extends BaseRepository<Accessory> implements AccessoryRepository {

    @Override
    protected String tableName() {
        return "Accessories";
    }

    @Override
    protected String idColumn() {
        return "accessory_id";
    }

    @Override
    protected Accessory map(ResultSet rs) throws SQLException {
        AccessoryType accessoryType = null;

        if (rs.getString("type") != null) {
            accessoryType = AccessoryType.valueOf(rs.getString("type"));
        }

        return new Accessory(
              UUID.fromString(rs.getString("accessory_id")),
              rs.getString("name"),
              accessoryType,
              rs.getString("color"),
              rs.getBigDecimal("price"),
              rs.getInt("stock_quantity"),
              rs.getString("image_path"));
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Accessory accessory) throws SQLException {
        stmt.setString(1, accessory.getAccessoryId().toString());
        stmt.setString(2, accessory.getName());

        if (accessory.getAccessoryType() != null) {
            stmt.setString(3, accessory.getAccessoryType().name());
        } else {
            stmt.setString(3, null);
        }

        stmt.setString(4, accessory.getColor());
        stmt.setBigDecimal(5, accessory.getPrice());
        stmt.setInt(6, accessory.getStockQuantity());
        stmt.setString(7, accessory.getImagePath());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Accessory accessory) throws SQLException {
        stmt.setString(1, accessory.getName());

        if (accessory.getAccessoryType() != null) {
            stmt.setString(2, accessory.getAccessoryType().name());
        } else {
            stmt.setString(2, null);
        }

        stmt.setString(3, accessory.getColor());
        stmt.setBigDecimal(4, accessory.getPrice());
        stmt.setInt(5, accessory.getStockQuantity());
        stmt.setString(6, accessory.getImagePath());
        stmt.setString(7, accessory.getAccessoryId().toString());
    }

    @Override
    protected UUID getId(Accessory entity) {
        return entity.getAccessoryId();
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO Accessories (accessory_id, name, type, color, price, stock_quantity, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE Accessories SET name=?, type=?, color=?, price=?, stock_quantity=?, image_path=? WHERE accessory_id=?";
    }
}