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
public interface TeamMemberRepository extends CrudRepository<TeamMember, TeamMemberPrimaryKey> {

    @Query(value = "select member.handle from member inner join team_member on team_member.id=member.id where team_member.team_id = :team_id", nativeQuery = true)
    List<Map<String, Object>> getHandlesByTeamId(@Param("team_id") Integer teamId);

    @Query(value = "select * from team_member as tm where tm.id = :id and tm.team_id = team_id", nativeQuery = true)
    List<TeamMember> findByIdAndTeamId(@Param("id") Integer id, @Param("team_id") Integer teamId);

    @Transactional
    @Modifying
    @Query(value = "insert into team_member(id, team_id) values(:id, :team_id)", nativeQuery = true)
    Integer insertRecord(@Param("id") Integer id, @Param("team_id") Integer team_id) throws Exception;
}
