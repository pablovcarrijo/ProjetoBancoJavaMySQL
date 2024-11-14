package model.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import model.exceptions.DbException;

public class RemoveAccount {
	
	public static void removeAccount(String account) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
		
			conn = DB.getConnection();
			
			ps = conn.prepareStatement(
					"DELETE FROM contas "
					+ "WHERE "
					+ "titular = ?",
					Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, account);
			
			int rows = ps.executeUpdate();

			System.out.println("Conta excluida com sucesso...");
			
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
