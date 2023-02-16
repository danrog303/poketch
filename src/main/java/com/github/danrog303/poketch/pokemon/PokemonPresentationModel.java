package com.github.danrog303.poketch.pokemon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PokemonPresentationModel {
    long pokedexNumber;

    @NonNull
    String name;

    int generation;

    boolean owned;

    @NonNull
    String typeString;

    private String imageUrl;

    private String articleUrl;
}
