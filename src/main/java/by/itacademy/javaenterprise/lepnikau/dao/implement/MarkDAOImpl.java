package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.MarkDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Mark;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MarkDAOImpl implements MarkDAO {

    private static final Logger logger = LoggerFactory.getLogger(MarkDAOImpl.class);
    private static final String INSERT_INTO = "INSERT INTO `marks` (`student_id`,`mark`,`subject_id`,`date`) " +
            "VALUES (?,?,?,?)";
    private static final String SELECT_BY_ID = "SELECT `id`, `student_id`, `mark`, `subject_id`, `date` " +
            "FROM `marks` WHERE `id` = ?";
    private static final String SELECT_ALL = "SELECT `id`, `student_id`, `mark`, `subject_id`, `date` " +
            "FROM `marks` ORDER BY `date` LIMIT ? OFFSET ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MarkDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Mark save(Mark mark) {
        try {
            jdbcTemplate.update(INSERT_INTO,
                    mark.getStudentId(),
                    mark.getMark(),
                    mark.getSubjectId(),
                    mark.getDate()
            );
            return mark;
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Mark get(Long id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_BY_ID,
                    new BeanPropertyRowMapper<>(Mark.class), id);

        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Mark> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        try {
            return jdbcTemplate.query(SELECT_ALL,
                    new BeanPropertyRowMapper<>(Mark.class), pageSize, offset);
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return new ArrayList<>();
    }
}
