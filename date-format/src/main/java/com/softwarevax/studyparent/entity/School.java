package com.softwarevax.studyparent.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class School {

    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH")
    private Date startTime;

    private Date endTime;
}
