package com.ssm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ouyangyufeng
 */
@Data
public class Student implements Serializable, Cloneable{

    private static final long serialVersionUID = 1L;

    private Long sid;

    private String sname;

    private Integer sage;

}