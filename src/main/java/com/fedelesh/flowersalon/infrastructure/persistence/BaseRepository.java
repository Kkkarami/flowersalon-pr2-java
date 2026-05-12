package com.fedelesh.flowersalon.infrastructure.persistence;

import com.fedelesh.flowersalon.infrastructure.persistence.util.ConnectionPool;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class BaseRepository<T> implements Repository<T> {

    protected final ConnectionPool pool = ConnectionPool.getInstance();

    protected abstract String tableName();

    protected abstract String idColumn();

    protected abstract T map(ResultSet rs) throws SQLException;

    protected abstract void setInsertParams(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract void setUpdateParams(PreparedStatement stmt, T entity) throws SQLException;

    protected abstract UUID getId(T entity);

    protected abstract String insertSql();

    protected abstract String updateSql();

    @Override
    public Optional<T> findById(UUID id) {
        String sql = "SELECT * FROM " + tableName() + " WHERE " + idColumn() + " = ?";

        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при пошуку сутності за id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName();

        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при отриманні списку сутностей", e);
        }

        return list;
    }

    @Override
    public void save(T entity) {
        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(insertSql())) {

            setInsertParams(stmt, entity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при збереженні сутності", e);
        }
    }

    @Override
    public void update(T entity) {
        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(updateSql())) {

            setUpdateParams(stmt, entity);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при оновленні сутності", e);
        }
    }

    @Override
    public void deleteById(UUID id) {
        String sql = "DELETE FROM " + tableName() + " WHERE " + idColumn() + " = ?";

        try (Connection conn = pool.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id.toString());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Помилка при видаленні сутності", e);
        }
    }
}
