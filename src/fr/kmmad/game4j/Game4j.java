package fr.kmmad.game4j;

import java.util.List;

import java.sql.*;
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
	List<Game> getHistory(){
		return null;
	}
	// R�cup�rer une partie en cours dans la sauvegarde � partir de son nom
	Game getSave(String nameSave) {
		return null;
	}
	// Ajouter une partie finie et la mettre dans l'historique
	void addGameHistory(Game game) {
	}
	// Ajouter une partie en cours dans la sauvegarde 
	void addGameSave(Game game) {
	}
}
