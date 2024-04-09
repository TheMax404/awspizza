package it.adesso.awspizza.connector;

import it.adesso.awspizza.model.OrdineModel;
import it.adesso.awspizza.model.StatoEnum;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GetOrdiniConnector {

    private static final String GET_ORDINI_QUERY = "SELECT id, stato, note, totale, data_inserimento, data_completamento from ordine WHERE 1=1 ";


    public List<OrdineModel> call(List<StatoEnum> stato) throws SQLException, ClassNotFoundException {
        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        StringBuilder query = new StringBuilder();
        List<OrdineModel> response = new ArrayList<>();
        if(stato != null && !stato.isEmpty()) {
            query.append(" AND (");
            for (int i = 0; i < stato.size(); i++) {
                query.append("stato = ").append(stato.get(i).toString());
                if(i < stato.size() -1){
                    query.append(" OR ");
                }else{
                    query.append(" )");
                }
            }
        }
        query.append("order by data_inserimento desc");
        PreparedStatement ps = connection.prepareStatement(GET_ORDINI_QUERY.concat(query.toString()));
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while(rs.next()){
            OrdineModel ordineModel = OrdineModel.builder()
                    .id(rs.getLong("id"))
                    .stato(rs.getString("stato"))
                    .note(rs.getString("note"))
                    .totale(rs.getDouble("totale"))
                    .dataCompletamento(rs.getTimestamp("data_completamento"))
                    .dataInserimento(rs.getTimestamp("data_inserimento"))
                    .build();
            response.add(ordineModel);
        }
        DbSingletonConnection.close(rs, ps);
        return response;

    }
}
