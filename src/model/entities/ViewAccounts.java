package model.entities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import model.exceptions.DbException;

public class ViewAccounts {

	public static void view() {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			
			rs = st.executeQuery("select * from contas");
			
			while(rs.next()) {
				System.out.println("Id: " + rs.getInt("id")+" - Titular: " + rs.getString("titular")
				+ " - Saldo: R$" + String.format("%.2f", rs.getDouble("saldo")));
			}
			
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}
	
}
