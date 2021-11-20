package by.itacademy.javaenterprise.lepnikau.entity;

import lombok.Data;

@Data
public class Parent{
    private Long id;
    private Long studentId;
    private String lastName;
    private String firstName;
    private String patronymic;
}
