package aplicacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class ProgramaPrincipal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = null;
		PreparedStatement st = null;

		try {
			conn = DB.getConnection();
			st = conn.prepareStatement("DELETE FROM department " 
			+ "WHERE " 
			+ "Id = ?");

			st.setInt(1, 5);

			int linhasAfetadas = st.executeUpdate();
			System.out.println("Feito! Linhas afetadas: " + linhasAfetadas);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			DB.closeConnection();
			DB.closeStatement(st);
		}
	}
}