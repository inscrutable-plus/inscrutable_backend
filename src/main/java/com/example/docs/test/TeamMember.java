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
@IdClass(TeamMemberPrimaryKey.class)
public class TeamMember {
    @Id
    @ManyToOne(targetEntity = Member.class)
    private Integer id;
    
    @Id
    @ManyToOne(targetEntity = Team.class)
    private Integer teamId; 
}
