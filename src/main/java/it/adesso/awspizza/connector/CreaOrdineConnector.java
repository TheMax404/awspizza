package it.adesso.awspizza.connector;

import it.adesso.awspizza.exception.InternalServerErrorException;
import it.adesso.awspizza.model.CreaOrdineModel;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class CreaOrdineConnector {

    private static final String CREA_ORDINE = "INSERT INTO ordine (" +
            " id," +
            " stato," +
            " totale," +
            " note," +
            " data_inserimento," +
            " data_completamento" +
            "            )" +
            "VALUES (" +
            "  NEXT VALUE FOR ordine_seq," +
            "  ?," +
            "  (SELECT SUM(prezzo) FROM piatto WHERE id IN (<PLACE_HOLDER_PIATTI>))," +
            "  ?," +
            "  CURRENT_TIMESTAMP," +
            "  NULL" +
            "          ) ";

    private static final String CREA_PIATTO_ORDINE = "INSERT INTO ORDINE_PIATTO " +
            " VALUES <PLACE_HOLDER_ORDINE_PIATTO> ";


    public int creaOrdine(CreaOrdineModel model) throws SQLException, ClassNotFoundException {
        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        connection.setAutoCommit(false);

        String insertQuery = CREA_ORDINE;
        int insertedkey = -1;

        StringBuilder placeHolder = new StringBuilder();
        for (int i = 0; i < model.getPiatti().size(); i++) {
            placeHolder.append(model.getPiatti().get(i));
            if (i < model.getPiatti().size() - 1) {
                placeHolder.append(" , ");
            }
        }
        insertQuery = insertQuery.replace("<PLACE_HOLDER_PIATTI>", placeHolder.toString());

        PreparedStatement ps = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, model.getStato());
        ps.setString(2, model.getNote());

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            throw new InternalServerErrorException();
        }

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            insertedkey = rs.getInt(1);
        }

        placeHolder = new StringBuilder();
        for (int i = 0; i < model.getPiatti().size(); i++) {
            placeHolder.append("( ").append(insertedkey).append(" , ").append(model.getPiatti().get(i)).append(" )");
            if (i < model.getPiatti().size() - 1) {
                placeHolder.append(" , ");
            }
        }
        insertQuery = CREA_PIATTO_ORDINE.replace("<PLACE_HOLDER_ORDINE_PIATTO>", placeHolder);
        ps = connection.prepareStatement(insertQuery);

        try {
            ps.executeUpdate();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        connection.commit();
        DbSingletonConnection.close(rs, ps);
        return insertedkey;
    }
}

