package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.Accessory;
import com.fedelesh.flowersalon.domain.enums.AccessoryType;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.AccessoryRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccessoryRepositoryImpl extends BaseRepository<Accessory>
    implements AccessoryRepository {

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
    return new Accessory(
        UUID.fromString(rs.getString("accessory_id")),
        rs.getString("name"),
        rs.getString("type") != null ? AccessoryType.valueOf(rs.getString("type")) : null,
        rs.getString("color"),
        rs.getBigDecimal("price"),
        rs.getInt("stock_quantity"));
  }

  @Override
  protected void setInsertParams(PreparedStatement stmt, Accessory a) throws SQLException {
    stmt.setString(1, a.getAccessoryId().toString());
    stmt.setString(2, a.getName());
    stmt.setString(3, a.getAccessoryType() != null ? a.getAccessoryType().name() : null);
    stmt.setString(4, a.getColor());
    stmt.setBigDecimal(5, a.getPrice());
    stmt.setInt(6, a.getStockQuantity());
  }

  @Override
  protected void setUpdateParams(PreparedStatement stmt, Accessory a) throws SQLException {
    stmt.setString(1, a.getName());
    stmt.setString(2, a.getAccessoryType() != null ? a.getAccessoryType().name() : null);
    stmt.setString(3, a.getColor());
    stmt.setBigDecimal(4, a.getPrice());
    stmt.setInt(5, a.getStockQuantity());
    stmt.setString(6, a.getAccessoryId().toString());
  }

  @Override
  protected UUID getId(Accessory entity) {
    return entity.getAccessoryId();
  }

  @Override
  protected String insertSql() {
    return "INSERT INTO Accessories (accessory_id, name, type, color, price, stock_quantity) VALUES (?, ?, ?, ?, ?, ?)";
  }

  @Override
  protected String updateSql() {
    return "UPDATE Accessories SET name=?, type=?, color=?, price=?, stock_quantity=? WHERE accessory_id=?";
  }
}
