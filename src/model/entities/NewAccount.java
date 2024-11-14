package model.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import model.exceptions.DbException;

public class NewAccount {
	
	private String titular;
	private Double saldo;
	
	public NewAccount(String titular, Double saldo) {
		this.titular = titular;
		this.saldo = saldo;
	}
	
	public void newAccount() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			
			ps = conn.prepareStatement(
					"INSERT INTO contas "
					+ "(titular, saldo) "
					+ "VALUES "
					+ "(?,?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, this.titular);
			ps.setDouble(2, this.saldo);
			
			int rows = ps.executeUpdate();
			
			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
					int idAdd = rs.getInt(1);
					System.out.println("Cliente added! New id: " + idAdd);
				}
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(ps);
		}
	}

}
