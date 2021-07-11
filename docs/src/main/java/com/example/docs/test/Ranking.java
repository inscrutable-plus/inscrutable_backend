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
@IdClass(RankingPrimaryKey.class)
public class Ranking {
    @Id
    private Integer weekId;

    private Integer rank;

    @Id
    @ManyToOne(targetEntity = Member.class)
    private Integer id;
    private Integer score;
}
