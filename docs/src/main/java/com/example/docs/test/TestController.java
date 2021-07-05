package com.example.docs.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings(value = "unchecked")
@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolveRepository solveRepository;
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Value("${secure.key}")
    private Integer key;
    @Value("${urls.search}")
    private String search;

    @PostMapping("/add")
    public @ResponseBody String addNewUser(@RequestBody Iterable<Member> body, @RequestParam Integer pass) {

        if (pass.intValue() == key.intValue()) {
            return memberRepository.saveAll(body).toString();
        }
        return "Unathorized";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Member> userList() {
        return memberRepository.findAll();
    }

    // @GetMapping("/team")
    // public Iterable<Member> userList(@RequestParam Integer team) {
    // return memberRepository.findByTeam(team);
    // }

    @GetMapping("/problem/count")
    public Map<String, Object> solvedCountTeam(@RequestParam(required = false) Integer team,
            @RequestParam(required = false) String handle) {
        // TODO if team and handle is both not null
        if (team != null)
            return ResultHandler.formatResult(solveRepository.findCountByTeam(team));
        if (handle != null)
            return ResultHandler.formatResult(solveRepository.findCountByHandle(handle));
        return ResultHandler.formatResult(0);
    }

    @GetMapping("/problem/solved")
    public Map<String, Object> solvedSolvedTeam(@RequestParam(required = false) Integer team,
            @RequestParam(required = false) String handle, @RequestParam(required = false) Integer tier) {
        // TODO if team and handle is both not null
        if (tier == null) {
            if (team != null)
                return ResultHandler.formatResult(solveRepository.findSolvedByTeam(team));
            if (handle != null)
                return ResultHandler.formatResult(solveRepository.findSolvedByHandle(handle));
        } else {
            if (team != null)
                return ResultHandler.formatResult(solveRepository.findSolvedByTeamByTier(team, tier));
            if (handle != null)
                return ResultHandler.formatResult(solveRepository.findSolvedByHandleByTier(handle, tier));
        }
        return ResultHandler.formatResult(null);
    }

    @PostMapping(value = "/updatebyhandle")
    public Map<String, Object> updateByHandle(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> errors = new ArrayList<>();

        if (!body.containsKey("key") || !body.get("key").toString().equals(key.toString())) {
            return ResultHandler.formatResult("key failed", false);
        }

        int solvePage = 1;
        int solveCount = 0;
        if (!body.containsKey("handle")) {
            // result.put("error", "no handle provided");
            return ResultHandler.formatResult(result, false);
        }

        String handle = body.get("handle").toString();
        List<Integer> ids = memberRepository.findByHandle(handle);
        if (ids.size() == 0) {
            return ResultHandler.formatResult("no user with such handle exist", false);
        }
        Integer id = ids.get(0);

        JSONObject res;
        do {
            res = RestAPICaller
                    .restCall(search + "solved_by:" + handle + "&sort=id&sort_direction=asc&page=" + solvePage);

            List<Problem> problems = DataParser.parseProblems(res);
            for (Problem item : problems) {
                Solve s = new Solve(item.getProblemId(), id, new Timestamp(System.currentTimeMillis()));
                try {
                    if (solveRepository.findByIds(s.getId(), s.getProblemId()).size() == 0)
                        solveRepository.insertRecord(s.getProblemId(), s.getId(),
                                new Timestamp(System.currentTimeMillis()));
                } catch (Exception e) {
                    errors.add(s.getProblemId());
                }
            }
            solveCount += ((JSONArray) res.get("items")).size();
            solvePage++;
        } while (solveCount < Integer.parseInt(res.get("count").toString()));

        result.put("errors", errors);

        return ResultHandler.formatResult(result);
    }

    @PostMapping(value = "/updateproblemsbytier")
    public Map<String, Object> updateProblemsByTier(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> errors = new ArrayList<>();

        if (!body.containsKey("key") || !body.get("key").toString().equals(key.toString())) {
            return ResultHandler.formatResult("key failed", false);
        }

        int page = 1;
        int count = 0;
        if (!body.containsKey("tier")) {
            // result.put("error", "no tier provided");
            return ResultHandler.formatResult(result, false);
        }
        Integer tier = Integer.parseInt(body.get("tier").toString());

        JSONObject res;
        do {
            res = RestAPICaller.restCall(search + "tier:" + tier + "&sort=id&sort_direction=asc&page=" + page);

            if (res == null) {
                return ResultHandler.formatResult("error, please try again later", false);
            }

            List<Problem> problems = DataParser.parseProblems(res);
            for (Problem item : problems) {
                try {
                    problemRepository.save(item);
                } catch (Exception e) {
                    errors.add(item.getProblemId());
                }
            }
            count += ((JSONArray) res.get("items")).size();
            page++;
        } while (count < Integer.parseInt(res.get("count").toString()));

        result.put("errors", errors);

        return ResultHandler.formatResult(result);

    }

    @PostMapping(value = "/updatebytier")
    public Map<String, Object> updateByTier(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> errors = new ArrayList<>();

        if (!body.containsKey("key") || !body.get("key").toString().equals(key.toString())) {
            return ResultHandler.formatResult("key failed", false);
        }

        int solvePage = 1;
        int solveCount = 0;
        if (!body.containsKey("tier")) {
            // result.put("error", "no tier provided");
            return ResultHandler.formatResult(result, false);
        }
        Integer tier = Integer.parseInt(body.get("tier").toString());

        Iterable<Member> members = memberRepository.findAll();
        for (Member m : members) {

            String handle = m.getHandle();
            List<Integer> ids = memberRepository.findByHandle(handle);
            if (ids.size() == 0) {
                return ResultHandler.formatResult("no user with such handle exist", false);
            }
            Integer id = ids.get(0);

            JSONObject res;
            do {
                res = RestAPICaller.restCall(search + "solved_by:" + handle + "%20tier:" + tier
                        + "&sort=id&sort_direction=asc&page=" + solvePage);

                List<Problem> problems = DataParser.parseProblems(res);
                for (Problem item : problems) {
                    Solve s = new Solve(item.getProblemId(), id, new Timestamp(System.currentTimeMillis()));
                    try {
                        if (solveRepository.findByIds(s.getId(), s.getProblemId()).size() == 0)
                            solveRepository.insertRecord(s.getProblemId(), s.getId(),
                                    new Timestamp(System.currentTimeMillis()));
                    } catch (Exception e) {
                        errors.add(s.getProblemId());
                    }
                }
                solveCount += ((JSONArray) res.get("items")).size();
                solvePage++;
            } while (solveCount < Integer.parseInt(res.get("count").toString()));

            result.put("errors", errors);
        }

        return ResultHandler.formatResult(result);
    }

    @GetMapping("/teamstatistics")
    public Map<String, Object> getRankByTeamId(@RequestParam("team") Integer team) {
        Map<String, Object> res = new HashMap<>();

        for (int i = 0; i <= 30; i++) {
            Map<String, Object> subResult = new HashMap<>();
            Integer solved = problemRepository.countSolvedByTeamId(i, team);
            Integer all = problemRepository.countByLevel(i);
            subResult.put("todo", all - solved);
            subResult.put("solved", solved);
            subResult.put("all", all);
            subResult.put("progress", Double.parseDouble(Integer.toString(solved)) / all);
            res.put(Integer.toString(i), subResult);
        }

        return ResultHandler.formatResult(res);
    }

    @PostMapping("add/team")
    public Map<String, Object> addToTeam(@RequestBody Map<String, Object> body) {
        if (!body.containsKey("handle") || !body.containsKey("team")) {
            return ResultHandler.formatResult("invalid member", false);
        }

        if (!body.containsKey("key") || !body.get("key").toString().equals(key.toString())) {
            return ResultHandler.formatResult("key failed", false);
        }

        String handle = body.get("handle").toString();
        String team = body.get("team").toString();

        List<Integer> teamIds = teamRepository.findByName(team);
        if (teamIds.size() == 0) {
            return ResultHandler.formatResult("no team with such name exist", false);
        }
        Integer teamId = teamIds.get(0);
        List<Integer> ids = memberRepository.findByHandle(handle);
        if (ids.size() == 0) {
            return ResultHandler.formatResult("no user with such handle exist", false);
        }
        Integer id = ids.get(0);

        if (teamMemberRepository.findByIdAndTeamId(id, teamId).size() > 0) {
            return ResultHandler.formatResult("user already in the team", false);
        }
        try {
            teamMemberRepository.insertRecord(id, teamId);
        } catch (Exception e) {
            return ResultHandler.formatResult("failed to add\n\n" + e.getStackTrace(), false);
        }

        return ResultHandler.formatResult("success");
    }

    @GetMapping("/problem/unsolved")
    public Map<String, Object> solvedUnsolvedTeam(@RequestParam(required = false) Integer team,
            @RequestParam(required = false) String handle, @RequestParam Integer tier) {
        // TODO if team and handle is both not null
        if (team != null)
            return ResultHandler.formatResult(solveRepository.findNotSolvedByTeamByTier(team, tier));
        if (handle != null)
            return ResultHandler.formatResult(solveRepository.findNotSolvedByHandleByTier(handle, tier));

        return ResultHandler.formatResult(null);
    }

}
