package it.adesso.awspizza.connector;

import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class PutOrdineConnector {

    private static final String GET_NEXT_ORDINE_QUERY = "UPDATE ORDINE SET STATO = ? " + " WHERE ID = ? AND STATO = 'PENDING' ";


    public long call(Long idOrdine, String stato) throws SQLException, ClassNotFoundException {
        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(GET_NEXT_ORDINE_QUERY, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, stato);
        ps.setLong(2, idOrdine);
        ps.executeUpdate();
        long key = -1;
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            key = rs.getInt(1);
        }

        connection.commit();
        DbSingletonConnection.close(rs, ps);
        return key;
    }
}



