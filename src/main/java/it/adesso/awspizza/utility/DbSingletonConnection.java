package it.adesso.awspizza.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class DbSingletonConnection {



    static Logger logger = LoggerFactory.getLogger(DbSingletonConnection.class);

    private static ConfigProperties configProperties;

    @Autowired
    private DbSingletonConnection(ConfigProperties configProperties){
        DbSingletonConnection.configProperties = configProperties;
    }

    private static DbSingletonConnection instance = null;
    private Connection conn = null;

    private static final String STATEMENT_ERROR = "The statement cannot be closed.";
    private static final String CONNECTION_ERROR = "The connection cannot be closed.";
    private static final String RESULTSET_ERROR = "The result set cannot be closed.";

    private DbSingletonConnection() {
    }

    private void init() throws SQLException, ClassNotFoundException {

        Class.forName(configProperties.getDriverClassName());

        conn = DriverManager.getConnection(configProperties.getUrl(), configProperties.getUsername(), configProperties.getPassword());

    }

    public Connection getConnection() {
        return conn;
    }

    public static DbSingletonConnection getInstance() throws SQLException, ClassNotFoundException {
        if (instance != null && !instance.getConnection().isClosed()) {
            return instance;
        } else {
            instance = new DbSingletonConnection();
            instance.init();
            return instance;
        }
    }

    public static void close(ResultSet rs, Statement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();

            } catch (SQLException e) {
                logger.error(RESULTSET_ERROR, e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(STATEMENT_ERROR, e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(CONNECTION_ERROR, e);
            }
        }

    }

    public static void close(ResultSet rs, Statement ps) {
        if (rs != null) {
            try {
                rs.close();

            } catch (SQLException e) {
                logger.error(RESULTSET_ERROR, e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(STATEMENT_ERROR, e);
            }
        }

    }

    public static void close(Statement ps, Connection conn) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(STATEMENT_ERROR, e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error(CONNECTION_ERROR, e);
            }
        }
    }


}