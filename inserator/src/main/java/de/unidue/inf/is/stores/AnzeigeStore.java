package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.domain.Kategorie;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class AnzeigeStore implements Closeable {

    private Connection connection;
    private boolean complete;
    private String query;


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
        	query = "insert into dbp64.Anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setString(4, anzeigeToAdd.getErsteller()); 
            preparedStatement.setString(5, "aktiv");
            
            

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public int getIDofInsertedQ() {
    	Integer result = 0;
    	Integer numero ;
    	try {
    		PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    		numero = preparedStatement.executeUpdate();

    		ResultSet rs = preparedStatement.getGeneratedKeys();
    		if (rs.next()){
    		    result =rs.getInt(1);
    		}
    		return result;

        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public void insertIntoHatKategorie(int id, String nameOfkategorie) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into HatKategorie (anzeigeID, kategorie) values (?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nameOfkategorie);

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
