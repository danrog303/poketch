package com.github.danrog303.poketch.stats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class PokemonOwnershipStats {
    public static final int GLOBAL_STATS = -1;

    /**
     * Number of Pokémon generation (starting from 1, generation zero does not exist).
     * If this field is equal to PokemonOwnershipStats.GLOBAL_STATS, the object contains global statistcs (for every generation).
     */
    private int generationNumber;

    /**
     * Completion rate of the specified Pokémon generation.
     * Field is equal 1.0 if completed, 0.0 if no Pokémon of this generation is owned.
     */
    private double completionRate;
}
