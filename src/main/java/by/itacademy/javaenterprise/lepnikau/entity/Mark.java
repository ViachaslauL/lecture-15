package by.itacademy.javaenterprise.lepnikau.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Mark{
    private Long id;
    private Long studentId;
    private int mark;
    private long subjectId;
    private Date date;
}
