package com.example.pokemon.controller;


import com.example.pokemon.repository.PokemonRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
