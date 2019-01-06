package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class AnzeigeStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public AnzeigeStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection("project");
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }


    public void addAnzeige(Anzeige anzeigeToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Anzeige (titel, text, preis,erstellungsdatum, ersteller, status) values (?,?,?,?,?,?)");
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setString(4, anzeigeToAdd.getErstellungsdatum().toString());
            //preparedStatement.setFloat(3, anzeigeToAdd.getPreis());  hier muss der ersteller bestimmt werden
            preparedStatement.setString(6, anzeigeToAdd.getStatus());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }


    public void complete() {
        complete = true;
    }


    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }

}
