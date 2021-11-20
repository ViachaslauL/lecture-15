package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Parent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ParentDAOImpl implements ParentDAO {

    private static final Logger logger = LoggerFactory.getLogger(ParentDAOImpl.class);
    private static final String INSERT_INTO = "INSERT INTO `parents` (`student_id`,`last_name`,`first_name`,`patronymic`) " +
            "VALUE (?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
            "WHERE `id` = ?";
    private static final String SELECT_ALL = "SELECT `id`, `student_id`, `last_name`, `first_name`, `patronymic` FROM `parents` " +
            "ORDER BY `last_name` LIMIT ? OFFSET ?";

    private final DataSource dataSource;

    @Autowired
    public ParentDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Parent save(Parent parent) {
        if (parent == null) throw new IllegalArgumentException();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {

            preparedStatement.setLong(1, parent.getStudentId());
            preparedStatement.setString(2, parent.getLastName());
            preparedStatement.setString(3, parent.getFirstName());
            preparedStatement.setString(4, parent.getPatronymic());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                return parent;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public Parent get(Long id) {
        if (id == null) throw new IllegalArgumentException();
        Parent parent = null;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                parent = new Parent();
                parent.setId(resultSet.getLong("id"));
                parent.setStudentId(resultSet.getLong("student_id"));
                parent.setLastName(resultSet.getString("last_name"));
                parent.setFirstName(resultSet.getString("first_name"));
                parent.setPatronymic(resultSet.getString("patronymic"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return parent;
    }

    @Override
    public List<Parent> getAllPageByPage(int pageSize, int pageNumber) {
        int offset = pageSize * (pageNumber - 1);
        List<Parent> parents = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL)) {

            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Parent parent = new Parent();
                parent.setId(resultSet.getLong("id"));
                parent.setStudentId(resultSet.getLong("student_id"));
                parent.setLastName(resultSet.getString("last_name"));
                parent.setFirstName(resultSet.getString("first_name"));
                parent.setPatronymic(resultSet.getString("patronymic"));
                parents.add(parent);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return parents;
    }
}
