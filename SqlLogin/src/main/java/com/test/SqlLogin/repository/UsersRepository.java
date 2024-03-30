package com.test.SqlLogin.repository;

import com.test.SqlLogin.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Users findByIdAndPw(String id, String pw) {
        String sql = "SELECT * FROM users WHERE id = ? AND pw = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id, pw}, (rs, rowNum) -> {
                Users user = new Users();
                user.setId(rs.getString("id"));
                user.setPw(rs.getString("pw"));
                user.setName(rs.getString("name"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                return user;
            });
        } catch (DataAccessException e) {
            return null;
        }
    }

    public Users findById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
                Users user = new Users();
                user.setId(rs.getString("id"));
                user.setPw(rs.getString("pw"));
                user.setName(rs.getString("name"));
                user.setAdmin(rs.getBoolean("isAdmin"));
                return user;
            });
        } catch (DataAccessException e) {
            return null;
        }
    }

    public List<Users> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Users user = new Users();
            user.setId(rs.getString("id"));
            user.setPw(rs.getString("pw"));
            user.setName(rs.getString("name"));
            user.setAdmin(rs.getBoolean("isAdmin"));
            return user;
        });
    }

    public Users save(Users user) {
        String sql = "INSERT INTO users (id, pw, name, isAdmin) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getId(), user.getPw(), user.getName(), user.isAdmin());
        return user;
    }
}
