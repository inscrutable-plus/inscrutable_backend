package com.example.docs.test;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Solve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer solveId;
    private Integer problemId;
    private String handle;
}
