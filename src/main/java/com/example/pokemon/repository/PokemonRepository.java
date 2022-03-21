package com.example.pokemon.repository;
import com.example.pokemon.model.Pokemon;
import org.springframework.stereotype.Repository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@Repository
public class PokemonRepository {
  private static String user = "root";
  private static Connection connection;
    //<editor-fold desc="Description">
  private static String pw = "Kiaersej123!";
  //</editor-fold>
  private static String DB_URL = "jdbc:mysql://localhost:3306/pokedex";
  public static final String jdbc_driver = "com.mysql.jdbc.driver";


  public ArrayList<Pokemon> readAll(){
    ArrayList<Pokemon> pokemonListe = new ArrayList<>();
    connectToSql();
    try{
      String query = "select * from pokemon";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(query);
      while(resultSet.next()){
        int pokemonID = resultSet.getInt("pokedex_number");
        String name = resultSet.getString("name");
        int speed = resultSet.getInt("speed");
        int special_defence = resultSet.getInt("special_defence");
        int special_attack = resultSet.getInt("special_attack");
        int defence = resultSet.getInt("defence");
        int attack = resultSet.getInt("attack");
        int hp = resultSet.getInt("hp");
        String primary_type = resultSet.getString("primary_type");
        String secondary_type = resultSet.getString("secondary_type");
        pokemonListe.add(new Pokemon(pokemonID,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,
            secondary_type));
      }
    } catch (Exception e) {
      System.out.println("fejl" + e);
    }

return pokemonListe;

  }



  static Connection connectToSql() {
    if (connection != null) {
      return connection;
    } else {
      try {
        connection = DriverManager.getConnection(DB_URL, user, pw);
        System.out.println("Forbundet");
      } catch (Exception e) {
        System.out.println("Fejl" + " " + e);
      }
      return connection;
    }

  }


}
