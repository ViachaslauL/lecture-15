package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.entity.Mark;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class MarkDAOImplTest {

    private static MySQLContainer container;
    private static MarkDAOImpl markDAO;

    private Mark mark;

    @BeforeAll
    static void beforeAll() {
        container = new MySQLContainer("mysql:8.0.27");
        container.withDatabaseName("school_journal")
                .withUsername("xxx")
                .withPassword("xxx")
                .start();

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

        markDAO = new MarkDAOImpl(new JdbcTemplate(dataSource));
    }

    @BeforeEach
    void beforeEach() {
        mark = new Mark();
        mark.setSubjectId(1);
        mark.setStudentId(1);
        mark.setMark(10);
        mark.setDate(Date.valueOf(LocalDate.now()));
    }

    @AfterAll
    static void afterAll() {
        container.stop();
        container.close();
    }

    @Test
    void saveTest() {
        Mark actual = markDAO.save(mark);
        assertNotNull(actual);
    }

    @Test
    void saveTestWithWrongStudentId() {
        mark.setStudentId(-1);
        Mark actual = markDAO.save(mark);
        assertNull(actual);

        mark.setStudentId(100);
        actual = markDAO.save(mark);
        assertNull(actual);
    }

    @Test
    void saveTestWithWrongSubjectId() {
        mark.setSubjectId(-1);
        Mark actual = markDAO.save(mark);
        assertNull(actual);

        mark.setSubjectId(100);
        actual = markDAO.save(mark);
        assertNull(actual);
    }

    @Test
    void getTest() {
        Mark actual = markDAO.get(1);
        assertNotNull(actual);
    }

    @Test
    void getTestWithWringId() {
        Mark actual = markDAO.get(-1);
        assertNull(actual);

        actual = markDAO.get(100);
        assertNull(actual);
    }

    @Test
    void getAllPageByPageTest() {
        List<Mark> actual = markDAO.getAllPageByPage(5, 1);
        assertEquals(5, actual.size());
    }

    @Test
    void getAllPageByPageTestWithWrongPageSizeAndPageNumber() {
        List<Mark> actual = markDAO.getAllPageByPage(-1, 1);
        assertEquals(0, actual.size());

        actual = markDAO.getAllPageByPage(5, -1);
        assertEquals(0, actual.size());
    }
}