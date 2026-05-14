package com.quantity.measurement.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class ConnectionPool {
    private final String url;
    private final String user;
    private final String password;
    private final int maxSize;
    private final Deque<Connection> pool = new ArrayDeque<>();

    public ConnectionPool(String url, String user, String password, int maxSize) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxSize = maxSize;
    }

    public synchronized Connection acquire() throws SQLException {
        if (!pool.isEmpty()) return pool.pop();
        if (pool.size() < maxSize) return DriverManager.getConnection(url, user, password);
        throw new SQLException("No available connections");
    }

    public synchronized void release(Connection conn) {
        if (conn != null) pool.push(conn);
    }

    public synchronized void closeAll() throws SQLException {
        for (Connection c : pool) c.close();
        pool.clear();
    }

    public boolean allConnectionsClosed() { return pool.isEmpty(); }

    public String getStatistics() {
        return "Pool size: " + pool.size() + "/" + maxSize;
    }
}