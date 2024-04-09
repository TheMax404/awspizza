package it.adesso.awspizza.connector;

import it.adesso.awspizza.model.OrdineModel;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class StatoOrdineConnector {

    private final static String STATO_ORDINE_QUERY = "SELECT O.ID, O.STATO, O.DATA_INSERIMENTO, O.DATA_COMPLETAMENTO " +
                                              " FROM ORDINE O " +
                                              " WHERE O.ID = ? ";
    public OrdineModel getStatoOrdine(Long idOrdine) throws SQLException, ClassNotFoundException {
        OrdineModel response = new OrdineModel();

        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(STATO_ORDINE_QUERY);
        ps.setLong(1, idOrdine);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        if(rs.next()) {
            response.setId(rs.getLong("id"));
            response.setStato(rs.getString("stato"));
            response.setDataInserimento(rs.getTimestamp("data_inserimento"));
            response.setDataCompletamento(rs.getTimestamp("data_completamento"));
        }
        DbSingletonConnection.close(rs, ps);
        return response;
    }
}
