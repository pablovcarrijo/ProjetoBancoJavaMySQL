package model.entities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import db.DB;
import model.exceptions.DbException;

public class Transferencia {
	
	public static void transfer(Integer contaSaque, Integer contaDeposito, Double value) {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = DB.getConnection();
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(
					"UPDATE contas SET saldo = saldo - ? "
					+ "WHERE "
					+ "(id = ?)");
			ps.setDouble(1, value);
			ps.setInt(2, contaSaque);
			
			ps.executeUpdate();
			
			ps = conn.prepareStatement(
					"UPDATE contas SET saldo = saldo + ? "
					+ "WHERE "
					+ "(id = ?)");
			ps.setDouble(1, value);
			ps.setInt(2, contaDeposito);
			
			ps.executeUpdate();
			
			System.out.println("Transferencia realizada com sucesso...");
			
			LocalDateTime date = LocalDateTime.now();
			
			ps = conn.prepareStatement(
					"INSERT INTO transacoes "
					+ "(conta_origem, conta_destino, valor, data_transacao) "
					+ "VALUES "
					+ "(?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, contaSaque);
			ps.setInt(2, contaDeposito);
			ps.setDouble(3, value);
			ps.setTimestamp(4, Timestamp.valueOf(date));
			
			int rows = ps.executeUpdate();
			conn.commit();

			if(rows > 0) {
				rs = ps.getGeneratedKeys();
				while(rs.next()) {
					int idTrans = rs.getInt(1);
					System.out.println("Id da transação: " + idTrans);
				}
			}
			
			
		}
		catch(SQLException e) {
			try {
				conn.rollback();
				throw new DbException("Erro na transferencia: " + e.getMessage());
			}
			catch(SQLException e1) {
				throw new DbException("Erro no rollback: " + e1.getMessage());
			}
		}
		finally {
			DB.closeConnection();
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
	}

}
