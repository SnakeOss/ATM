package org.example.model;

import org.example.DatabaseConfig;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private static UserDAO userDAO;

    @BeforeAll
    static void setup() {
        userDAO = new UserDAO();
        // Optionally prepare DB, e.g. clean table or insert test data
    }

    @BeforeEach
    void beforeEach() {
        // Clear users table before each test (optional, depends on your test DB setup)
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM users")) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAddUserAndGetUserById() {
        User user = new User(0, "Test User", "1234-5678", "4321", 500.0, "customer");
        userDAO.addUser(user);

        // Assuming auto-generated ID starts from 1
        User fetchedUser = userDAO.getUserByCardNumber("1234-5678");
        assertNotNull(fetchedUser);
        assertEquals("Test User", fetchedUser.getName());

        User byId = userDAO.getUserById(fetchedUser.getId());
        assertNotNull(byId);
        assertEquals("1234-5678", byId.getCardNumber());
    }

    @Test
    void testUpdateUserBalance() {
        User user = new User(0, "Balance User", "1111-2222", "0000", 300.0, "customer");
        userDAO.addUser(user);

        User fetched = userDAO.getUserByCardNumber("1111-2222");
        assertNotNull(fetched);
        userDAO.updateUserBalance(fetched.getId(), 600.0);

        User updated = userDAO.getUserById(fetched.getId());
        assertEquals(600.0, updated.getBalance());
    }

    @Test
    void testGetAllUsers() {
        userDAO.addUser(new User(0, "User1", "0001", "pin1", 100, "customer"));
        userDAO.addUser(new User(0, "User2", "0002", "pin2", 200, "customer"));

        List<User> allUsers = userDAO.getAllUsers();
        assertTrue(allUsers.size() >= 2);
    }
}
