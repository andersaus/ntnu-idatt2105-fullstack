package com.ntnu.idi.obligfour.repository;

import com.ntnu.idi.obligfour.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepositoryInterface {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO CALC_USERS (username, password) VALUES(:username, :password)";
        Map<String, Object> params = new HashMap<>();
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<User> findByID(long id) {
        String sql = "SELECT * FROM CALC_USERS WHERE id = :id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(User.class));
            return Optional.ofNullable(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM CALC_USERS WHERE username = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(User.class));
            return Optional.ofNullable(user);
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean checkPassword(String username, String password) {
        String sql = "SELECT COUNT(*) FROM CALC_USERS WHERE username = :username AND password = :password";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        Integer count = namedParameterJdbcTemplate.queryForObject(sql, params,Integer.class);
        return count != null && count > 0;
    }
}