package com.softwarevax.studyparent.controller;

import com.softwarevax.studyparent.entity.People;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @RequestMapping("/list")
    public People list(@RequestBody People people) {
        return people;
    }
}
