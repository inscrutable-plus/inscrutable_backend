package com.example.docs.test;

import java.sql.Timestamp;
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

    @Query(value = "select problem.* from solve join member on solve.id = member.id join problem on solve.problem_id = problem.problem_id where member.handle = :handle and problem.level = :tier", nativeQuery = true)
    List<Map<String, Object>> findSolvedByHandleByTier(@Param("handle") String handle, @Param("tier") Integer tier);

    @Query(value = "select pro.* from problem as pro where pro.problem_id not in (select problem.problem_id from solve join member on solve.id = member.id join problem on solve.problem_id = problem.problem_id where member.handle = :handle)", nativeQuery = true)
    List<Map<String, Object>> findNotSolvedByHandle(@Param("handle") String handle);

    @Query(value = "select pro.* from problem as pro where pro.problem_id not in (select problem.problem_id from solve join member on solve.id = member.id join problem on solve.problem_id = problem.problem_id where member.handle = :handle) and pro.level = :tier", nativeQuery = true)
    List<Map<String, Object>> findNotSolvedByHandleByTier(@Param("handle") String handle, @Param("tier") Integer tier);

    @Query(value = "select distinct problem.* from solve join team_member on solve.id = team_member.id join problem on solve.problem_id = problem.problem_id where team_member.team_id = :teamId", nativeQuery = true)
    List<Map<String, Object>> findSolvedByTeam(@Param("teamId") Integer teamId);

    @Query(value = "select distinct problem.* from solve join team_member on solve.id = team_member.id join problem on solve.problem_id = problem.problem_id where team_member.team_id = :teamId and problem.level = :tier", nativeQuery = true)
    List<Map<String, Object>> findSolvedByTeamByTier(@Param("teamId") Integer teamId, @Param("tier") Integer tier);

    @Query(value = "select distinct pro.* from problem as pro where pro.problem_id not in (select problem.problem_id from solve join team_member on solve.id = team_member.id join problem on solve.problem_id = problem.problem_id where team_member.team_id = :teamId)", nativeQuery = true)
    List<Map<String, Object>> findNotSolvedByTeam(@Param("teamId") Integer teamId);

    @Query(value = "select distinct pro.* from problem as pro where pro.problem_id not in (select problem.problem_id from solve join team_member on solve.id = team_member.id join problem on solve.problem_id = problem.problem_id where team_member.team_id = :teamId) and pro.level = :tier", nativeQuery = true)
    List<Map<String, Object>> findNotSolvedByTeamByTier(@Param("teamId") Integer teamId, @Param("tier") Integer tier);

    @Query(value = "select count(distinct problem_id) from solve where id in (select id from team_member where team_id = :teamId)", nativeQuery = true)
    Integer findCountByTeam(@Param("teamId") Integer teamId);

    @Query(value = "select count(solve.problem_id) from solve join member on solve.id = member.id where member.handle = :handle", nativeQuery = true)
    Integer findCountByHandle(@Param("handle") String handle);

    @Query(value = "select * from solve where id = :id and problem_id = :problem_id", nativeQuery = true)
    List<Map<String, Object>> findByIds(@Param("id") Integer id, @Param("problem_id") Integer problemId);

    @Query(value = "select member.handle, solve.problem_id, problem.title, problem.level, solve.solve_date from solve join member on member.id = solve.id join problem on problem.problem_id = solve.problem_id where member.handle = :handle", nativeQuery = true)
    List<Map<String, Object>> findSolveListByHandle(@Param("handle") String handle);

    @Query(value = "select problem.title, solve.* from solve join problem on solve.problem_id = problem.problem_id where solve.id = :id order by solve.solve_date asc", nativeQuery = true)
    List<Map<String, Object>> findSolveListById(@Param("id") Integer id);

    @Query(value = "select problem.title, problem.level, solve.* from solve join problem on solve.problem_id = problem.problem_id where solve.id = :id and solve.solve_date >= :start and solve.solve_date < :end order by solve.solve_date asc", nativeQuery = true)
    List<Map<String, Object>> findSolveListByIdAndDate(@Param("id") Integer id, @Param("start") Timestamp start, @Param("end") Timestamp end);

    @Query(value =  "select solve.id, sum(problem.level) as score from solve join problem on solve.problem_id = problem.problem_id where solve.solve_date >= :start and solve.solve_date < :end group by solve.id", nativeQuery = true)
    List<Map<String, Object>> findScoresByTime(@Param("start") Timestamp start, @Param("end") Timestamp end);

    @Transactional
    @Modifying
    @Query(value = "insert into solve(problem_id, id, solve_date) values(:problem_id, :id, :solve_date)", nativeQuery = true)
    Integer insertRecord(@Param("problem_id") Integer problemId, @Param("id") Integer id, @Param("solve_date") Timestamp solveDate) throws Exception;
}
