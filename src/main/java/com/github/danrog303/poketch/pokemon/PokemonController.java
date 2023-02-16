package com.github.danrog303.poketch.pokemon;

import com.github.danrog303.poketch.auth.AppUserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {
    private final AppUserProvider appUserProvider;
    private final PokemonService pokemonService;
    private final UsersPokemonService usersPokemonService;
    private final PokemonPresentationService pokemonPresentationService;

    @PostMapping("/remove-ownership")
    public String removePokemonOwnership(@RequestParam Long pokemonNumber, Authentication auth) {
        var user = appUserProvider.getAppUserDetails();
        var pokemon = pokemonService.getPokemon(pokemonNumber);
        usersPokemonService.removePokemonFromUser(user, pokemon);
        return "redirect:/pokemon/list?generation=%s#pokemon-%s".formatted(pokemon.getGeneration(), pokemon.getPokedexNumber());
    }

    @PostMapping("/add-ownership")
    public String addPokemonOwnership(@RequestParam Long pokemonNumber) {
        var user = appUserProvider.getAppUserDetails();
        var pokemon = pokemonService.getPokemon(pokemonNumber);
        usersPokemonService.addPokemonToUser(user, pokemon);
        return "redirect:/pokemon/list?generation=%s#pokemon-%s".formatted(pokemon.getGeneration(), pokemon.getPokedexNumber());
    }

    @GetMapping("/list")
    public String showPokemonList(Model model, @RequestParam(required=false, defaultValue="1") Integer generation) {
        var user = appUserProvider.getAppUserDetails();
        var generations = pokemonService.getAvailableGenerations();
        var pokemonCollection = pokemonPresentationService.getPokemonPresentationModels(user, generation);

        model.addAttribute("pokemonCollection", pokemonCollection);
        model.addAttribute("currentGeneration", generation);
        model.addAttribute("generations", generations);
        model.addAttribute("user", user);

        return "pages/pokemon-list";
    }
}
