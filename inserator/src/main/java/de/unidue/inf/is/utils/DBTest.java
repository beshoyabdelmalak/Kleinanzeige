package de.unidue.inf.is.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.unidue.inf.is.domain.Anzeige;
import de.unidue.inf.is.stores.StoreException;

public class DBTest {

	private static Connection connection;
	private static String  query = "insert into dbp64.anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)";
	public static void main(String args[]) {
		
        try {
            connection = DBUtil.getExternalConnection("project");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Anzeige a = new Anzeige("reebok mid schuh", "schuhe", 56, "BillGates", "aktiv");
        getIDofInsertedQ(a);
        
		
	}
	public static int getIDofInsertedQ(Anzeige a) {
    	Integer result = 0;
    	ResultSet rs = null;
//		String titel = a.getTitel();
//		String text = a.getText();
//		float preis = a.getPreis();
//		String ersteller = a.getErsteller();
//		String status = a.getStatus();
    	try {
    		String query1 = "insert into dbp64.Anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)";
    		PreparedStatement preparedStatement = connection.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, a.getTitel());
            preparedStatement.setString(2, a.getText());
            preparedStatement.setFloat(3, a.getPreis());
            preparedStatement.setString(4, a.getErsteller()); 
            preparedStatement.setString(5, "aktiv");
    		preparedStatement.executeUpdate();
    			rs = preparedStatement.getGeneratedKeys();
    			if(rs.next()) {
    				result = rs.getInt(1);
    				System.out.println("id =" + result);
    			}
    		

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return result;
    }
	
	   public static void addAnzeige(Anzeige a) {
	        try {
	        	//connection.prepareStatement("insert into dbp64.Anzeige (titel, text, preis, ersteller, status) values (?,?,?,?,?)");
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setString(1, a.getTitel());
	            preparedStatement.setString(2, a.getText());
	            preparedStatement.setFloat(3, a.getPreis());
	            preparedStatement.setString(4, a.getErsteller());
	            preparedStatement.setString(5, a.getStatus());
	            

	            preparedStatement.executeUpdate();
	        }
	        catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
