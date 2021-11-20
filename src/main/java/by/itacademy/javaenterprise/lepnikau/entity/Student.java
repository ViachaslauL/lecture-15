package by.itacademy.javaenterprise.lepnikau.entity;

import lombok.Data;

@Data
public class Student{
    private Long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private Long classId;
}
