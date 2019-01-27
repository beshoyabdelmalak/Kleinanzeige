package de.unidue.inf.is.stores;

import java.io.Closeable; 
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.unidue.inf.is.domain.Nachricht;
import de.unidue.inf.is.utils.DBUtil;

public final class NachrichtStore implements Closeable{

	private Connection connection;
	private boolean complete;
	private String query;
	
	public NachrichtStore() throws StoreException {
		try {
			connection = DBUtil.getExternalConnection("project");
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}
	
	//Nachricht addieren
	public void addNachricht(Nachricht nachricht) {
		try {
			query = "insert into dbp64.nachricht(text , absender, empfaenger) values (?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, nachricht.getText());
			preparedStatement.setString(2, nachricht.getAbsender());
			preparedStatement.setString(3, nachricht.getEmpfaenger());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new StoreException(e);
		}
	}
	
	//erhalten die Nachrichten von der absender und empfaenge von der DB
	public ArrayList<Nachricht> getNachricht(String absender, String empfaenger) throws StoreException {
		ArrayList<Nachricht> nachrichten = new ArrayList<>();
		try {
			query = "select text, absender, empfaenger from dbp64.nachricht where (absender = ? and empfaenger = ?) or"
					+ " (absender =? and empfaenger =?) order by id asc";
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, absender);
			pstmt.setString(2, empfaenger);
			pstmt.setString(3, empfaenger);
			pstmt.setString(4, absender);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Nachricht nachricht = new Nachricht(rs.getString(1), rs.getString(2), rs.getString(3));
				nachrichten.add(nachricht);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nachrichten;
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
				} else {
					connection.rollback();
				}
			} catch (SQLException e) {
				throw new StoreException(e);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new StoreException(e);
				}
			}
		}
	}
	
}
