package com.example.docs.test;

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

@RestController
@RequestMapping("/api")
public class TestController {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolveRepository solveRepository;

    @Value("${secure.key}")
    private Integer key;
    @Value("${urls.level}")
    private String level;

    @PostMapping("/add")
    public @ResponseBody String addNewUser(@RequestBody Member body, @RequestParam Integer pass) {

        if (pass.intValue() == key.intValue()) {
            // return body.getHandle();
            memberRepository.save(body);
            return "Done!";
        }
        return "Unathorized";
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Member> userList() {
        return memberRepository.findAll();
    }

    @GetMapping("/team")
    public Iterable<Member> userList(@RequestParam Integer team) {
        return memberRepository.findByTeam(team);
    }

    @GetMapping("/problem/count")
    public Integer solvedCountTeam(@RequestParam(required = false) Integer team,
            @RequestParam(required = false) String handle) {

        // TODO if team and handle is both not null
        if (team != null)
            return solveRepository.findCountByTeam(team);
        if (handle != null)
            return solveRepository.findCountByHandle(handle);
        return 0;
    }

    @GetMapping("/problem/solved")
    public Iterable<Solve> solvedSolvedTeam(@RequestParam(required = false) Integer team,
            @RequestParam(required = false) String handle) {

        // TODO if team and handle is both not null
        if (team != null)
            return solveRepository.findSolvedByTeam(team);
        if (handle != null)
            return solveRepository.findSolvedByHandle(handle);
        return null;
    }

    // @GetMapping("/level/organization")
    // public Iterable<Level> levelStats(@RequestParam Integer organizationId) {
    // Iterable<Member> arr = memberRepository.findByTeam(organizationId);
    /*
     * TODO : search by team
     * 
     */
    // }

    @GetMapping("/level/individual")
    public Level levelStats(@RequestParam String handle, @RequestParam Integer tier) {
        JSONObject res;
        String url = level;
        Level result = new Level();
        if (tier < 0 || tier > 30)
            return result;
        result.setLevel(tier);

        res = RestAPICaller.restCall(url + "tier:" + tier);
        Integer count = Integer.parseInt(res.get("count").toString());
        result.setCount(count);

        res = RestAPICaller.restCall(url + "tier:" + tier + "%20solved_by:" + handle);
        Integer solved = Integer.parseInt(res.get("count").toString());
        result.setSolved(solved);
        result.setUnsolved(count - solved);
        result.setPercentage(solved * 1.0 / count);

        return result;
    }
}
