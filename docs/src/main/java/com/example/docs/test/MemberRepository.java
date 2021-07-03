package com.example.docs.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {

    // @Query(value = "select * from member where team = :team", nativeQuery = true)
    // List<Member> findByTeam(@Param("team") Integer team);

    @Query(value = "select * from member", nativeQuery = true)
    List<Member> findAll();
}
