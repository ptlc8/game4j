package fr.kmmad.game4j;

import java.util.List;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import java.sql.*;

public class Game4j {
	public static void main(String[] args) throws SQLException {
		Game4j game4j = new Game4j();
	}
	private Connection bdd ;
	Game4j() throws SQLException{
		DriverManager.registerDriver(new SQLServerDriver());
		bdd = DriverManager.getConnection("jdbc:sqlserver://localhost:3306\\game4j","root","EllaB13600");
		System.out.println("COUCOU");
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		rs.next();
		System.out.println(rs.getInt("id"));
	}
	
	private void println(String string) {
		// TODO Auto-generated method stub
		
	}

	// récupérer les parties finies dans l'historique 
	List<Game> getHistory(){
		return null;
	}
	// Récupérer une partie en cours dans la sauvegarde à partir de son nom
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
