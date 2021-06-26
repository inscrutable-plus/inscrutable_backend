package com.example.docs.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SolveRepository extends CrudRepository<Solve, Integer> {

    @Query(value = "select * from solves where handle = :handle", nativeQuery = true)
    List<Solve> findSolvedByHandle(@Param("handle") String handle);

    @Query(value = "select * from solves where handle in (select handle from member where team = :team)", nativeQuery = true)
    List<Solve> findSolvedByTeam(@Param("team") Integer team);

    @Query(value = "select count(distinct problemId) from solves where handle in (select handle from member where team = :team)", nativeQuery = true)
    Integer findCountByTeam(@Param("team") Integer team);

    @Query(value = "select count(problemId) from solves where handle = :handle", nativeQuery = true)
    Integer findCountByHandle(@Param("handle") String handle);
}
