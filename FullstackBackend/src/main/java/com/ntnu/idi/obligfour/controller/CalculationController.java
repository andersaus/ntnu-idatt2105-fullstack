package com.ntnu.idi.obligfour.controller;

import com.ntnu.idi.obligfour.model.Calculation;
import com.ntnu.idi.obligfour.model.Expression;
import com.ntnu.idi.obligfour.model.User;
import com.ntnu.idi.obligfour.repository.CalculationRepositoryInterface;
import com.ntnu.idi.obligfour.repository.JdbcCalculationRepository;
import com.ntnu.idi.obligfour.service.CalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/calculations")
public class CalculationController {
//    Logger logger = LoggerFactory.getLogger(CalculationController.class);
    private final CalculationRepositoryInterface calculationRepository;

    @Autowired
    public CalculationController(CalculationRepositoryInterface calculationRepository) { this.calculationRepository = calculationRepository; }

    @PostMapping("/user/{user_id}/add-calculation")
    public ResponseEntity<Object> addCalculation(@RequestBody Expression expression,
                                                              @PathVariable("user_id") Long user_id) {
        try {
            double result = CalculationService.compute(expression);
            expression.setResult(result);
            String equation = expression.toString();

            Calculation calculation = new Calculation(user_id, equation);
            calculationRepository.save(calculation);

            Map<String, Double> resultResponse = new HashMap<>();
            resultResponse.put("result", result);

            return ResponseEntity.status(HttpStatus.CREATED).body(resultResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid input or error handling calculation.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<String>> getHistory(
            @PathVariable("user_id") Long user_id,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        List<String> calculations = calculationRepository.findAllByUserId(user_id, page, size);
        return ResponseEntity.ok(calculations);
    }
}
