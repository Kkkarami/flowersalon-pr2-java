package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.BouquetRepository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class BouquetRepositoryImpl extends BaseRepository<Bouquet> implements BouquetRepository {

    @Override
    protected String tableName() {
        return "Bouquets";
    }

    @Override
    protected String idColumn() {
        return "bouquet_id";
    }

    @Override
    protected Bouquet map(ResultSet rs) throws SQLException {
        UUID createdBy = null;

        if (rs.getString("created_by") != null) {
            createdBy = UUID.fromString(rs.getString("created_by"));
        }

        return new Bouquet(
              UUID.fromString(rs.getString("bouquet_id")),
              rs.getString("name"),
              rs.getString("description"),
              rs.getBoolean("is_custom"),
              rs.getTimestamp("created_at").toLocalDateTime(),
              createdBy,
              rs.getString("image_path"));
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Bouquet bouquet) throws SQLException {
        stmt.setString(1, bouquet.getBouquetId().toString());
        stmt.setString(2, bouquet.getName());
        stmt.setString(3, bouquet.getDescription());
        stmt.setBoolean(4, bouquet.isCustom());
        stmt.setTimestamp(5, Timestamp.valueOf(bouquet.getCreatedAt()));

        if (bouquet.getCreatedBy() != null) {
            stmt.setString(6, bouquet.getCreatedBy().toString());
        } else {
            stmt.setString(6, null);
        }

        stmt.setString(7, bouquet.getImagePath());
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Bouquet bouquet) throws SQLException {
        stmt.setString(1, bouquet.getName());
        stmt.setString(2, bouquet.getDescription());
        stmt.setBoolean(3, bouquet.isCustom());
        stmt.setString(4, bouquet.getImagePath());
        stmt.setString(5, bouquet.getBouquetId().toString());
    }

    @Override
    protected UUID getId(Bouquet entity) {
        return entity.getBouquetId();
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO Bouquets (bouquet_id, name, description, is_custom, created_at, created_by, image_path) VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE Bouquets SET name=?, description=?, is_custom=?, image_path=? WHERE bouquet_id=?";
    }
}