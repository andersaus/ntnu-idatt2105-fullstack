package com.ntnu.idi.obligfour.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Calculation {
    public long id;
    public long user_id;
    public String equation;

    public Calculation() {

    }

    public Calculation(long id, long user_id, String equation) {
        this.id = id;
        this.user_id = user_id;
        this.equation = equation;
    }

    public Calculation(long user_id, String equation) {
        this.user_id = user_id;
        this.equation = equation;
    }

    public Calculation(String equation) {
        this.equation = equation;
    }
}
