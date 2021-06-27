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

}
