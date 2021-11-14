package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.app.entity.Parent;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class ParentDAOImplTests {

    private static MySQLContainer container;
    private static ParentDAO parentDAO;

    private Parent parent;

    @BeforeAll
    static void beforeAll() {
        container = new MySQLContainer("mysql:8.0.27");
        container.withDatabaseName("school_journal");
        container.withUsername("xxx");
        container.withPassword("xxx");
        container.start();

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUsername(container.getUsername());
        dataSource.setPassword(container.getPassword());

        Flyway flyway = Flyway.configure()
                .locations("classpath:/db/migrate")
                .dataSource(dataSource)
                .load();
        flyway.migrate();

        parentDAO = new ParentDAOImpl(new NamedParameterJdbcTemplate(dataSource));
    }

    @BeforeEach
    void beforeEach() {
        parent = new Parent();
        parent.setLastName("test");
        parent.setFirstName("test");
        parent.setPatronymic("test");
    }

    @AfterAll
    static void afterAll() {
        container.stop();
        container.close();
    }

    @Test
    void saveTest() {
        parent.setStudentId(1);
        Parent actual = parentDAO.save(parent);
        assertNotNull(actual);
        assertEquals(parent, actual);
    }

    @Test
    void saveTestWithWrongStudentId() {
        parent.setStudentId(-1);
        Parent actual = parentDAO.save(parent);
        assertNull(actual);

        parent.setStudentId(100);
        actual = parentDAO.save(parent);
        assertNull(actual);
    }

    @Test
    void getTest() {
        Parent actual = parentDAO.get(1);
        assertNotNull(actual);
    }

    @Test
    void getTestWithWrongId() {
        Parent actual = parentDAO.get(-1);
        assertNull(actual);

        actual = parentDAO.get(100);
        assertNull(actual);
    }

    @Test
    void getAllPageByPageTest() {
        List<Parent> actual = parentDAO.getAllPageByPage(5, 1);
        assertEquals(5, actual.size());
        for (Parent p: actual) {
            assertNotNull(p);
        }
    }

    @Test
    void getAllPageByPageTestWithWrongPageSizeAndPageNumber() {
        List<Parent> actual = parentDAO.getAllPageByPage(-1, 1);
        assertEquals(0, actual.size());

        actual = parentDAO.getAllPageByPage(5, 0);
        assertEquals(0, actual.size());

        actual = parentDAO.getAllPageByPage(20, 0);
        assertEquals(0, actual.size());
    }
}