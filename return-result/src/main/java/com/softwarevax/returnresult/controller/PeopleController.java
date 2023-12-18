package com.softwarevax.returnresult.controller;

import com.softwarevax.returnresult.config.IgnoreWrap;
import com.softwarevax.returnresult.config.ResultDto;
import com.softwarevax.returnresult.entity.People;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @RequestMapping("/json")
    public People list(@RequestBody People people) {
        return people;
    }

    @RequestMapping("/string")
    public String string(@RequestBody People people) {
        return people.getName();
    }

    @RequestMapping("/error")
    public int error(@RequestBody People people) {
        return 1 / 0;
    }

    @RequestMapping("/dto")
    public ResultDto<People> dto(@RequestBody People people) {
        return ResultDto.successT(people);
    }

    @RequestMapping("/map")
    public Map<String, Object> map(@RequestBody People people) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", people.getName());
        map.put("birthday", people.getBirthday());
        return map;
    }

    @RequestMapping("/exception")
    public int exception(@RequestBody People people) {
        Assert.isTrue(1 == 0, "自定义异常");
        return 1;
    }

    @IgnoreWrap
    @RequestMapping("/ignore")
    public String ignore(@RequestBody People people) {
        return "没有任何外围包装";
    }
}
