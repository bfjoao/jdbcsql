package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null;

	public static Connection getConnection() {
		if (conn == null) { // Verifica se a conexão ainda não foi estabelecida
			try {
				Properties props = loadProperties(); // Carrega as propriedades do banco
				String url = props.getProperty("dburl"); // Obtém a URL do banco de dados
				conn = DriverManager.getConnection(url, props); // Estabelece a conexão
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage()); // Lança uma exceção personalizada
			}
		}
		return conn; // Retorna a conexão
	}

	public static void closeConnection() {
		if (conn != null) { // Verifica se há uma conexão ativa
			try {
				conn.close(); // Fecha a conexão
			} 
			catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")) { // Abre o arquivo db.properties
			Properties props = new Properties();
			props.load(fs); // Carrega as propriedades do arquivo
			return props; // Retorna as configurações carregadas
		} 
		catch (IOException e) {
			throw new DbException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}