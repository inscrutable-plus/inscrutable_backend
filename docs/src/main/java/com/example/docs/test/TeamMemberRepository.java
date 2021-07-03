package com.example.docs.test;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends CrudRepository<TeamMember,TeamMemberPrimaryKey>{

    @Query(value="select member.handle from member inner join team_member on team_member.id=member.id where team_member.team_id = :team_id", nativeQuery = true)
    List<Map<String, Object>> getHandlesByTeamId(@Param("team_id") Integer teamId);
}
