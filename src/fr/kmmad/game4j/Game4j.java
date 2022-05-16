package fr.kmmad.game4j;

import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Game4j {
	public static void main(String[] args) throws SQLException {
		Game4j game4j = new Game4j();
	}
	private Connection bdd ;
	Game4j() throws SQLException{
		DriverManager.registerDriver(new SQLServerDriver());
		bdd = DriverManager.getConnection("jdbc:sqlserver://localhost:3306;dbname=game4j","root","EllaB13600");
		System.out.println("COUCOU");
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		rs.next();
		System.out.println(rs.getInt("id"));
	}
	

	// récupérer les parties finies dans l'historique 
	List<Game> getHistory() throws SQLException{
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		rs.next();
		System.out.println(rs.getInt("id"));
		return null;
	}
	// Récupérer une partie en cours dans la sauvegarde à partir de son nom
	Game getSave(String nameSave) throws SQLException {
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From saves");
		rs.next();
		System.out.println(rs.getInt("id"));
		return null;
	}
	// Ajouter une partie finie et la mettre dans l'historique
	void addGameHistory(Game game) throws SQLException {
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Insert to history");
		rs.next();
		System.out.println(rs.getInt("id"));
	}
	// Ajouter une partie en cours dans la sauvegarde 
	void addGameSave(Game game) throws SQLException {
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Insert to saves");
		rs.next();
		System.out.println(rs.getInt("id"));
	}
}
