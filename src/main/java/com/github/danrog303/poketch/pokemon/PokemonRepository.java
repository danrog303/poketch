package com.github.danrog303.poketch.pokemon;

import com.github.danrog303.poketch.pokemon.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    @Query("select distinct generation from Pokemon order by generation")
    List<Integer> findDistinctGenerations();

    List<Pokemon> findAllByGeneration(int generation);

    long countPokemonByGeneration(int generation);
}
