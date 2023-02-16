package com.github.danrog303.poketch.stats;

import com.github.danrog303.poketch.user.details.AppUserDetails;
import com.github.danrog303.poketch.pokemon.PokemonRepository;
import lombok.Setter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.List;

@Service
public class PokemonStatsService {
    @Getter @Setter(onMethod_={@Autowired})
    private PokemonRepository pokemonRepository;

    public PokemonOwnershipStats getPokemonOwnershipStats(int generation, AppUserDetails user) {
        long pokemonCount = pokemonRepository.countPokemonByGeneration(generation);
        long usersPokemonCount = user.getPokemonCollection().stream()
                .filter(pokemon -> pokemon.getGeneration() == generation).count();
        return new PokemonOwnershipStats(generation, (double) usersPokemonCount / pokemonCount);
    }

    public List<PokemonOwnershipStats> getPokemonOwnershipStats(Collection<Integer> generations, AppUserDetails user) {
        return generations.stream().sorted().map(generation -> getPokemonOwnershipStats(generation, user)).toList();
    }

    public PokemonOwnershipStats getGlobalPokemonOwnershipStats(AppUserDetails user) {
        long pokemonCount = pokemonRepository.count();
        long usersPokemonCount = user.getPokemonCollection().size();
        double completionRate = (double) usersPokemonCount / pokemonCount;
        return new PokemonOwnershipStats(PokemonOwnershipStats.GLOBAL_STATS, completionRate);
    }
}
