package com.fedelesh.flowersalon.infrastructure.persistence.impl;

import com.fedelesh.flowersalon.domain.entity.User;
import com.fedelesh.flowersalon.domain.enums.Role;
import com.fedelesh.flowersalon.infrastructure.persistence.BaseRepository;
import com.fedelesh.flowersalon.infrastructure.persistence.contract.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

  @Override
  protected String tableName() {
    return "Users";
  }

  @Override
  protected String idColumn() {
    return "user_id";
  }

  @Override
  protected User map(ResultSet rs) throws SQLException {
    return new User(
        UUID.fromString(rs.getString("user_id")),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email"),
        rs.getString("phone"),
        Role.valueOf(rs.getString("role")),
        rs.getString("password_hash"),
        rs.getTimestamp("created_at").toLocalDateTime());
  }

  @Override
  protected void setInsertParams(PreparedStatement stmt, User u) throws SQLException {
    stmt.setString(1, u.getUserId().toString());
    stmt.setString(2, u.getFirstName());
    stmt.setString(3, u.getLastName());
    stmt.setString(4, u.getEmail());
    stmt.setString(5, u.getPhone());
    stmt.setString(6, u.getRole().name());
    stmt.setString(7, u.getPasswordHash());
    stmt.setTimestamp(8, Timestamp.valueOf(u.getCreatedAt()));
  }

  @Override
  protected void setUpdateParams(PreparedStatement stmt, User u) throws SQLException {
    stmt.setString(1, u.getFirstName());
    stmt.setString(2, u.getLastName());
    stmt.setString(3, u.getEmail());
    stmt.setString(4, u.getPhone());
    stmt.setString(5, u.getRole().name());
    stmt.setString(6, u.getUserId().toString());
  }

  @Override
  protected UUID getId(User entity) {
    return entity.getUserId();
  }

  @Override
  protected String insertSql() {
    return "INSERT INTO Users (user_id, first_name, last_name, email, phone, role, password_hash, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
  }

  @Override
  protected String updateSql() {
    return "UPDATE Users SET first_name=?, last_name=?, email=?, phone=?, role=? WHERE user_id=?";
  }

  @Override
  public Optional<User> findByEmail(String email) {

    String sql = "SELECT * FROM Users WHERE email = ?";

    try (Connection conn = pool.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, email);

      try (ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
          return Optional.of(map(rs));
        }
      }

    } catch (SQLException e) {
      throw new RuntimeException("Помилка при пошуку користувача по email", e);
    }

    return Optional.empty();
  }
}
