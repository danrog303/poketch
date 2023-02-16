package com.github.danrog303.poketch.pokemon;

import java.util.List;

import com.github.danrog303.poketch.user.details.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * Service responsible for preparing {@link Pokemon} objects to be displayed.
 */
@Service
public class PokemonPresentationService {
    @Getter @Setter(onMethod_={@Autowired})
    private PokemonService pokemonService;

    public List<PokemonPresentationModel> getPokemonPresentationModels(AppUserDetails owner) {
        List<Pokemon> pokemonList = pokemonService.getPokemonList();
        return pokemonList.stream().map(pokemon -> mapPokemonToPresentationModel(pokemon, owner)).toList();
    }

    public List<PokemonPresentationModel> getPokemonPresentationModels(AppUserDetails owner, int pokemonGeneration) {
        List<Pokemon> pokemonList = pokemonService.getPokemonList(pokemonGeneration);
        return pokemonList.stream().map(pokemon -> mapPokemonToPresentationModel(pokemon, owner)).toList();
    }

    private PokemonPresentationModel mapPokemonToPresentationModel(Pokemon pokemon, AppUserDetails owner) {
        return new PokemonPresentationModel(
          pokemon.getPokedexNumber(),
          pokemon.getName(),
          pokemon.getGeneration(),
          owner.getPokemonCollection().contains(pokemon),
          getPokemonTypeString(pokemon),
          pokemon.getImageUrl(),
          pokemon.getArticleUrl()
        );
    }

    /**
     * <p>Creates type string for the specified Pokémon.</p>
     * <p>Example of usage:</p>
     * <p>If Pokémon has two types, TYPE_FIRE and TYPE_GHOST, this function will return "Fire / Ghost".
     * If Pokémon has only one type, for example TYPE_FIRE, this function will return "Fire".</p>
     */
    private String getPokemonTypeString(Pokemon pokemon) {
        PokemonType type1 = pokemon.getPrimaryType();
        PokemonType type2 = pokemon.getSecondaryType();

        String typeString1 = type1.getTypeCodeName();
        String typeString2 = type2 != null ? type2.getTypeCodeName() : null;

        if (typeString1.startsWith("TYPE_")) {
            typeString1 = typeString1.substring(5);
        }
        if (typeString2 != null && typeString2.startsWith("TYPE_")) {
            typeString2 = typeString2.substring(5);
        }

        typeString1 = StringUtils.capitalize(typeString1.toLowerCase());
        if (typeString2 != null) {
            typeString2 = StringUtils.capitalize(typeString2.toLowerCase());
            return "%s / %s".formatted(typeString1, typeString2);
        } else {
            return typeString1;
        }
    }
}
