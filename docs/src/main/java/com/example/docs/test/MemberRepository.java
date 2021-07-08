package com.example.docs.test;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {

    // @Query(value = "select * from member where team = :team", nativeQuery = true)
    // List<Member> findByTeam(@Param("team") Integer team);

    @Query(value = "select * from member", nativeQuery = true)
    List<Member> findAll();
    
    @Query(value = "select member.handle, member.rating, member.solved_class, count(distinct solve.problem_id), member.rank as solve_count from member join solve on solve.id = member.id group by member.id", nativeQuery = true)
    List<Map<String, Object>> findAllWithSolveCount();

    @Query(value = "select member.id from member where handle = :handle limit 1", nativeQuery = true)
    List<Integer> findByHandle(@Param("handle") String handle);
}
