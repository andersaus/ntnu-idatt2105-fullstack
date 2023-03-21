package com.ntnu.idi.obligfour.repository;

import com.ntnu.idi.obligfour.model.Calculation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcCalculationRepository implements CalculationRepositoryInterface {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcCalculationRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(Calculation calculation) {
        String sql = "INSERT INTO CALC_HISTORY (user_id, equation) VALUES(:user_id, :equation)";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", calculation.getUser_id());
        params.put("equation", calculation.getEquation());
        namedParameterJdbcTemplate.update(sql, params);
    }

    @Override
    public List<String> findAllByUserId(Long user_id, int pageNumber, int pageSize) {
        String sql = "SELECT equation FROM CALC_HISTORY WHERE user_id = :user_id ORDER BY id DESC LIMIT :limit OFFSET :offset";
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("limit", pageSize);
        params.put("offset", pageNumber * pageSize);

        return namedParameterJdbcTemplate.queryForList(sql, params, String.class);
    }
}
