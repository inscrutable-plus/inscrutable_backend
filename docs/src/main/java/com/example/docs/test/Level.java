package com.example.docs.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Level {
    private Integer level;
    private Integer unsolved;
    private Integer solved;
    private Integer count;
    private Double percentage;
}
