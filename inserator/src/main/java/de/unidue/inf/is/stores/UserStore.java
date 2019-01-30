package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.spi.DirStateFactory.Result;

import org.omg.PortableInterceptor.USER_EXCEPTION;

import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class UserStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public UserStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection("project");
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }


    public void addUser(User userToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                            .prepareStatement("insert into dbp64.Benutzer (benutzername, name) values (?, ?)");
            preparedStatement.setString(1, userToAdd.getBenutzerName());
            preparedStatement.setString(2, userToAdd.getName());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    
    public ArrayList<String> getUserNames(){
    	ArrayList<String> array = new ArrayList<>();
    	String query= "select benutzername from dbp64.benutzer"; 
		try {
			PreparedStatement pstmt = connection.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
	    	while (rs.next()) {
	    		String user = rs.getString(1);
	    		array.add(user);
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return array;
    }
    
    public User getUser(String username){
    	String query1 = "select b.name, b.benutzername , b.eintrittsdatum, count(k.id) from dbp64.Benutzer b join dbp64.anzeige k on b.benutzername = k.ersteller "
    			+ "where b.benutzername = ? and k.status = ? group by b.benutzername, b.name, b.eintrittsdatum" ;
    	String query2 = "select b.name, b.benutzername, b.eintrittsdatum from dbp64.Benutzer b where b.benutzername = ? ";
    	User user = null;
    	PreparedStatement pstmt;
		try {
			pstmt = connection.prepareStatement(query1);
			pstmt.setString(1, username);
			pstmt.setString(2, "verkauft");
			ResultSet rs = pstmt.executeQuery();
	    	boolean empty = true ;
	    	while (rs.next()) {
	    		empty = false;
	    		user = new User(rs.getString(1), rs.getString(2));
	    		user.setEintrittsDatum(rs.getDate(3));
	    		user.setGekauft(rs.getInt(4));
	    	}
	    	rs.close();
	    	if (empty) {
	    		pstmt = connection.prepareStatement(query2);
				pstmt.setString(1, username);
				ResultSet set = pstmt.executeQuery();
		    	while (set.next()) {
		    		user = new User(set.getString(1), set.getString(2));
		    		user.setEintrittsDatum(set.getDate(3));
		    		user.setGekauft(0);
		    	}
	    	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
    	
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
