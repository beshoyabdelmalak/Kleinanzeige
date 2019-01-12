package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
//    	int numero = -1;
//    	int result = 0;
        try {
        	query = "insert into dbp64.Anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setString(4, anzeigeToAdd.getErsteller()); 
            preparedStatement.setString(5, "aktiv");
            
            

            preparedStatement.executeUpdate();   
//            ResultSet rs = preparedStatement.getGeneratedKeys();
//    		if (rs.next()){
//    		    result =rs.getInt(1);
//    		}
    		//return result;
        }catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public int idOfTheLastInsertedValue(String query) {
        PreparedStatement preparedStatement;
        ResultSet rs;
        int result= 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			rs = preparedStatement.executeQuery();
			while(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
        
    	
    }
    
   public ArrayList<Anzeige> getAllAnzeige(){
	   ArrayList<Anzeige> array = new ArrayList<>();
	   query = "select * from dbp64.anzeige where status = ?";
	   try {
		PreparedStatement pstmt=  connection.prepareStatement(query);
		pstmt.setString(1, "aktiv");
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Anzeige anzeige = new Anzeige(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getFloat(4),rs.getDate(5), rs.getString(6), rs.getString(7));
			//anzeige.setDate(rs.getDate(5));
			array.add(anzeige);	
		}
		
	  }catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return array;
	   
	   
   }
   public Anzeige getAnzeige(int id) {
	   	String query = "select * from dbp64.anzeige where id = ?";
	   	PreparedStatement preparedStatement;
	   	Anzeige anzeige = null;
	   	try {
		     preparedStatement = connection.prepareStatement(query);
		     preparedStatement.setInt(1, id);
		     ResultSet rs = preparedStatement.executeQuery();
		     while(rs.next()) {
		    	 String titel = rs.getString(2);
		    	 String text = rs.getString(3);
		    	 float preis = rs.getFloat(4);
		    	 Date erstellungsdatum = rs.getDate(5);
		    	 String ersteller = rs.getString(6);
		    	 String status = rs.getString(7);
		    	 
		    	 anzeige = new Anzeige(id, titel, text, preis, erstellungsdatum, ersteller, status);
		    	 //System.out.println(anzeige.getErsteller());
		     }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	
	   	
	   	
	   	return anzeige;
   }


    public void insertIntoHatKategorie(int id, String nameOfkategorie) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp64.HatKategorie (anzeigeID, kategorie) values (?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, nameOfkategorie);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.fillInStackTrace();
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
