package it.adesso.awspizza.connector;

import it.adesso.awspizza.model.InfoOrdineModel;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class InfoOrdineConnector {
    private static final String INFO_ORDINE_QUERY = "SELECT o.stato, " +
            "       o.totale, " +
            "       o.data_inserimento, " +
            "       o.data_completamento, " +
            "       om.id_ordine, " +
            "       om.id_piatto, " +
            "       p.nome, " +
            "       o.note, " +
            "       p.prezzo " +
            " FROM   ordine AS O " +
            "       JOIN ordine_piatto AS OM " +
            "         ON O.id = OM.id_ordine " +
            "       JOIN piatto AS P " +
            "         ON P.id = OM.id_piatto " +
            " WHERE  o.id = ? ";

    public List<InfoOrdineModel> getInfoOrdine(Long idOrdine) throws SQLException, ClassNotFoundException {
        List<InfoOrdineModel> responseList = new ArrayList<>();

        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(INFO_ORDINE_QUERY);
        ps.setLong(1, idOrdine);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            InfoOrdineModel model = InfoOrdineModel.builder()
                    .idOrdine(rs.getLong("id_ordine"))
                    .idPiatto(rs.getLong("id_piatto"))
                    .note(rs.getString("note"))
                    .stato(rs.getString("stato"))
                    .dataInserimento(rs.getTimestamp("data_inserimento"))
                    .dataCompletamento(rs.getTimestamp("data_completamento"))
                    .nome(rs.getString("nome"))
                    .prezzo(rs.getDouble("prezzo"))
                    .totale(rs.getDouble("totale"))
                    .build();
            responseList.add(model);
        }
        DbSingletonConnection.close(rs, ps);
        return responseList;
    }
}
