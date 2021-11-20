package by.itacademy.javaenterprise.lepnikau;

import by.itacademy.javaenterprise.lepnikau.config.SchoolDiaryConfig;
import by.itacademy.javaenterprise.lepnikau.dao.implement.ParentDAOImpl;
import by.itacademy.javaenterprise.lepnikau.entity.Parent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SchoolDiaryConfig.class);
        ParentDAOImpl parentDAO = context.getBean(ParentDAOImpl.class);

        Parent parent = new Parent();
        parent.setStudentId(-1L);
        parent.setLastName("test");
        parent.setFirstName("test");
        parent.setPatronymic("test");

        parentDAO.save(parent);
    }
}
