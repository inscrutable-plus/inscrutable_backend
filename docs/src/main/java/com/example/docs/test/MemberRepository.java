package com.example.docs.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Integer> {

    @Query(value = "select * from member where team = ?1", nativeQuery = true)
    List<Member> findByTeam(Integer team);

    @Query(value = "select * from member", nativeQuery = true)
    List<Member> findAll();
}
