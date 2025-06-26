package org.example.model;

import org.example.DatabaseConfig;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionIntegrationTest {

    @BeforeEach
    public void setupUser() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            // Clean up user with id=1 if exists
            stmt.executeUpdate("DELETE FROM users WHERE id = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertUserSQL = "INSERT INTO users (id, name, card_number, pin, balance, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertUserSQL)) {
            stmt.setInt(1, 1);               // user id
            stmt.setString(2, "testuser");  // name
            stmt.setString(3, "testcard123");// card_number
            stmt.setString(4, "1234");      // pin
            stmt.setBigDecimal(5, new BigDecimal("1000.00")); // balance
            stmt.setString(6, "customer");  // role
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void cleanup() {
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM transactions");
            stmt.executeUpdate("DELETE FROM users WHERE id = 1");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddAndGetTransaction() {
        TransactionDAO dao = new TransactionDAO();
        int userId = 1;  // The user inserted in setupUser

        dao.addTransaction(userId, "deposit", 100.0);
        List<Transaction> transactions = dao.getTransactionsByUserId(userId);

        assertFalse(transactions.isEmpty(), "Transactions list should not be empty");
        Transaction lastTransaction = transactions.get(0);
        assertEquals(userId, lastTransaction.getUserId());
        assertEquals("deposit", lastTransaction.getType());
        assertEquals(100.0, lastTransaction.getAmount(), 0.001);
    }
}
