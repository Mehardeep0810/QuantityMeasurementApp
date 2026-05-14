package com.app.quantitymeasurement.repoImpl;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.database.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuantityMeasurementDatabaseRepository implements QuantityMeasurementRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private final ConnectionPool pool;

    public QuantityMeasurementDatabaseRepository(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void save(QuantityMeasurementEntity e) {
        LOGGER.debug("Saving entity to database: {}", e);
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
            LOGGER.info("Entity saved successfully: {}", e);
        } catch (Exception ex) {
            LOGGER.error("Save failed for entity {}", e, ex);
            throw new DatabaseException("Save failed", ex);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        LOGGER.debug("Fetching all measurements from database");
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
            LOGGER.info("Fetched {} measurements from database", list.size());
        } catch (Exception ex) {
            LOGGER.error("Fetch all failed", ex);
            throw new DatabaseException("Fetch all failed", ex);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        LOGGER.debug("Fetching measurements by operation: {}", operation);
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
            LOGGER.info("Fetched {} measurements for operation {}", list.size(), operation);
        } catch (Exception ex) {
            LOGGER.error("Fetch by operation failed for {}", operation, ex);
            throw new DatabaseException("Fetch by operation failed", ex);
        }
        return list;
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        LOGGER.debug("Fetching measurements by type: {}", type);
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
            LOGGER.info("Fetched {} measurements for type {}", list.size(), type);
        } catch (Exception ex) {
            LOGGER.error("Fetch by type failed for {}", type, ex);
            throw new DatabaseException("Fetch by type failed", ex);
        }
        return list;
    }

    @Override
    public int getTotalCount() {
        LOGGER.debug("Fetching total count of measurements");
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM quantity_measurement_entity");
            if (rs.next()) {
                int count = rs.getInt(1);
                pool.release(c);
                LOGGER.info("Total count of measurements: {}", count);
                return count;
            }
            pool.release(c);
        } catch (Exception ex) {
            LOGGER.error("Count failed", ex);
            throw new DatabaseException("Count failed", ex);
        }
        return 0;
    }

    @Override
    public void deleteAll() {
        LOGGER.warn("Deleting all measurements from database");
        try (Connection c = pool.acquire()) {
            Statement st = c.createStatement();
            st.executeUpdate("DELETE FROM quantity_measurement_entity");
            pool.release(c);
            LOGGER.info("All measurements deleted successfully");
        } catch (Exception ex) {
            LOGGER.error("Delete all failed", ex);
            throw new DatabaseException("Delete all failed", ex);
        }
    }

    @Override
    public boolean schemaExists() {
        LOGGER.debug("Checking if schema exists");
        try (Connection c = pool.acquire()) {
            DatabaseMetaData meta = c.getMetaData();
            ResultSet rs = meta.getTables(null, null, "QUANTITY_MEASUREMENT_ENTITY", null);
            boolean exists = rs.next();
            pool.release(c);
            LOGGER.info("Schema exists: {}", exists);
            return exists;
        } catch (Exception ex) {
            LOGGER.error("Schema check failed", ex);
            throw new DatabaseException("Schema check failed", ex);
        }
    }

    @Override
    public void forceError() {
        LOGGER.error("Forced error triggered in DatabaseRepository");
        throw new DatabaseException("Forced error for testing", new RuntimeException());
    }

    public void initializeSchema() {
        LOGGER.debug("Initializing schema for quantity_measurement_entity");
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
            LOGGER.info("Schema initialized successfully");
        } catch (Exception ex) {
            LOGGER.error("Schema initialization failed", ex);
            throw new DatabaseException("Schema initialization failed", ex);
        }
    }
}
