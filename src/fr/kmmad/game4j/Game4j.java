package fr.kmmad.game4j;

import java.util.List;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.jdbc.Driver;

public class Game4j {
	public static void main(String[] args) throws SQLException {
		Game4j game4j = new Game4j();
	}
	private Connection bdd ;
	Game4j() throws SQLException{
		//DriverManager.registerDriver(new Driver());
		bdd = DriverManager.getConnection("jdbc:mysql://localhost:3306/game4j", Credentials.DB_USERNAME, Credentials.DB_PASSWORD);
		System.out.println("COUCOU");
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		rs.next();
		System.out.println(rs.getInt("id"));
	}
	
	// r�cup�rer les parties finies dans l'historique 
	List<Game> getHistory() throws SQLException{
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		rs.next();
		System.out.println(rs.getInt("id"));
		return null;
	}

	// R�cup�rer une partie en cours dans la sauvegarde � partir de son nom
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
