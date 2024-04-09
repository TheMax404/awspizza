package it.adesso.awspizza.connector;

import it.adesso.awspizza.model.PiattoModel;
import it.adesso.awspizza.utility.DbSingletonConnection;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuConnector {

    private static final String MENU_QUERY = "SELECT * FROM PIATTO WHERE VISIBILE = TRUE";


    public List<PiattoModel> call(String filtro) throws SQLException, ClassNotFoundException {
        List<PiattoModel> responseList = new ArrayList<>();
        String query = MENU_QUERY;
        if(StringUtils.isNotEmpty(filtro)){
            filtro = "%" + filtro + "%";
            query = MENU_QUERY.concat(" AND (UPPER(NOME) LIKE UPPER(?)  OR UPPER(DESCRIZIONE) LIKE  UPPER(?))");
        }

        DbSingletonConnection dbConnection = DbSingletonConnection.getInstance();
        Connection connection = dbConnection.getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        if(StringUtils.isNotEmpty(filtro)) {
            ps.setString(1, filtro);
            ps.setString(2, filtro);
        }
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while(rs.next()){
            PiattoModel piatto = PiattoModel.builder()
                    .id(rs.getLong("id"))
                    .nome(rs.getString("nome"))
                    .descrizione(rs.getString("descrizione"))
                    .prezzo(rs.getDouble("prezzo"))
                    .build();
            responseList.add(piatto);
        }
        DbSingletonConnection.close(rs, ps);
        return responseList;
    }
}
