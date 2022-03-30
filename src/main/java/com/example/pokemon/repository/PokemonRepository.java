package com.example.pokemon.repository;
import com.example.pokemon.model.Pokemon;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class PokemonRepository {

  private static Connection connection;


  public ArrayList<Pokemon> readAll(){
    ArrayList<Pokemon> pokemonListe = new ArrayList<>();
    connectToSql();
    try{
      String query = "select * from heroku_2ba92db6c587479.pokemon";
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
        if (secondary_type.equalsIgnoreCase("null")){
          secondary_type = "no secondary type";
        }
        pokemonListe.add(new Pokemon(pokemonID,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,
            secondary_type));
      }
    } catch (Exception e) {
      System.out.println("fejl" + e);
    }

return pokemonListe;

  }

  public void addPokemon(Pokemon pokemon){
    //connect
    connectToSql();
    try {
      //preparestatement
      PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO " +
          "heroku_2ba92db6c587479.pokemon(pokedex_number,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,secondary_type) values (?,?,?,?,?,?,?,?,?,?)");
      //set attributer
      prepareStatement.setInt(1, pokemon.id);
      prepareStatement.setString(2, pokemon.name);
      prepareStatement.setInt(3, pokemon.speed);
      prepareStatement.setInt(4, pokemon.special_defence);
      prepareStatement.setInt(5, pokemon.special_attack);
      prepareStatement.setInt(6, pokemon.defence);
      prepareStatement.setInt(7, pokemon.attack);
      prepareStatement.setInt(8, pokemon.hp);
      prepareStatement.setString(9, pokemon.primary_type);
      prepareStatement.setString(10, pokemon.secondary_type);
      //execute
      prepareStatement.executeUpdate();
    }
    catch (SQLException sqlException){
      System.out.println("Error in creation of pokemon");
      sqlException.printStackTrace();
    }

  }

  public void deletePokemon(int id){
    //connect
    connectToSql();
    try {
      //preparestatement
      PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM pokemon where pokedex_number = ?");
      //Set attributer
      prepareStatement.setInt(1,id);
      //execute
      prepareStatement.executeUpdate();
    }
    catch (SQLException sqlException){
      System.out.println("Error in deletion of pokemon");
      sqlException.printStackTrace();
    }
  }

  public void updatePokemon(Pokemon pokemon){
    connectToSql();
    try {
      //preparestatement
      PreparedStatement prepareStatement = connection.prepareStatement("UPDATE pokemon SET name=?, speed=?," +
          "special_defence=?, special_attack=?, defence=?, attack=?, hp=?, primary_type=?, secondary_type=?" +
          "where pokedex_number = ?");
      //Set attributer
      prepareStatement.setString(1, pokemon.name);
      prepareStatement.setInt(2, pokemon.speed);
      prepareStatement.setInt(3, pokemon.special_defence);
      prepareStatement.setInt(4, pokemon.special_attack);
      prepareStatement.setInt(5, pokemon.defence);
      prepareStatement.setInt(6, pokemon.attack);
      prepareStatement.setInt(7, pokemon.hp);
      prepareStatement.setString(8, pokemon.primary_type);
      prepareStatement.setString(9, pokemon.secondary_type);
      prepareStatement.setInt(10,pokemon.id);
      //execute
      prepareStatement.executeUpdate();
    }
    catch (SQLException sqlException){
      System.out.println("Error in deletion of pokemon");
      sqlException.printStackTrace();
    }
  }

  public Pokemon findById (int id){
    Pokemon pokemon = new Pokemon();
    //connect
    connectToSql();
    String query = "SELECT pokedex_number, name, speed, special_defence, special_attack, defence, attack, hp, " +
     "primary_type, secondary_type FROM pokemon where pokedex_number = ?";

    try {
      //preparestatement
      PreparedStatement prepareStatement = connection.prepareStatement(query);

      //Set attributer
      prepareStatement.setInt(1,id);
      //execute
      ResultSet resultSet = prepareStatement.executeQuery();

      while (resultSet.next()){
        int pokemonId = resultSet.getInt("pokedex_number");
        String name = resultSet.getString("name");
        int speed = resultSet.getInt("speed");
        int special_defence = resultSet.getInt("special_defence");
        int special_attack = resultSet.getInt("special_attack");
        int defence = resultSet.getInt("defence");
        int attack = resultSet.getInt("attack");
        int hp = resultSet.getInt("hp");
        String primary_type = resultSet.getString("primary_type");
        String secondary_type = resultSet.getString("secondary_type");
        pokemon = new Pokemon(pokemonId,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,secondary_type);
      }
    }
    catch (SQLException sqlException){
      System.out.println("Error in deletion of pokemon");
      sqlException.printStackTrace();
    }
    return pokemon;
  }


  static Connection connectToSql() {
    if (connection != null) {
      return connection;
    } else {
      try {
        connection = DriverManager.getConnection(System.getenv("url"), System.getenv("user"),
            System.getenv("password"));
        System.out.println("Forbundet");
      } catch (Exception e) {
        System.out.println("Fejl" + " " + e);
      }
      return connection;
    }

  }


}
