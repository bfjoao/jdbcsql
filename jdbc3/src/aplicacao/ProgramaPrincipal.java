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

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+ "VALUES "
					+ "(?, ?, ?, ? ,?)", // Placeholders para os valores
					Statement.RETURN_GENERATED_KEYS); // Retorna as chaves geradas
			
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime())); // Converte string para data SQL
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			
			/*
			st = conn.prepareStatement(
					"INSERT INTO department (Name) VALUES ('D1'),('D2')", // Insere duas novas linhas na tabela department, adicionando os valores "D1" e "D2" na coluna Name.
					Statement.RETURN_GENERATED_KEYS);
			*/		
			
			// Executa a atualização no banco de dados e retorna o número de linhas afetadas
			int linhasAfetadas = st.executeUpdate();
			
			if(linhasAfetadas > 0) { // Verifica se pelo menos uma linha foi modificada no banco de dados
				ResultSet rs = st.getGeneratedKeys(); // Obtém as chaves geradas automaticamente pelo banco de dados
				while (rs.next()) { // Percorre os resultados para acessar os valores das chaves geradas
					int id = rs.getInt(1); // Pega o valor da primeira coluna (chave primária gerada)
					System.out.println("Pronto! Id = " + id); // Exibe o ID gerado no console
				}
			}
			else
				System.out.println("Nenhuma linha afetada!");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}