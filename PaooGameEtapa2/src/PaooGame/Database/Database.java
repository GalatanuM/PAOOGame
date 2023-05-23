package PaooGame.Database;

import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Database {
    private static Connection c = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    private static Database database = null;
    private static int levelsFinished;

    private static RefLinks ref;
    private static int newLevelsFinished;
    private static float newHeroX;
    private static float newHeroY;
    private static int newScore;
    private static float heroX;
    private static float heroY;
    private static int score;

    private static boolean loaded = false;


    private Database(RefLinks ref) {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:./src/PaooGame/Database/data_base.db");
            stmt = c.createStatement();
            this.ref = ref;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected to database!");
    }

    public static synchronized Database getInstance(RefLinks ref) {
        if (database == null) {
            database = new Database(ref);
        }
        return database;
    }

    public static void databaseNewGame() {
        String sql = "CREATE TABLE IF NOT EXISTS Game (" +
                "id INTEGER PRIMARY KEY, " +
                "levelsFinished INTEGER," +
                "HeroX REAL," +
                "HeroY REAL," +
                "Score INTEGER" +
                ");";
        try {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean gameExists() {
        try {
            String sqlQuery = "SELECT 1 FROM Game LIMIT 1;";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);
            return resultSet.getBoolean(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void DatabaseSaveGame() {
        String sqlStatement;
        if (gameExists()) {
            sqlStatement = "UPDATE Game SET levelsFinished = ?, Score = ?, HeroX = ?, HeroY = ?;";
        } else {
            sqlStatement = "INSERT INTO Game(levelsFinished , Score , HeroX , HeroY) VALUES (?, ?, ?, ?);";
        }
        try {
            PreparedStatement pstmt = c.prepareStatement(sqlStatement);
            newLevelsFinished = ref.GetGame().levelsFinished;
            newHeroX = ref.GetHero().GetX() + ref.GetHero().getBoundX();
            newHeroY = ref.GetHero().GetY() + ref.GetHero().getBoundY();
            newScore = State.getScor();
            if (newLevelsFinished == 0) {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap1().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroX / Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroY / Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap1().map1ref.mapToString());
                writer.close();
            }
            if (newLevelsFinished == 1) {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap2().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroX / Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroY / Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap2().map2ref.mapToString());
                writer.close();
            }
            if (newLevelsFinished == 2) {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap3().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroX / Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroY / Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap3().map3ref.mapToString());
                writer.close();
            }
            if (newLevelsFinished == 3) {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap4().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroX / Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int) (newHeroY / Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap4().map4ref.mapToString());
                writer.close();
            }
            System.out.println(newHeroX + " " + newHeroY + " " + newScore + " " + newLevelsFinished);
            pstmt.setInt(1, newLevelsFinished);
            pstmt.setFloat(3, newHeroX);
            pstmt.setFloat(4, newHeroY);
            pstmt.setInt(2, newScore);
            pstmt.executeUpdate();
            System.out.println("DB UPDATED");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static int getLevelsFinished() {
        return levelsFinished;
    }

    public static void DatabaseLoadGame() {
        try {
            if (gameExists()) {
                String selectQuery = "SELECT levelsFinished , Score , HeroX , HeroY FROM Game";
                rs = stmt.executeQuery(selectQuery);
                while (rs.next()) {
                    levelsFinished = rs.getInt("levelsFinished");
                    heroX = rs.getFloat("HeroX");
                    heroY = rs.getFloat("HeroY");
                    score = rs.getInt("Score");
                    ref.GetHero().SetX(heroX);
                    ref.GetHero().SetY(heroY);
                    State.setScor(score);
                    ref.GetGame().setLevelFinished(levelsFinished);
                    ref.GetMap().setLevel(levelsFinished);
                    System.out.println(heroX + " " + heroY + " " + score + " " + levelsFinished);
                    loaded = true;
                    return;
                }
            }
            databaseNewGame();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void databaseCloseConnection() {
        try {
            stmt.close();
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public static void databaseSaveHighscore() {
        String createTableSql = "CREATE TABLE IF NOT EXISTS Highscores (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Score INTEGER" +
                ");";

        String insertSql = "INSERT INTO Highscores (Score) VALUES (?);";

        try {
            stmt.execute("DROP TABLE IF EXISTS Game;");
            stmt.execute(createTableSql);
            score = State.getScor() / 60;
            PreparedStatement pstmt = c.prepareStatement(insertSql);
            pstmt.setInt(1, score); // Assuming 'score' is the highscore value
            pstmt.executeUpdate();

            System.out.println("Highscore saved: " + score);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static List<String> loadHighscores() {
        String selectQuery = "SELECT Score FROM Highscores ORDER BY Score ASC LIMIT 10";
        try {
            c = DriverManager.getConnection("jdbc:sqlite:./src/PaooGame/Database/data_base.db");
            stmt = c.createStatement();
            ResultSet resultSet = stmt.executeQuery(selectQuery);
            List<String> highscores = new ArrayList<>();

            while (resultSet.next()) {
                int score = resultSet.getInt("Score");
                highscores.add(String.valueOf(score));
            }

            return highscores;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean isLoaded()
    {
        return loaded;
    }
}

