package com.softwarevax.dateformat.entity;

import lombok.Data;

import java.util.Date;

@Data
public class People {

    private String name;

    private Date birthday;

    private School school;
}
