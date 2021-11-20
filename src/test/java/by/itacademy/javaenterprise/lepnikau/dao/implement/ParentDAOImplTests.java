package by.itacademy.javaenterprise.lepnikau.dao.implement;

import by.itacademy.javaenterprise.lepnikau.dao.ParentDAO;
import by.itacademy.javaenterprise.lepnikau.entity.Parent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    private Parent parent;

    @BeforeEach
    void beforeEach() {
        parent = new Parent();
        parent.setStudentId(1L);
        parent.setLastName("test");
        parent.setFirstName("test");
        parent.setPatronymic("test");

        parentDAO = new ParentDAOImpl(dataSource);
    }

    @Test
    void saveTest() throws SQLException {

        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertEquals(parent, parentDAO.save(parent));

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void saveTestArgumentIsNull() {

        assertThrows(IllegalArgumentException.class, () -> {
            parentDAO.save(null);
        });

    }

    @Test
    void saveTestWithWrongStudentId() throws SQLException {
        parent.setStudentId(-1L);

        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertNull(parentDAO.save(parent));

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void getTest() throws SQLException {
        Long queryId = 1L;

        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(Boolean.TRUE);

        when(resultSet.getLong(anyString())).thenReturn(queryId);

        assertEquals(queryId, parentDAO.get(queryId).getId());

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
        verify(resultSet, times(2)).getLong(anyString());
    }

    @Test
    void getTestWithArgumentIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            parentDAO.get(null);
        });
    }

    @Test
    void getTestWithWrongId() throws SQLException {
        Long queryId = -1L;

        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(Boolean.FALSE);

        assertNull(parentDAO.get(queryId));

        verify(dataSource, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(1)).next();
    }

    @Test
    void getAllPageByPage() throws SQLException {
        /*int pageSize = 5, pageNumber = 1;

        when(dataSource.getConnection()).thenReturn(connection);

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        when(preparedStatement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(Boolean.TRUE);

        List<Parent> allPageByPage = parentDAO.getAllPageByPage(pageSize, pageNumber);

        assertEquals(pageSize, allPageByPage.size());*/

    }
}