package com.example.docs.test;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Integer> {

    @Query(value = "select * from problems where title like concat('%',:title,'%') limit 10", nativeQuery = true)
    List<String> findProblemsByTitle(@Param("title") String title);

    @Query(value = "select * from problems where title like concat('%',:title,'%') limit :limit", nativeQuery = true)
    List<String> findProblemsByTitle(@Param("title") String title, @Param("limit") Integer limit);

    @Query(value = "select count(solve.problem_id) from problem join solve on solve.problem_id = problem.problem_id where problem.level = :level and solve.id = :id", nativeQuery = true)
    Integer countSolvedById(@Param("level") Integer level, @Param("id") Integer id);

    @Query(value = "select count(distinct solve.problem_id) from problem join solve on solve.problem_id = problem.problem_id where problem.level = :level and solve.id in (select id from team_member where team_id = :team_id);", nativeQuery = true)
    Integer countSolvedByTeamId(@Param("level") Integer level, @Param("team_id") Integer teamId);

    @Query(value = "select count(problem_id) from problem where level = :level", nativeQuery = true)
    Integer countByLevel(@Param("level") Integer level);

}
