package com.example.pokemon.controller;


import com.example.pokemon.model.Pokemon;
import com.example.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class Homecontroller {

  PokemonRepository pokemonRepository;

  public Homecontroller(PokemonRepository pokemonRepository){
    this.pokemonRepository = pokemonRepository;
  }

  @GetMapping("/")
  public String index(Model model){
    model.addAttribute("pokemon", pokemonRepository.readAll());
    return "index";
  }

  @GetMapping("/thymeleaf")
  public String thymeleaf(Model model){
    model.addAttribute("name", "frederik");
    model.addAttribute("class", "Dat21b");
    return "thymeleaf";
  }

  @GetMapping("/formtest")
  public String showFormtest(){
    return "formtest";
  }

  @PostMapping("/formtest")
  public String formtest(@RequestParam("name") String navn, @RequestParam("pwd") String kodeord, Model model){
    System.out.println(navn + ", " + kodeord);
    model.addAttribute("mitNavn", navn);
    model.addAttribute("mitKodeord",kodeord);
    return "redirect:/formtest";
  }

  @GetMapping("/delete/{id}")
  public String deletePokemon(@PathVariable("id") int deleteid){
    pokemonRepository.deletePokemon(deleteid);
    return "redirect:/";
  }

  @GetMapping("/createpokemon")
  public String showCreatePokemon(){
    return "createpokemon";
  }

  @PostMapping("/createpokemon")
  public String createPokemon(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("speed") int speed, @RequestParam("special_defence")
      int special_defence, @RequestParam("special_attack") int special_attack, @RequestParam("defence") int defence, @RequestParam("attack")
      int attack, @RequestParam("hp") int hp, @RequestParam("primary_type") String primary_type, @RequestParam("secondary_type") String secondary_type){
    Pokemon newPokemon = new Pokemon(id,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,secondary_type);
    //Gem ny pokemon
    pokemonRepository.addPokemon(newPokemon);
    // Vis liste af alle pokemon
    return "redirect:/";
  }


  @GetMapping("/update/{id}")
  public String showUpdate(@PathVariable("id") int id, Model model){
    model.addAttribute("pokemon",pokemonRepository.findById(id));
    return "update";
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable("id") int id,@RequestParam("name") String name, @RequestParam("speed") int speed, @RequestParam("special_defence")
  int special_defence, @RequestParam("special_attack") int special_attack, @RequestParam("defence") int defence, @RequestParam("attack")
  int attack, @RequestParam("hp") int hp, @RequestParam("primary_type") String primary_type, @RequestParam("secondary_type") String secondary_type){
    Pokemon newPokemon = new Pokemon(id,name,speed,special_defence,special_attack,defence,attack,hp,primary_type,secondary_type);
    //Update pokemon
    pokemonRepository.updatePokemon(newPokemon);
    return "redirect:/";
  }

  //@PostMapping("/update/{id}")
  //public String update(@ModelAttribute Pokemon pokemon){
  //}



}
