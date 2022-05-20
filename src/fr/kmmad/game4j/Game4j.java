package fr.kmmad.game4j;

import java.util.List;
import java.util.ArrayList;

import java.sql.*;

import com.mysql.cj.jdbc.Driver;

public class Game4j {
	
	private Connection bdd = null;
	
	public Game4j() {
		try {
			DriverManager.registerDriver(new Driver());
			bdd = DriverManager.getConnection("jdbc:mysql://localhost:"+Credentials.DB_PORT+"/game4j", Credentials.DB_USERNAME, Credentials.DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// recuperer parties finies de l'historique
	public List<Game> getHistory() {
		List<Game> recupGameFinish = new ArrayList<Game>();
		try {
			Statement statement = bdd.createStatement();
			ResultSet rs = statement.executeQuery("Select * From history");
			while (rs.next()) {
				recupGameFinish.add(Game.loadSave(rs.getString("game")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recupGameFinish;
	}

	// recuperer toutes les parties en cours de la sauvegarde
	public List<Game> getAllSaves() {
		List<Game> recupSaves = new ArrayList<Game>();
		try {
			Statement statement = bdd.createStatement();
			ResultSet rs = statement.executeQuery("Select * From saves");
			while (rs.next()) {
				recupSaves.add(Game.loadSave(rs.getString("game")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recupSaves;
	}	
	
	// Récupérer une partie en cours dans la sauvegarde à partir de son nom
	public Game getSave(String nameSave) {
		try {
			Statement statement = bdd.createStatement();
			ResultSet rs = statement.executeQuery("Select name From saves Where name LIKE "+nameSave+"");
			System.out.println(rs.getInt("id"));
			if (!rs.next()) {
				return null;
			}
			return Game.loadSave(rs.getString("game"));
		} catch (SQLException e) {
			return null;
		}
	}

	// Ajouter une partie finie et la mettre dans l'historique
	public void addGameHistory(Game game) {
		try {
			Statement statement = bdd.createStatement();
			System.out.println("Insert into history(date, game) values('"+new Date(game.getDate().getTime())+"','"+game.createSave()+"')");
			statement.executeUpdate("Insert into history(date, game) values('"+new Date(game.getDate().getTime())+"','"+game.createSave()+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Ajouter une partie en cours dans la sauvegarde
	public void addGameSave(Game game, String name) {
		try {
			Statement statement = bdd.createStatement();
			statement.executeUpdate("Insert into saves(date, game, name) values('"+new Date(game.getDate().getTime())+"','"+game.createSave()+"','"+name+"')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}