package com.example.docs.test;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamMemberPrimaryKey implements Serializable {
    private Integer id;
    private Integer teamId;
}
