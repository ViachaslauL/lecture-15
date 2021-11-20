package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.StudentDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentDAOImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;
    private Student student;
    private StudentDAO studentDAO;

    @BeforeEach
    void beforeEach() {
        student = new Student();
        student.setClassId(1L);
        student.setLastName("test");
        student.setFirstName("test");
        student.setPatronymic("test");

        studentDAO = new StudentDAOImpl(jdbcTemplate);
    }

    @Test
    void save() {
    }

    @Test
    void get() {
        Long id = 2L;
        Student student = new Student();
        student.setId(id);

        Map<String, Long> sqlParameters = Map.of("id", id);

        when(jdbcTemplate.queryForObject(anyString(),
                eq(sqlParameters),
                any(RowMapper.class)
        )).thenReturn(student);

        Long found = studentDAO.get(2L).getId();

        assertNotNull(found);
        assertEquals(id, found);
    }

    @Test
    void getAllPageByPage() {
    }
}