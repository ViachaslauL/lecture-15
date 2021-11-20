package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Parent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import javax.xml.transform.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParentDAOImplTests {

    @Mock
    private DataSource dataSource;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    private ParentDAO parentDAO;

    @BeforeEach
    void beforeEach() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        parentDAO = new ParentDAOImpl(dataSource);
    }

    @Test
    void saveTest() throws SQLException {
        Parent parent = new Parent();
        parent.setStudentId(1L);
        parent.setLastName("test");
        parent.setFirstName("test");
        parent.setPatronymic("test");

        preparedStatement.setLong(1, parent.getStudentId());
        preparedStatement.setString(2, parent.getLastName());
        preparedStatement.setString(3, parent.getFirstName());
        preparedStatement.setString(4, parent.getPatronymic());

        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(parent, parentDAO.save(parent));

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void get() {
    }

    @Test
    void getAllPageByPage() {
    }
}