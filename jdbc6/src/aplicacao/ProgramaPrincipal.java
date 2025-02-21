package aplicacao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement st = null;

		try {
			conn = DB.getConnection();
			conn.setAutoCommit(false); // Desabilita o autocommit para permitir controle manual da transação
			
			st = conn.createStatement(); 
			
			int linhas1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");
			
			/*
			int x = 1;
			if (x < 2) {
				throw new SQLException("Erro falso!");
			}
			*/
			
			int linhas2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit(); // Se ambas as operações forem bem-sucedidas, a transação é confirmada no banco de dados
			
			// Exibição do número de linhas afetadas por cada atualização
			System.out.println("Linha1 " + linhas1);
			System.out.println("Linha2 " + linhas2);
			
		} 
		catch (SQLException e) {
			try {
				conn.rollback(); // Caso ocorra um erro, a transação é revertida para evitar inconsistências no banco de dados
				throw new DbException("Transação revertida! Causada por: " + e.getMessage());
			} catch (SQLException e1) { // Caso ocorra um erro ao reverter a transação, lança uma nova exceção
				throw new DbException("Erro na reversão da transação! Causada por: " + e.getMessage());
			}
		} 
		finally {
			DB.closeConnection();
			DB.closeStatement(st);
		}
	}
}