package com.example.docs.test;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingPrimaryKey implements Serializable {
    private Integer weekId;
    private Integer id;
}
