package com.fedelesh.flowersalon.infrastructure.storage.impl;

import com.fedelesh.flowersalon.domain.entity.BouquetItem;
import com.fedelesh.flowersalon.infrastructure.storage.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.storage.contract.BouquetItemRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BouquetItemRepositoryImpl extends BaseRepository<BouquetItem> implements
      BouquetItemRepository {

    @Override
    protected String tableName() {
        return "Bouquet_Items";
    }

    @Override
    protected String idColumn() {
        return "bouquet_item_id";
    }

    @Override
    protected BouquetItem map(ResultSet rs) throws SQLException {
        return new BouquetItem(
              UUID.fromString(rs.getString("bouquet_item_id")),
              UUID.fromString(rs.getString("bouquet_id")),
              UUID.fromString(rs.getString("flower_id")),
              rs.getInt("quantity")
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, BouquetItem b)
          throws SQLException {

        stmt.setString(1, b.getBouquetItemId().toString());
        stmt.setString(2, b.getBouquetId().toString());
        stmt.setString(3, b.getFlowerId().toString());
        stmt.setInt(4, b.getQuantity());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, BouquetItem b)
          throws SQLException {

        stmt.setString(1, b.getBouquetId().toString());
        stmt.setString(2, b.getFlowerId().toString());
        stmt.setInt(3, b.getQuantity());
        stmt.setString(4, b.getBouquetItemId().toString());
    }

    @Override
    protected UUID getId(BouquetItem entity) {
        return entity.getBouquetItemId();
    }

    @Override
    protected String insertSql() {
        return """
              INSERT INTO Bouquet_Items
              (bouquet_item_id, bouquet_id, flower_id, quantity)
              VALUES (?, ?, ?, ?)
              """;
    }

    @Override
    protected String updateSql() {
        return """
              UPDATE Bouquet_Items
              SET bouquet_id=?, flower_id=?, quantity=?
              WHERE bouquet_item_id=?
              """;
    }

    @Override
    public List<BouquetItem> findByBouquetId(UUID bouquetId) {

        List<BouquetItem> items = new ArrayList<>();

        String sql = "SELECT * FROM Bouquet_Items WHERE bouquet_id = ?";

        try (Connection conn = pool.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bouquetId.toString());

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    items.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку квітів букета", e);
        }

        return items;
    }
}