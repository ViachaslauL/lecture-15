package by.itacademy.javaenterprise.lepnikau;

import by.itacademy.javaenterprise.lepnikau.config.SchoolDiaryConfig;
import by.itacademy.javaenterprise.lepnikau.dao.implement.ParentDAOImpl;
import by.itacademy.javaenterprise.lepnikau.dao.implement.StudentDAOImpl;
import by.itacademy.javaenterprise.lepnikau.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SchoolDiaryConfig.class);
        StudentDAOImpl studentDAO = context.getBean(StudentDAOImpl.class);

        Student student = studentDAO.get(100L);
        if (student == null) {
            LOG.info("null");
        } else {
            LOG.info(student.toString());
        }
    }
}
