package com.github.danrog303.poketch.pokemon;

import lombok.Setter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PokemonService {
    @Getter @Setter(onMethod_={@Autowired})
    private PokemonRepository pokemonRepository;

    public List<Integer> getAvailableGenerations() {
        return pokemonRepository.findDistinctGenerations();
    }

    public List<Pokemon> getPokemonList() {
        return pokemonRepository.findAll();
    }

    public List<Pokemon> getPokemonList(int fromGeneration) {
        return pokemonRepository.findAllByGeneration(fromGeneration);
    }

    public Pokemon getPokemon(long pokedexNumber) {
        return pokemonRepository.getReferenceById(pokedexNumber);
    }
}
