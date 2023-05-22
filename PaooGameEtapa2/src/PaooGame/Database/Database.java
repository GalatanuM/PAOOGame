package PaooGame.Database;

import PaooGame.Items.Hero;
import PaooGame.RefLinks;
import PaooGame.States.State;
import PaooGame.Tiles.Tile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;


public class Database {
    static Connection c = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    private static Database database=null;
    private static int levelsFinished;

    private static RefLinks ref;
    private static int newLevelsFinished;
    private static float newHeroX;
    private static float newHeroY;
    private static int newScore;
    private static float heroX;
    private static float heroY;
    private static int score;

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

    public static synchronized Database getInstance(RefLinks ref)
    {
        if(database == null)
        {
            database = new Database(ref);
        }
        return database;
    }

    public static void databaseNewGame()  {
        String sql = "CREATE TABLE IF NOT EXISTS Game (" +
                "id INTEGER PRIMARY KEY, " +
                "levelsFinished INTEGER," +
                "HeroX REAL," +
                "HeroY REAL," +
                "Score INTEGER" +
                ");";
        try{
            stmt.execute("DROP TABLE IF EXISTS Game;");
            stmt.execute(sql);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean gameExists(){
        try{
            String sqlQuery = "SELECT 1 FROM Game LIMIT 1;";
            ResultSet resultSet = stmt.executeQuery(sqlQuery);
            return resultSet.getBoolean(1);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static void DatabaseSaveGame(){
        String sqlStatement;
        if(gameExists()) {
            sqlStatement = "UPDATE Game SET levelsFinished = ?, Score = ?, HeroX = ?, HeroY = ?;";
        }else {
            sqlStatement = "INSERT INTO Game(levelsFinished , Score , HeroX , HeroY) VALUES (?, ?, ?, ?);";
        }
        try {
            PreparedStatement pstmt = c.prepareStatement(sqlStatement);
            newLevelsFinished = ref.GetGame().levelsFinished;
            newHeroX = ref.GetHero().GetX()+ref.GetHero().getBoundX();
            newHeroY = ref.GetHero().GetY()+ref.GetHero().getBoundY();
            newScore = State.getScor();
            if(newLevelsFinished==0)
            {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap1().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroX/ Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroY/ Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap1().map1ref.mapToString());
                writer.close();
            }
            if(newLevelsFinished==1)
            {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap2().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroX/ Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroY/ Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap2().map2ref.mapToString());
                writer.close();
            }
            if(newLevelsFinished==2)
            {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap3().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroX/ Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroY/ Tile.TILE_HEIGHT)));
                writer.write("\n");
                writer.write(ref.GetMap3().map3ref.mapToString());
                writer.close();
            }
            if(newLevelsFinished==3)
            {
                FileWriter writer = new FileWriter("./src/PaooGame/Database/map.txt");
                writer.write("10 20");
                writer.write(" ");
                writer.write(String.valueOf(ref.GetMap4().getSoilTileContor()));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroX/ Tile.TILE_WIDTH)));
                writer.write(" ");
                writer.write(String.valueOf((int)(newHeroY/ Tile.TILE_HEIGHT)));
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
    public static int getLevelsFinished(){
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
                    State.scor=score;
                    ref.GetGame().setLevelFinished(levelsFinished);
                    if(levelsFinished==0) ref.GetMap1().setLevel(levelsFinished);
                    if(levelsFinished==1) ref.GetMap2().setLevel(levelsFinished);
                    if(levelsFinished==2) ref.GetMap3().setLevel(levelsFinished);
                    if(levelsFinished==3) ref.GetMap4().setLevel(levelsFinished);
                    System.out.println(heroX + " " + heroY + " " + score + " " + levelsFinished);
                    return;
                }
            }
            databaseNewGame();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

