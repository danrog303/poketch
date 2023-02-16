package com.github.danrog303.poketch.pokemon;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a Pokémon type.
 */
@Table
@Entity
@Data
public class PokemonType {
    @Id @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long pokemonTypeId;

    @Column(unique=true)
    private String typeCodeName;
}
