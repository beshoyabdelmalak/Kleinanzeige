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
import de.unidue.inf.is.domain.Kommentar;
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setString(4, anzeigeToAdd.getErsteller()); 
            preparedStatement.setString(5, "aktiv");
           
            preparedStatement.executeUpdate();   

        }catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public void addKommentar(String kommentarToAdd) throws StoreException {
        try {
        	query = "insert into dbp64.kommentar (text) values (?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, kommentarToAdd);
            preparedStatement.executeUpdate();   

        }catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    
    public void deleteFromHatKategorie(int id) throws StoreException {
        try {
        	query = "delete from dbp64.HatKategorie where anzeigeID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            
            

            preparedStatement.executeUpdate();   

        }catch (SQLException e) {
        	System.out.println("problem bei deleteFromHatKategorie");
            e.printStackTrace();;
        }
    }
    
    

    
    public void updateAnzeige(Anzeige anzeigeToAdd) throws StoreException {
        try {
        	query = "update dbp64.Anzeige set titel = ?, text = ?, preis = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, anzeigeToAdd.getTitel());
            preparedStatement.setString(2, anzeigeToAdd.getText());
            preparedStatement.setFloat(3, anzeigeToAdd.getPreis());
            preparedStatement.setInt(4, anzeigeToAdd.getId()); 
            
            

            preparedStatement.executeUpdate();   

        }catch (SQLException e) {
        	
            e.printStackTrace();
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
   public void insertIntoKauft(String benutzername, int id) {
	   String query = "insert into dbp64.Kauft(benutzername, anzeigeID ) values (?, ?)";
	   PreparedStatement preparedStatement = null;
	   try {
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setString(1,benutzername );
		   preparedStatement.setInt(2,id );
		   preparedStatement.executeUpdate();
		   
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   public ArrayList<Anzeige> getAllAnzeige(){
	   ArrayList<Anzeige> array = new ArrayList<>();
	   query = "select * from dbp64.anzeige where status = 'aktiv'";
	   try {
		PreparedStatement pstmt=  connection.prepareStatement(query);
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
   
   public ArrayList<Kommentar> getAllKommentaren(int id){
	   ArrayList<Kommentar> kommentaren = new ArrayList<>();
	   query = "select k.text, hk.benutzername from dbp64.kommentar k join dbp64.hatkommentar hk on k.id = hk.kommentarID  where hk.anzeigeid = ?"
;
	   try {
		PreparedStatement pstmt=  connection.prepareStatement(query);
		pstmt.setInt(1, id);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Kommentar kommentar = new Kommentar(rs.getString(2) + "        :"+rs.getString(1));
			kommentaren.add(kommentar);	
		}
		
	  }catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return kommentaren; 
   }
   
   public void deleteAnzeigeWithId(int id) {
	   String query = "delete from dbp64.anzeige where id = ?";
	   PreparedStatement preparedStatement ;
	   try {
		   preparedStatement = connection.prepareStatement(query);
		   preparedStatement.setInt(1, id);
		   preparedStatement.executeUpdate();
		   
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
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
    
    public void insertIntoHatKommentar(int kommentarid, String benutzername, int anzeigeID) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into dbp64.HatKommentar (kommentarID, benutzername ,anzeigeID) values (?,?, ?)");
            preparedStatement.setInt(1, kommentarid);
            preparedStatement.setString(2, benutzername);
            preparedStatement.setInt(3, anzeigeID);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
    
    public ArrayList<Anzeige> getOffersByUsername(String username){
    	ArrayList<Anzeige> array = new ArrayList<>();
    	String query = "select * from dbp64.anzeige where ersteller= ?";
    	try {
    		PreparedStatement pstmt = connection.prepareStatement(query);
    		pstmt.setString(1, username);
    		ResultSet rs = pstmt.executeQuery();
    		while (rs.next()) {
    			Anzeige anzeige = new Anzeige(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4), rs.getDate(5)
    					,rs.getString(6), rs.getString(7));
    			array.add(anzeige);
    			
    		}
    	}catch (SQLException e) {
			// TODO: handle exception
    		 e.fillInStackTrace();
		}
    	return array;
    }
    
    public ArrayList<Anzeige> getPurchasedOffers(String username){
    	ArrayList<Anzeige> array = new ArrayList<>();
    	String query = "select a.titel, a.text, a.preis, a.ersteller, a.status, a.erstellungsdatum, k.benutzername from dbp64.benutzer b join dbp64.anzeige a on"
    			+ " b.benutzername = a.ersteller join dbp64.kauft k on a.id = k.anzeigeID where k.benutzername = ?";
    	try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Anzeige anzeige = new Anzeige(rs.getString(1), rs.getString(2), rs.getFloat(3), rs.getString(4),
						rs.getString(5));
				anzeige.setDate(rs.getDate(6));
				anzeige.setBuyer(rs.getString(7));
				array.add(anzeige);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return array;
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
