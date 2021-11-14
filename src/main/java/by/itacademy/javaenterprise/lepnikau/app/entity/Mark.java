package by.itacademy.javaenterprise.lepnikau.app.entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Mark{
    private long id;
    private long studentId;
    private int mark;
    private long subjectId;
    private Date date;
}
