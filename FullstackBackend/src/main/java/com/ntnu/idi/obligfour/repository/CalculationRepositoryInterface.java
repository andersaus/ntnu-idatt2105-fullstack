package com.ntnu.idi.obligfour.repository;

import com.ntnu.idi.obligfour.model.Calculation;

import java.util.List;
import java.util.Optional;

public interface CalculationRepositoryInterface {
    void save(Calculation calculation);
    List<String> findAllByUserId(Long user_id, int pageNumber, int pageSize);
}