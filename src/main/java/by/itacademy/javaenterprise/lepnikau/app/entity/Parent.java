package by.itacademy.javaenterprise.lepnikau.app.entity;

import lombok.Data;

@Data
public class Parent{
    private long id;
    private long studentId;
    private String lastName;
    private String firstName;
    private String patronymic;
}
