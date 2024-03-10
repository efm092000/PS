package tests;

import com.dailyfit.user.User;
import com.dailyfit.user.UserDao;
import com.dailyfit.user.UserDaoImpl;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTests {
    private final Connection connection = mock(Connection.class);

    @Test
    public void test_read_user_given_real_email_should_return_actual_user() throws SQLException {
        String email = "real@email.com";
        String actualPassword = "actualPassword";
        String actualName = "actualName";
        User expectedUser = new User(email, actualPassword, actualName);

        UserDao userDao = new UserDaoImpl(connection);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(connection.createStatement()).thenReturn(mock(java.sql.Statement.class));
        when(connection.createStatement().executeQuery(anyString())).thenReturn(mockResultSet);
        when(mockResultSet.getString("email")).thenReturn(email);
        when(mockResultSet.getString("password")).thenReturn(actualPassword);
        when(mockResultSet.getString("name")).thenReturn(actualName);

        assertThat(userDao.readUser(email).orElse(new User("", "", ""))).isEqualTo(expectedUser);
    }
}
