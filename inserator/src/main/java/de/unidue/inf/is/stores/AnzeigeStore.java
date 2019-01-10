package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.db2.jcc.am.SqlException;
import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.domain.Kategorie;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class AnzeigeStore implements Closeable {

    private Connection connection;
    private boolean complete;
    private static AnzeigeStore instance;


    private AnzeigeStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection("project");
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    
    public static AnzeigeStore getInstance() {
		if (instance == null) {
			instance = new AnzeigeStore();
		}

		return instance;
	}

    public void addAnzeige(Anzeige anzeigeToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)");
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setString(4, anzeigeToAdd.getErsteller()); 
            preparedStatement.setString(5, anzeigeToAdd.getStatus());
            
            

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public int getIDofInsertedQ(String query) {
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
            PreparedStatement preparedStatement = connection.prepareStatement("insert into HatKategorie (anzeigeID, kategorie) values ('id','nameOfkategorie')");

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    
    public ArrayList<Anzeige> getAllAnzeige(){
		ArrayList<Anzeige> offers = new ArrayList<>();
		String query = "select * from anzeige where status = 'aktiv'";
		try {
			PreparedStatement stmt= connection.prepareStatement(query);
			ResultSet result = stmt.executeQuery();
			while (result.next()) {
				Anzeige offer= new Anzeige(result.getString(2), result.getString(3), result.getFloat(4), result.getString(6), result.getString(7));
				offers.add(offer);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return offers; 
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
