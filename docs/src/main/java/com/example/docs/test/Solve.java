package com.example.docs.test;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@IdClass(SolvePrimaryKey.class)
public class Solve {
    @Id
    @ManyToOne(targetEntity = Problem.class)
    private Integer problemId;
    
    @Id
    @ManyToOne(targetEntity = Member.class)
    private Integer id;
}
