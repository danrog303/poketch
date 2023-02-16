package com.github.danrog303.poketch.pokemon;

import com.github.danrog303.poketch.pokemon.PokemonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonTypeRepository extends JpaRepository<PokemonType, Long> {}
