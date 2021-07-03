package com.example.docs.test;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SolveRepository extends CrudRepository<Solve, SolvePrimaryKey> {

    @Query(value = "select problem.* from solve join member on solve.id = member.id join problem on solve.problem_id = problem.problem_id where member.handle = :handle", nativeQuery = true)
    List<Map<String, Object>> findSolvedByHandle(@Param("handle") String handle);

    @Query(value = "select distinct problem.* from solve join team_member on solve.id = team_member.id join problem on solve.problem_id = problem.problem_id where team_member.team_id = :teamId", nativeQuery = true)
    List<Map<String, Object>> findSolvedByTeam(@Param("teamId") Integer teamId);

    @Query(value = "select count(distinct problem_id) from solve where id in (select id from team_member where team_id = :teamId)", nativeQuery = true)
    Integer findCountByTeam(@Param("teamId") Integer teamId);

    @Query(value = "select count(solve.problem_id) from solve join member on solve.id = member.id where member.handle = :handle", nativeQuery = true)
    Integer findCountByHandle(@Param("handle") String handle);

    @Query(value = "select * from solve where id = :id and problem_id = :problem_id", nativeQuery = true)
    List<Map<String, Object>> findByIds(@Param("id") Integer id, @Param("problem_id") Integer problemId);

    @Transactional
    @Modifying
    @Query(value = "insert into solve(problem_id, id) values(:problem_id, :id)", nativeQuery = true)
    Integer insertRecord(@Param("problem_id") Integer problemId, @Param("id") Integer id) throws Exception;
}
