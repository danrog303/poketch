package com.github.danrog303.poketch.stats;

import com.github.danrog303.poketch.auth.AppUserProvider;
import com.github.danrog303.poketch.pokemon.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class PokemonStatsController {
    private final PokemonStatsService pokemonStatsService;
    private final PokemonService pokemonService;
    private final AppUserProvider appUserProvider;

    @GetMapping("")
    public String showPokemonStats(Model model) {
        var user = appUserProvider.getAppUserDetails();
        var pokemonGenerations = pokemonService.getAvailableGenerations();
        var stats = pokemonStatsService.getPokemonOwnershipStats(pokemonGenerations, user);
        var globalStats = pokemonStatsService.getGlobalPokemonOwnershipStats(user);
        model.addAttribute("statistics", stats);
        model.addAttribute("globalStatistics", globalStats);
        return "pages/statistics";
    }
}
