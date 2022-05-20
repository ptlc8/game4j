package fr.kmmad.game4j;

import java.util.List;
import java.util.ArrayList;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

public class Game4j {
	public static void main(String[] args) throws SQLException {
		Game4j game4j = new Game4j();
		int a = game4j.getHistory().size();
		System.out.println(a);
	}
	private Connection bdd ;
	public Game4j() throws SQLException{
		//DriverManager.registerDriver(new Driver());
		bdd = DriverManager.getConnection("jdbc:mysql://localhost:"+Credentials.DB_PORT+"/game4j", Credentials.DB_USERNAME, Credentials.DB_PASSWORD);
	}
	
	// recuperer parties finies de l'historique
	public List<Game> getHistory() throws SQLException{
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From history");
		List<Game> recupGameFinish = new ArrayList<Game>();
		while (rs.next()) {
			recupGameFinish.add(Game.loadSave(rs.getString("game")));
		}
		return recupGameFinish;
	}

	// recuperer toutes les parties en cours de la sauvegarde 
	public List<Game> getAllSaves() throws SQLException{
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select * From saves");
		List<Game> recupSaves = new ArrayList<Game>(); 
		for (int i=0; i <= rs.getFetchSize() ; i++) {
			recupSaves.add(Game.loadSave(rs.getString("game")));
			rs.next();
		}
		return recupSaves;
	}	
	
	// R�cup�rer une partie en cours dans la sauvegarde � partir de son nom
	public Game getSave(String nameSave) throws SQLException {
		Statement statement = bdd.createStatement();
		ResultSet rs = statement.executeQuery("Select name From saves Where name LIKE "+nameSave+"");
		System.out.println(rs.getInt("id"));
		if (rs.getFetchSize()==0) {
			return null;
		}
		rs.next();
		return Game.loadSave(rs.getString("game"));
	}

	// Ajouter une partie finie et la mettre dans l'historique
	public void addGameHistory(Game game) throws SQLException {
		Statement statement = bdd.createStatement();
		System.out.println("Insert into history(date, game) values('"+new Date(game.getDate().getTime())+"','"+game.createSave()+"')");
		statement.executeUpdate("Insert into history(date, game) values('"+new Date(game.getDate().getTime())+"','"+game.createSave()+"')");
	}
	
	// Ajouter une partie en cours dans la sauvegarde 
	public void addGameSave(Game game) throws SQLException {
		Statement statement = bdd.createStatement();
		statement.executeUpdate("Insert into saves(date, game) values("+new Date(game.getDate().getTime())+","+game.createSave()+")");
	}
}