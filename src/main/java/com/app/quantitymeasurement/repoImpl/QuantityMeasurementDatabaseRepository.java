package com.quantity.measurement.repoImpl;

import com.quantity.measurement.entity.QuantityMeasurementEntity;
import com.quantity.measurement.exception.DatabaseException;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements QuantityMeasurementRepository {
    private final ConnectionPool pool;

    public QuantityMeasurementDatabaseRepository(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void save(QuantityMeasurementEntity e) {
        try (Connection c = pool.acquire()) {
            PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO quantity_measurement_entity(measurement_type, operation, operand, result, timestamp) VALUES (?, ?, ?, ?, ?)"
            );
            ps.setString(1, e.getMeasurementType());
            ps.setString(2, e.getOperation());
            ps.setString(3, e.getOperand());
            ps.setString(4, e.getResult());
            ps.setTimestamp(5, Timestamp.valueOf(e.getTimestamp()));
            ps.executeUpdate();
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Save failed", ex);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT measurement_type, operation, operand, result, timestamp FROM quantity_measurement_entity");
            while (rs.next()) {
                list.add(new QuantityMeasurementEntity(
                        rs.getString("measurement_type"),
                        rs.getString("operation"),
                        rs.getString("operand"),
                        rs.getString("result")
                ));
            }
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Fetch all failed", ex);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        try (Connection c = pool.acquire()) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT measurement_type, operation, operand, result, timestamp FROM quantity_measurement_entity WHERE operation = ?"
            );
            ps.setString(1, operation);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuantityMeasurementEntity(
                        rs.getString("measurement_type"),
                        rs.getString("operation"),
                        rs.getString("operand"),
                        rs.getString("result")
                ));
            }
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Fetch by operation failed", ex);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        List<QuantityMeasurementEntity> list = new ArrayList<>();
        try (Connection c = pool.acquire()) {
            PreparedStatement ps = c.prepareStatement(
                    "SELECT measurement_type, operation, operand, result, timestamp FROM quantity_measurement_entity WHERE measurement_type = ?"
            );
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuantityMeasurementEntity(
                        rs.getString("measurement_type"),
                        rs.getString("operation"),
                        rs.getString("operand"),
                        rs.getString("result")
                ));
            }
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Fetch by type failed", ex);
        }
        return list;
    }

    @Override
    public int getTotalCount() {
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM quantity_measurement_entity");
            if (rs.next()) {
                int count = rs.getInt(1);
                pool.release(c);
                return count;
            }
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Count failed", ex);
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            st.executeUpdate("DELETE FROM quantity_measurement_entity");
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Delete all failed", ex);
        }
    }

    @Override
    public boolean schemaExists() {
        try (Connection c = pool.acquire()) {
            DatabaseMetaData meta = c.getMetaData();
            ResultSet rs = meta.getTables(null, null, "QUANTITY_MEASUREMENT_ENTITY", null);
            boolean exists = rs.next();
            pool.release(c);
            return exists;
        } catch (Exception ex) {
            throw new DatabaseException("Schema check failed", ex);
        }
    }

    @Override
    public void forceError() {
        throw new DatabaseException("Forced error for testing", new RuntimeException());
    }

    public void initializeSchema() {
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            st.executeUpdate("CREATE TABLE IF NOT EXISTS quantity_measurement_entity (" +
                    "id IDENTITY PRIMARY KEY, " +
                    "measurement_type VARCHAR(50), " +
                    "operation VARCHAR(50), " +
                    "operand VARCHAR(255), " +
                    "result VARCHAR(255), " +
                    "timestamp TIMESTAMP)");
            pool.release(c);
        } catch (Exception ex) {
            throw new DatabaseException("Schema initialization failed", ex);
        }
    }
}