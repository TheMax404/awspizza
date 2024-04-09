package it.adesso.awspizza.connector;

import it.adesso.awspizza.exception.NotFoundErrorException;
import it.adesso.awspizza.model.StatoEnum;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class CancellaOrdineConnector {

    private static final String UPDATE_STATO_ORDINE = "UPDATE ORDINE SET stato = ? " +
            " WHERE id = ? AND stato = 'PENDING' ";

    public void modificaOrdine(Long idOrdine, StatoEnum stato) throws SQLException, ClassNotFoundException {
        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(UPDATE_STATO_ORDINE);
        ps.setString(1, stato.toString());
        ps.setLong(2, idOrdine);
        int row = ps.executeUpdate();
        if(row != 1){
            connection.rollback();
            throw new NotFoundErrorException("Ordine in input not presente");
        }
        connection.commit();
        DbSingletonConnection.close(ps,connection);
    }
}
