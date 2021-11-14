package by.itacademy.javaenterprise.lepnikau.app.entity;

import lombok.Data;

@Data
public class Student{
    private long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private long classId;
}
