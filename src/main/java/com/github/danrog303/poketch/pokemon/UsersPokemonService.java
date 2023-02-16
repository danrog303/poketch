package com.github.danrog303.poketch.pokemon;

import com.github.danrog303.poketch.user.details.AppUserDetails;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.*;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersPokemonService {
    @Getter @Setter(onMethod_={@Autowired})
    PokemonRepository pokemonRepository;

    @Transactional
    public Set<Long> getPokemonNumbersOfUser(AppUserDetails user) {
        return user.getPokemonCollection().stream()
                .map(Pokemon::getPokedexNumber).collect(Collectors.toUnmodifiableSet());
    }

    @Transactional
    public void removePokemonFromUser(AppUserDetails user, Pokemon pokemon) {
        user.getPokemonCollection().remove(pokemon);
    }

    @Transactional
    public void addPokemonToUser(AppUserDetails user, Pokemon pokemon) {
        user.getPokemonCollection().add(pokemon);
    }
}
