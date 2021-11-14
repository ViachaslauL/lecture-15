package by.itacademy.javaenterprise.lepnikau.app.dao.implement;

import by.itacademy.javaenterprise.lepnikau.app.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.app.entity.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParentDAOImpl implements ParentDAO {

    private static final Logger logger = LoggerFactory.getLogger(ParentDAOImpl.class);
    private static final String INSERT_INTO = "INSERT INTO `parents` (`student_id`,`last_name`,`first_name`,`patronymic`) " +
            "VALUE (:studentId, :lastName, :firstName, :patronymic)";
    private static final String SELECT_BY_ID = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
            "WHERE `id` = :id";
    private static final String SELECT_ALL = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
            "ORDER BY `last_name` LIMIT :pageSize OFFSET :offset";

    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public ParentDAOImpl(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    @Override
    public Parent save(Parent parent) {
        try {
            namedJdbcTemplate.update(INSERT_INTO, new MapSqlParameterSource()
                    .addValue("studentId", parent.getStudentId())
                    .addValue("lastName", parent.getLastName())
                    .addValue("firstName", parent.getFirstName())
                    .addValue("patronymic", parent.getPatronymic())
            );
            return parent;
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public Parent get(int id) {
        try {
            return namedJdbcTemplate.queryForObject(SELECT_BY_ID, new MapSqlParameterSource()
                            .addValue("id", id),
                    new BeanPropertyRowMapper<>(Parent.class));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Parent> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);

        try {
            return namedJdbcTemplate.query(SELECT_ALL, new MapSqlParameterSource()
                            .addValue("pageSize", pageSize)
                            .addValue("offset", offset),
                    new BeanPropertyRowMapper<>(Parent.class));
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
