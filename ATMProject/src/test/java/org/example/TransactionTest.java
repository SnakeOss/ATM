package org.example.model;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testConstructorWithId() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(1, 100, "Deposit", 500.0, timestamp);

        assertEquals(1, transaction.getId());
        assertEquals(100, transaction.getUserId());
        assertEquals("Deposit", transaction.getType());
        assertEquals(500.0, transaction.getAmount());
        assertEquals(timestamp, transaction.getTimestamp());
    }

    @Test
    public void testConstructorWithoutId() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Transaction transaction = new Transaction(100, "Withdrawal", 250.0, timestamp);

        // No id set in this constructor, so id should be default 0
        assertEquals(0, transaction.getId());
        assertEquals(100, transaction.getUserId());
        assertEquals("Withdrawal", transaction.getType());
        assertEquals(250.0, transaction.getAmount());
        assertEquals(timestamp, transaction.getTimestamp());
    }
}
