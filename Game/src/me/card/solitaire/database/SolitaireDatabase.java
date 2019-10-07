package me.card.solitaire.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolitaireDatabase {

    private String host, port, database;

    public SolitaireDatabase(String host, String port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;
    }

    private Connection connection;
    private PreparedStatement getHighscores, addHighscore;

    public void connect(){
        try{
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            String url = "jdbc:derby://" + host + ":" + port + "/" + database + ";create=true;";
            connection = DriverManager.getConnection(url);

            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS `highscores` (`name` VARCHAR(100) NOT NULL, `time` INT NOT NULL);");

            getHighscores = connection.prepareStatement("SELECT * FROM `highscores` LIMIT 10 ORDER BY `time`;");
            addHighscore = connection.prepareStatement("INSERT INTO `highscores` (`name`, `time`) VALUES (?,?);");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getHighscores() {
        List<String> list = new ArrayList<>();
        try{
            ResultSet rs = getHighscores.executeQuery();
            int i = 1;
            while(rs.next()){
                list.add(String.format("%d. %s %d seconds", i++, rs.getString(1), rs.getInt(2)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void addHighscore(String name, int time){
        try{
            addHighscore.setString(1, name);
            addHighscore.setInt(2, time);
            addHighscore.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
