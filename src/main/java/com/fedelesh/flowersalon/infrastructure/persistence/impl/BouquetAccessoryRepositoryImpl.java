package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.BouquetAccessory;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetAccessoryRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BouquetAccessoryRepositoryImpl extends BaseRepository<BouquetAccessory>
        implements BouquetAccessoryRepository {

    @Override
    protected String tableName() {
        return "Bouquet_Accessories";
    }

    @Override
    protected String idColumn() {
        return "bouquet_accessory_id";
    }

    @Override
    protected BouquetAccessory map(ResultSet rs) throws SQLException {
        return new BouquetAccessory(
                UUID.fromString(rs.getString("bouquet_accessory_id")),
                UUID.fromString(rs.getString("bouquet_id")),
                UUID.fromString(rs.getString("accessory_id")),
                rs.getInt("quantity"));
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, BouquetAccessory b) throws SQLException {

        stmt.setString(1, b.getBouquetAccessoryId().toString());
        stmt.setString(2, b.getBouquetId().toString());
        stmt.setString(3, b.getAccessoryId().toString());
        stmt.setInt(4, b.getQuantity());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, BouquetAccessory b) throws SQLException {

        stmt.setString(1, b.getBouquetId().toString());
        stmt.setString(2, b.getAccessoryId().toString());
        stmt.setInt(3, b.getQuantity());
        stmt.setString(4, b.getBouquetAccessoryId().toString());
    }

    @Override
    protected UUID getId(BouquetAccessory entity) {
        return entity.getBouquetAccessoryId();
    }

    @Override
    protected String insertSql() {
        return """
              INSERT INTO Bouquet_Accessories
              (bouquet_accessory_id, bouquet_id, accessory_id, quantity)
              VALUES (?, ?, ?, ?)
              """;
    }

    @Override
    protected String updateSql() {
        return """
              UPDATE Bouquet_Accessories
              SET bouquet_id=?, accessory_id=?, quantity=?
              WHERE bouquet_accessory_id=?
              """;
    }

    @Override
    public List<BouquetAccessory> findByBouquetId(UUID bouquetId) {

        List<BouquetAccessory> accessories = new ArrayList<>();

        String sql = "SELECT * FROM Bouquet_Accessories WHERE bouquet_id = ?";

        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bouquetId.toString());

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    accessories.add(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку аксесуарів букета", e);
        }

        return accessories;
    }
}
