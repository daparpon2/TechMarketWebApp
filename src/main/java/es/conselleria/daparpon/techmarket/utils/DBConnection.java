package es.conselleria.daparpon.techmarket.utils;

import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {

    public static final String SQL_LOG_TEMPLATE = "[SQL] {}";
    private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);

    private Connection conn;

    public DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DBParams.DB_URL + "/" + DBParams.DB_NAME, DBParams.DB_USER, DBParams.DB_PASSWORD);

            if (conn != null) {
                conn.isValid(1);
                LOG.info("Connecting database [" + conn + "] OK");
                LOG.info("Database properties have been initialized");
            } else {
                LOG.error("Database connection unavailable");
            }
        } catch (SQLException sqle) {
            LOG.error("Error when trying to open a connection", sqle);
        } catch (ClassNotFoundException cnfe) {
            LOG.error("Database connection driver not found", cnfe);
        }
    }

    public Connection getConnection() throws SQLException {
        if (conn == null) {
            LOG.error("Database connection driver not found");
            throw new SQLException("Database connection driver not found");
        }
        return conn;
    }

    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                LOG.info("Closing database: [" + conn + "] OK");
                conn = null;
            } catch (SQLException sqle) {
                LOG.error("Error when trying to close database connection " + conn, sqle);
            }
        }
    }
}
