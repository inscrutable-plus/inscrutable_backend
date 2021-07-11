package com.example.docs.test;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RankingRepository extends CrudRepository<Ranking, RankingPrimaryKey> {
    @Query(value = "select * from ranking", nativeQuery = true)
    List<Map<String, Object>> findAllList();

    @Query(value = "select * from ranking where week_id = :week_id and id = :id", nativeQuery = true)
    List<Map<String, Object>> findByPKList(@Param("week_id") Integer week_id, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query(value = "insert into ranking ( week_id, id, score ) values (:week_id, :id, :score)", nativeQuery = true)
    Integer insertRecord(@Param("week_id") Integer week_id, @Param("id") Integer id, @Param("score") Integer score);

    @Transactional
    @Modifying
    @Query(value = "update ranking set score = :score where week_id = :week_id and id = :id", nativeQuery = true)
    Integer updateRecord(@Param("week_id") Integer week_id, @Param("id") Integer id, @Param("score") Integer score);
}
