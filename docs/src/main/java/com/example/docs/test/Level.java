package com.example.docs.test;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Level {
    private int level;
    private int unsolved;
    private int solved;
    private int count;
    private double percentage;
}
