package com.example.docs.test;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer>{
    @Query(value = "select id from team where name = :name limit 1", nativeQuery = true)
    Integer findByName(@Param("name") String name);
}
