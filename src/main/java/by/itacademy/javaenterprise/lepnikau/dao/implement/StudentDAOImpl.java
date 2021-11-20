package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.StudentDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class StudentDAOImpl implements StudentDAO {

    private static final Logger logger = LoggerFactory.getLogger(StudentDAOImpl.class);
    private static final String INSERT_INTO = "INSERT INTO `students` (`last_name`,`first_name`,`patronymic`,`class_id`) " +
            "VALUE (:lastName, :firstName, :patronymic, :classId)";
    private static final String SELECT_BY_ID = "SELECT `id`, `last_name`, `first_name`, `patronymic`, `class_id` FROM `students` " +
            "WHERE `id` = :id ORDER BY `last_name`";
    private static final String SELECT_ALL = "SELECT `id`, `last_name`, `first_name`, `patronymic`, `class_id` FROM `students` " +
            "ORDER BY `last_name` LIMIT :pageSize OFFSET :offset";

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public StudentDAOImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Student save(Student student) {
        if (student == null) throw new IllegalArgumentException();

        try {
            int update = namedJdbcTemplate.update(INSERT_INTO, new MapSqlParameterSource()
                    .addValue("lastName", student.getLastName())
                    .addValue("firstName", student.getFirstName())
                    .addValue("patronymic", student.getPatronymic())
                    .addValue("classId", student.getClassId())
            );
            if (update > 0) return student;
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Student get(Long id) {
        if (id == null || id <= 0) throw new IllegalArgumentException();

        try {
            return namedJdbcTemplate.queryForObject(SELECT_BY_ID,
                    Collections.singletonMap("id", id),
                    new BeanPropertyRowMapper<>(Student.class)
            );
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public List<Student> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);

        try {
            return namedJdbcTemplate.query(SELECT_ALL, new MapSqlParameterSource()
                            .addValue("pageSize", pageSize)
                            .addValue("offset", offset),
                    new BeanPropertyRowMapper<>(Student.class));
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
