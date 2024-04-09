package it.adesso.awspizza.connector;

import it.adesso.awspizza.exception.BadRequestErrorException;
import it.adesso.awspizza.model.StatoEnum;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class ModificaStatoOrdineConnector {

    private static final String UPDATE_STATO_ORDINE = "UPDATE ORDINE SET stato = ?,  data_completamento = CURRENT_TIMESTAMP " +
            " WHERE id = ? ";

    public void modificaOrdine(Long idOrdine, StatoEnum stato) throws SQLException, ClassNotFoundException {
        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(UPDATE_STATO_ORDINE);
        ps.setString(1, stato.toString());
        ps.setLong(2, idOrdine);
        int row = ps.executeUpdate();
        if (row != 1) {
            connection.rollback();
            throw new BadRequestErrorException("Ordine non trovato");
        }
        connection.commit();
        DbSingletonConnection.close(ps, connection);
    }
}
