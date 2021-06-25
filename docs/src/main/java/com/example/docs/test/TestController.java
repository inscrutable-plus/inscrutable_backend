package com.example.docs.test;

import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/add")
    public @ResponseBody String addNewUser(@RequestBody Member body, @RequestParam String pass) {
        if (pass.equals("2020011776")) {
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
}
