package com.example.docs.test;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Problem {
    @Id
    private int problemId;
    private String title;
    private int level;
}
