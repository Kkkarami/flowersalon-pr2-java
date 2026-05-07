package com.fedelesh.flowersalon.infrastructure.storage.impl;

import com.fedelesh.flowersalon.domain.entity.Bouquet;
import com.fedelesh.flowersalon.infrastructure.storage.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.storage.contract.BouquetRepository;
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
        return new Bouquet(
              UUID.fromString(rs.getString("bouquet_id")),
              rs.getString("name"),
              rs.getString("description"),
              rs.getBoolean("is_custom"),
              rs.getTimestamp("created_at").toLocalDateTime(),
              rs.getString("created_by") != null ? UUID.fromString(rs.getString("created_by"))
                    : null
        );
    }

    @Override
    protected void setInsertParams(PreparedStatement stmt, Bouquet b) throws SQLException {
        stmt.setString(1, b.getBouquetId().toString());
        stmt.setString(2, b.getName());
        stmt.setString(3, b.getDescription());
        stmt.setBoolean(4, b.isCustom());
        stmt.setTimestamp(5, Timestamp.valueOf(b.getCreatedAt()));
        stmt.setString(6, b.getCreatedBy() != null ? b.getCreatedBy().toString() : null);
    }

    @Override
    protected void setUpdateParams(PreparedStatement stmt, Bouquet b) throws SQLException {
        stmt.setString(1, b.getName());
        stmt.setString(2, b.getDescription());
        stmt.setBoolean(3, b.isCustom());
        stmt.setString(4, b.getBouquetId().toString());
    }

    @Override
    protected UUID getId(Bouquet entity) {
        return entity.getBouquetId();
    }

    @Override
    protected String insertSql() {
        return "INSERT INTO Bouquets (bouquet_id, name, description, is_custom, created_at, created_by) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String updateSql() {
        return "UPDATE Bouquets SET name=?, description=?, is_custom=? WHERE bouquet_id=?";
    }
}